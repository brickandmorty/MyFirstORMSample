package at.campus02.dbp;

import at.campus02.dbp.model.Kleidungsstueck;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactoryOld() {
        if (sessionFactory == null) {
            Configuration cfg = new Configuration().configure();
            cfg.addAnnotatedClass(Kleidungsstueck.class);

            sessionFactory = cfg.buildSessionFactory(
                    new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties())
                            .build());
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
