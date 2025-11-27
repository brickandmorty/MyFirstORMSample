package at.campus02.dbp.sampleRelationship;
import at.campus02.dbp.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class VorlesungDAO {

    public Vorlesung save(Vorlesung vorlesung) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(vorlesung);
            tx.commit();
            return vorlesung;
        }
    }

    public Vorlesung findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Vorlesung.class, id);
        }
    }
}
