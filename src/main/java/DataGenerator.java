import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Created by Karol on 2017-12-24.
 */
public class DataGenerator {
    private final EntityManagerFactory emf;
    private EntityManager em;
    public DataGenerator(EntityManagerFactory emf){
        this.emf = emf;
    }

    public void generate(){
        em = emf.createEntityManager();
        try{
            EntityTransaction tx = em.getTransaction();
            createCategories(tx);
            createCompanies(tx);
            createProducts(tx);
        }
        finally {
            em.close();
        }

    }

    private void createCategories(EntityTransaction tx) {
        tx.begin();

        Category cat1 = new Category("Jedzenie", "Pieczywo, nabial, napoje...");
        Category cat2 = new Category("Chemia", "Srodki czystosci, rzeczy do uzytku codziennego...");
        em.persist(cat1);
        em.persist(cat2);

        tx.commit();
    }

    private void createCompanies(EntityTransaction tx) {
        tx.begin();

        Supplier com1 = new Supplier("Swieze jedzonko", new Address("Poland", "Cracow", "Smaczna"));
        Supplier com2 = new Supplier("Fajna chemia", new Address("Poland", "Cracow", "Kwasowa"));
        Supplier com3 = new Supplier("Chemia i jedzonko", new Address("Poland", "Cracow", "Kwasna"));

        Customer com4 = new Customer("Fakturopol", new Address("Poland", "Cracow", "Krolewska"), "medius", "qwe");
        Customer com5 = new Customer("Czysciutko", new Address("Poland", "Cracow", "Kawiory"), "ala", "123");

        em.persist(com1);
        em.persist(com2);
        em.persist(com3);
        em.persist(com4);
        em.persist(com5);

        tx.commit();
    }

    private void createProducts(EntityTransaction tx) {
        tx.begin();

        List<Category> categories = em.createQuery("from Category").getResultList();
        Category cat1 = categories.get(0);
        Category cat2 = categories.get(1);
        List<Supplier> suppliers = em.createQuery("from Supplier").getResultList();
        Supplier sup1 = suppliers.get(0);
        Supplier sup2 = suppliers.get(1);
        Supplier sup3 = suppliers.get(2);

        Product pro1 = new Product("Chleb krojony", "swiezy", 30, 2.50, cat1, sup1);
        Product pro2 = new Product("Mleko w butelce", "swieze", 10, 2.00, cat1, sup1);
        Product pro3 = new Product("Cola w puszce", "gazowana", 50, 1.50, cat1, sup1);
        Product pro4 = new Product("Domestos", "dezynfekujacy", 15, 10.50, cat2, sup2);
        Product pro5 = new Product("Zel pod prysznic", "pachnacy", 10, 7.50, cat2, sup2);
        Product pro6 = new Product("Worki na smieci", "wytrzymale", 100, 3.50, cat2, sup2);
        Product pro7 = new Product("Bulka", "chrupiaca", 24, 0.50, cat1, sup3);
        Product pro8 = new Product("Jaja 10 sztuk", "rozmiar M", 6, 8.50, cat1, sup3);
        Product pro9 = new Product("Pasta do zebow", "wybielajaca", 7, 9.50, cat2, sup3);

        em.persist(pro1);
        em.persist(pro2);
        em.persist(pro3);
        em.persist(pro4);
        em.persist(pro5);
        em.persist(pro6);
        em.persist(pro7);
        em.persist(pro8);
        em.persist(pro9);

        tx.commit();
    }
}