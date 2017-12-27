import com.google.gson.Gson;
import model.Customer;
import model.Product;
import model.ProductOrder;
import model.ShopTransaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

/**
 * Created by Karol on 2017-12-25.
 */
public class Router {
    private final EntityManagerFactory emf;
    private final Gson gson;
    private int currentCustomerId;

    public Router(EntityManagerFactory emf){
        this.emf = emf;
        this.gson = new Gson();

        port(4000);
        enableCORS();
    }

    public void registerRoutes(){
        this.currentCustomerId = 0;

        get("/products", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                return gson.toJson(((List<Product>)em.createQuery("from Product ").getResultList())
                        .stream().map(s -> new IdObj(s.getId(), s.getName())).collect(Collectors.toList()));
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }});

        get("/products/:id", (req, res) -> {
            int prodId = Integer.parseInt(req.params(":id"));
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                return gson.toJson(new IdObj(prodId,
                        em.createQuery("select p.name, p.description, p.quantity, p.unitPrice, p.category.name, p.supplier.companyName from Product p where p.id=:id").setParameter("id", prodId).getSingleResult()));
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }});

        post("/products/:id/:quantity", (req, res) -> {
            int prodId = Integer.parseInt(req.params(":id"));
            int quantity = Integer.parseInt(req.params(":quantity"));
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                em.createQuery("update Product p set p.quantity=p.quantity-:quantity where p.id=:id")
                        .setParameter("quantity", quantity)
                        .setParameter("id", prodId)
                        .executeUpdate();

                Product p = em.getReference(Product.class, prodId);
                int stId = (int)em.createQuery("select st.id from ShopTransaction st order by st.id desc").setMaxResults(1).getSingleResult();
                ShopTransaction st = em.getReference(ShopTransaction.class, stId);
                ProductOrder po = new ProductOrder(quantity, p, st);
                em.persist(po);
                st.addProductOrder(po);
                res.status(200);
                return res;
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }
        });

        get("/transactions", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                return gson.toJson(((List<ShopTransaction>)em.createQuery("from ShopTransaction").getResultList())
                        .stream().map(ShopTransaction::getTransactionNumber).collect(Collectors.toList()));
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }});

        get("/transactions/:id/products", (req, res) -> {
            int transId = Integer.parseInt(req.params(":id"));
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                ShopTransaction st = em.getReference(ShopTransaction.class, transId);
                return gson.toJson(
                st.getProductOrders().stream().map(po -> new String[]{
                    po.getProduct().getName(),
                    String.valueOf(po.getQuantity()),
                    String.valueOf(po.getProduct().getUnitPrice() * po.getQuantity())})
                        .collect(Collectors.toList()));
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }});

        get("/transactions/:id/cost", (req, res) -> {
            int transId = Integer.parseInt(req.params(":id"));
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                ShopTransaction st = em.getReference(ShopTransaction.class, transId);
                return gson.toJson(
                        st.getProductOrders().stream()
                                .mapToDouble(po -> po.getQuantity() * po.getProduct().getUnitPrice())
                                .sum());
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }});

        post("/transactions/new", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try{
                tx.begin();
                List<Integer> transactions = em.createQuery("select st.id from ShopTransaction st order by id desc").getResultList();

                if(transactions.size() == 0 || em.getReference(ShopTransaction.class, transactions.get(0)).getProductOrders().size() > 0){
                    ShopTransaction transaction = new ShopTransaction(em.getReference(Customer.class, currentCustomerId));
                    em.persist(transaction);
                    res.status(201);
                }
                else res.status(200);

                return res;
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }});

        post("/logIn/:login/:password", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            String login = req.params(":login");
            String password = req.params(":password");

            try{
                tx.begin();
                List result = em.createQuery("select c.id from Customer c where c.login=:login and c.password=:password")
                        .setParameter("login", login)
                        .setParameter("password", password)
                        .getResultList();

                if(result.isEmpty())
                    res.status(401);
                else
                    this.currentCustomerId = (int)result.get(0);

                return res;
            }
            catch(Exception e) {
                res.status(500);
                return res;
            }
            finally {
                tx.commit();
                em.close();
            }});

        post("/logOut", (req, res) -> this.currentCustomerId = 0);
        get("/loggedIn", (req, res) -> this.currentCustomerId != 0);
    }

    private static void enableCORS() {
        options("/*",
                (request, response) -> {
                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    }

    private class IdObj{
        public int id;
        public Object obj;

        public IdObj(int id, Object obj){
            this.id = id;
            this.obj = obj;
        }
    }
}
