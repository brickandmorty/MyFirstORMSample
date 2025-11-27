package at.campus02.dbp;


import at.campus02.dbp.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class SchrankQuery  {


    public Schrank findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Schrank.class, id);
        }
    }

    public List<Schrank> getAlleSchraenke(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Schrank AS s ";
            Query query = session.createQuery(hql);
            List<Schrank> results = query.list();
            return  results;
        }

    }
}