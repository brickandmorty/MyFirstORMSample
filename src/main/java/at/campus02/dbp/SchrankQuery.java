package at.campus02.dbp;


import at.campus02.dbp.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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

    public List<Object[]>  groupSchrankByOrt(Standort standort) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = " SELECT s.standort, SUM(s.maxAnzahlAnKleidungsstuecken), count(s.id)  AS AnzahlSchraenke FROM Schrank s" +
                    "            GROUP BY s.standort";
            Query query = session.createQuery(hql, Object[].class);

            return query.getResultList();
        }
    }

    //ORDER BY (des or asc)

    public List<Schrank> findAllOrderedByMaxAmount(boolean descending) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String order = descending ? "DESC" : "ASC";
            String hql = "FROM Schrank s ORDER BY s.maxAnzahlAnKleidungsstuecken " + order;
            return session.createQuery(hql, Schrank.class).getResultList();
        }
    }

    /* HQL WHERE Clause */
    public List<Schrank> getAlleSchraenkeWhere(Standort standort) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Schrank s WHERE s.standort = :standort"; // HQL - kein Tabellenname, sondern Entity-Name
            Query<Schrank> query = session.createQuery(hql, Schrank.class);
            query.setParameter("standort", standort);
            return query.list();
        }
    }

    public List showSchrankOrderByOrt(int count) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Schrank s WHERE s.maxAnzahlAnKleidungsstuecken > :amount ORDER BY s.standort DESC";
            Query query = session.createQuery(hql);
            query.setParameter("amount", count);
            return query.getResultList();
        }
    }

    public List showSchrankPagination() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Schrank";
            Query query = session.createQuery(hql);
            query.setFirstResult(1);
            query.setMaxResults(3);
            return query.getResultList();
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
    public List<Schrank> findSchraenkeByStandort(Standort standort) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Schrank s WHERE s.standort = :standort";
            Query<Schrank> query = session.createQuery(hql, Schrank.class);
            query.setParameter("standort", standort);   // Enum direkt übergeben

            return query.getResultList();
        }
    }


    public List<Schrank> findSchraenkeByStandortCriteria(Standort standort) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Schrank> cq = cb.createQuery(Schrank.class);
            Root<Schrank> root = cq.from(Schrank.class);

            cq.select(root)
                    .where(cb.equal(root.get("standort"), standort));

            Query<Schrank> query = session.createQuery(cq);
            return query.getResultList();
        }
    }

        public List<Schrank> findSchraenkeByStandortUndMaxAnzahl(Standort standort, int maxAnzahl) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {

                String hql = "FROM Schrank s " +
                        "WHERE s.standort = :standort " +
                        "AND s.maxAnzahlAnKleidungsstuecken < :maxAnzahl";

                Query<Schrank> query = session.createQuery(hql, Schrank.class);
                query.setParameter("standort", standort);
                query.setParameter("maxAnzahl", maxAnzahl);

                return query.getResultList();
            }

        }

    public List<Schrank> findSchraenkeByStandortUndMaxAnzahlCriteria(Standort standort, int maxAnzahl) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Schrank> cq = cb.createQuery(Schrank.class);
            Root<Schrank> root = cq.from(Schrank.class);

            // Einzelne Bedingungen
            // s.standort = :standort
            var predicateStandort =
                    cb.equal(root.get("standort"), standort);

            // s.maxAnzahlAnKleidungsstuecken < :maxAnzahl
            var predicateMaxAnzahl =
                    cb.lt(root.get("maxAnzahlAnKleidungsstuecken"), maxAnzahl);
            // alternativ (für Comparable-Typen):
            // cb.lessThan(root.get("maxAnzahlAnKleidungsstuecken"), maxAnzahl);

            // Beide Bedingungen mit AND verknüpfen
            cq.select(root)
                    .where(cb.and(predicateStandort, predicateMaxAnzahl));

            Query<Schrank> query = session.createQuery(cq);
            return query.getResultList();
        }
    }
    }
