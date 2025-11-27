package at.campus02.dbp;


import at.campus02.dbp.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class SchrankDAOImpl implements SchrankDAO {

    @Override
    public Schrank findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Schrank.class, id);
        }
    }

    @Override
    public Schrank save(Schrank schrank) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(schrank);      // transient -> persistent
            tx.commit();                   // ID wird gesetzt
            return schrank;                // jetzt persistent (mit ID)
        } // detached nach try-with-resources
    }

    @Override
    public Schrank update(Schrank schrank) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            // schrank ist in typischen Fällen detached -> merge gibt eine
            // gemanagte (persistent) Instanz zurück
            Schrank merged = (Schrank) session.merge(schrank);
            tx.commit();
            return merged;                 // gemanagte Version zurückgeben
        } // merged ist nach Schließen der Session wieder detached
    }

    @Override
    public void delete(Schrank schrank) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            // falls schrank detached ist, zuerst mergen oder neu laden
            Schrank managed = (Schrank) session.merge(schrank);
            session.remove(managed);
            tx.commit();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Schrank schrank = session.get(Schrank.class, id);
            if (schrank != null) {
                session.remove(schrank);
            }

            tx.commit();
        }
    }

    public List getAlleSchraenke(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Schrank ";
            Query query = session.createQuery(hql);
            List results = query.list();
            return  results;
        }

    }


}