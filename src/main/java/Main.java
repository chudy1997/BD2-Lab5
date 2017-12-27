import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Karol on 2017-12-23.
 */
public class Main {
    public static void main(final String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAKarolBartyzel");

        final DataGenerator dg = new DataGenerator(emf);
        final Router rt = new Router(emf);

        dg.generate();
        rt.registerRoutes();
    }
}