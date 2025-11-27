package at.campus02.dbp;

import at.campus02.dbp.model.Kleidungsstueck;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class KleidungsstueckDAO {


    //umbuchen(b1, 500, b2)
    //Transiente Entity - neue Entität
    public void save(Kleidungsstueck ks) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(ks);
            tx.commit(); //persistent
        } //detached
    }
    public Kleidungsstueck findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Kleidungsstueck.class, id);
        }
    }
    //bei Detached
    public void update(Kleidungsstueck ks) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(ks); //persistent
            tx.commit();
        } ////detached
    }

    public void delete(Kleidungsstueck ks) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(ks);
            tx.commit();
        } //removed
    }


}
