package at.campus02.dbp;

import at.campus02.dbp.model.Kleidungsstueck;
import at.campus02.dbp.model.Schrank;
import at.campus02.dbp.model.Standort;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * HQL Query Examples für die Schrankverwaltung
 * Basierend auf: https://www.tutorialspoint.com/hibernate/hibernate_query_language.htm
 */
public class HQLQueryExamples {

    private Session session;

    public HQLQueryExamples(Session session) {
        this.session = session;
    }

    // 1. BASIC SELECT with WHERE Clause
    public List<Schrank> findSchrankByStandort(Standort standort) {
        String hql = "FROM Schrank s WHERE s.standort = :standort";
        Query<Schrank> query = session.createQuery(hql, Schrank.class);
        query.setParameter("standort", standort);
        return query.getResultList();
    }

    // 2. SELECT with WHERE and Comparison Operators
    public List<Schrank> findSchraenkeWithMinCapacity(int minCapacity) {
        String hql = "FROM Schrank s WHERE s.maxAnzahlAnKleidungsstuecken >= :minCapacity";
        Query<Schrank> query = session.createQuery(hql, Schrank.class);
        query.setParameter("minCapacity", minCapacity);
        return query.getResultList();
    }

    // 3. SELECT with WHERE and LIKE Operator for Kleidungsstücke
    public List<Kleidungsstueck> findKleidungByBezeichnung(String bezeichnungPattern) {
        String hql = "FROM Kleidungsstueck k WHERE k.bezeichnung LIKE :pattern";
        Query<Kleidungsstueck> query = session.createQuery(hql, Kleidungsstueck.class);
        query.setParameter("pattern", "%" + bezeichnungPattern + "%");
        return query.getResultList();
    }

    // 4. SELECT with WHERE and BETWEEN Operator
    public List<Schrank> findSchraenkeInCapacityRange(int minCap, int maxCap) {
        String hql = "FROM Schrank s WHERE s.maxAnzahlAnKleidungsstuecken BETWEEN :min AND :max";
        Query<Schrank> query = session.createQuery(hql, Schrank.class);
        query.setParameter("min", minCap);
        query.setParameter("max", maxCap);
        return query.getResultList();
    }

    // 5. SELECT with WHERE and IN Operator
    public List<Kleidungsstueck> findKleidungByColors(List<String> colors) {
        String hql = "FROM Kleidungsstueck k WHERE k.farbe IN (:colorList)";
        Query<Kleidungsstueck> query = session.createQuery(hql, Kleidungsstueck.class);
        query.setParameter("colorList", colors);
        return query.getResultList();
    }

    // 6. ORDER BY Examples
    public List<Schrank> findAllSchraenkeOrderedByCapacity() {
        String hql = "FROM Schrank s ORDER BY s.maxAnzahlAnKleidungsstuecken DESC";
        Query<Schrank> query = session.createQuery(hql, Schrank.class);
        return query.getResultList();
    }

    public List<Kleidungsstueck> findAllKleidungOrderedByBezeichnungAndFarbe() {
        String hql = "FROM Kleidungsstueck k ORDER BY k.bezeichnung ASC, k.farbe ASC";
        Query<Kleidungsstueck> query = session.createQuery(hql, Kleidungsstueck.class);
        return query.getResultList();
    }

    // 7. GROUP BY with Aggregate Functions
    public List<Object[]> countSchraenkeByStandort() {
        String hql = "SELECT s.standort as standort, COUNT(s) as anzahl " +
                    "FROM Schrank s GROUP BY s.standort";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        return query.getResultList();
    }

    public List<Object[]> avgMaxCapacityByStandort() {
        String hql = "SELECT s.standort as ort, AVG(s.maxAnzahlAnKleidungsstuecken) as durchschnitt " +
                    "FROM Schrank s GROUP BY s.standort";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        return query.getResultList();
    }

    public List<Object[]> minMaxCapacityByStandort() {
        String hql = "SELECT s.standort as standort, " +
                    "MIN(s.maxAnzahlAnKleidungsstuecken) as minKapazitaet, " +
                    "MAX(s.maxAnzahlAnKleidungsstuecken) as maxKapazitaet " +
                    "FROM Schrank s GROUP BY s.standort";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        return query.getResultList();
    }

    // 8. GROUP BY with HAVING Clause
    public List<Object[]> findStandorteWithMultipleSchraenke() {
        String hql = "SELECT s.standort as standort, COUNT(s) as anzahl " +
                    "FROM Schrank s " +
                    "GROUP BY s.standort " +
                    "HAVING COUNT(s) > 1";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        return query.getResultList();
    }

    public List<Object[]> findStandorteWithHighAvgCapacity(double minAvgCapacity) {
        String hql = "SELECT s.standort as standort, AVG(s.maxAnzahlAnKleidungsstuecken) as avgKapazitaet " +
                    "FROM Schrank s " +
                    "GROUP BY s.standort " +
                    "HAVING AVG(s.maxAnzahlAnKleidungsstuecken) > :minAvg";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("minAvg", minAvgCapacity);
        return query.getResultList();
    }

    // 9. Pagination Examples
    public List<Schrank> findSchraenkePaginated(int pageNumber, int pageSize) {
        String hql = "FROM Schrank s ORDER BY s.id";
        Query<Schrank> query = session.createQuery(hql, Schrank.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<Kleidungsstueck> findKleidungPaginatedByFarbe(String farbe, int pageNumber, int pageSize) {
        String hql = "FROM Kleidungsstueck k WHERE k.farbe = :farbe ORDER BY k.bezeichnung";
        Query<Kleidungsstueck> query = session.createQuery(hql, Kleidungsstueck.class);
        query.setParameter("farbe", farbe);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    // 10. Advanced Queries with Aliases
    public List<Object[]> findSchraenkeWithAliases() {
        String hql = "SELECT schrank.id as schrankId, " +
                    "schrank.standort as ort, " +
                    "schrank.maxAnzahlAnKleidungsstuecken as kapazitaet " +
                    "FROM Schrank schrank " +
                    "WHERE schrank.maxAnzahlAnKleidungsstuecken > 10 " +
                    "ORDER BY schrank.standort, schrank.maxAnzahlAnKleidungsstuecken DESC";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        return query.getResultList();
    }

    public List<Object[]> findKleidungWithAliases() {
        String hql = "SELECT kl.id as kleidungId, " +
                    "kl.bezeichnung as name, " +
                    "kl.farbe as color " +
                    "FROM Kleidungsstueck kl " +
                    "WHERE kl.farbe IS NOT NULL " +
                    "ORDER BY kl.farbe, kl.bezeichnung";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        return query.getResultList();
    }

    // 11. Complex Query with Multiple Conditions and Aliases
    public List<Object[]> complexQueryWithAliases(String farbFilter, int minCapacity) {
        String hql = "SELECT DISTINCT s.standort as standortName, " +
                    "COUNT(s) as schrankAnzahl, " +
                    "SUM(s.maxAnzahlAnKleidungsstuecken) as gesamtKapazitaet " +
                    "FROM Schrank s " +
                    "WHERE s.maxAnzahlAnKleidungsstuecken >= :minCap " +
                    "GROUP BY s.standort " +
                    "HAVING COUNT(s) > 0 " +
                    "ORDER BY gesamtKapazitaet DESC";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("minCap", minCapacity);
        return query.getResultList();
    }

    // 12. Aggregation Functions Examples
    public Long countAllSchraenke() {
        String hql = "SELECT COUNT(s) FROM Schrank s";
        Query<Long> query = session.createQuery(hql, Long.class);
        return query.getSingleResult();
    }

    public Long countAllKleidungsstuecke() {
        String hql = "SELECT COUNT(k) FROM Kleidungsstueck k";
        Query<Long> query = session.createQuery(hql, Long.class);
        return query.getSingleResult();
    }

    public Double getTotalCapacityOfAllSchraenke() {
        String hql = "SELECT SUM(s.maxAnzahlAnKleidungsstuecken) FROM Schrank s";
        Query<Double> query = session.createQuery(hql, Double.class);
        return query.getSingleResult();
    }

    public Double getAverageCapacityOfSchraenke() {
        String hql = "SELECT AVG(s.maxAnzahlAnKleidungsstuecken) FROM Schrank s";
        Query<Double> query = session.createQuery(hql, Double.class);
        return query.getSingleResult();
    }

    public Integer getMaxCapacityOfSchraenke() {
        String hql = "SELECT MAX(s.maxAnzahlAnKleidungsstuecken) FROM Schrank s";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        return query.getSingleResult();
    }

    public Integer getMinCapacityOfSchraenke() {
        String hql = "SELECT MIN(s.maxAnzahlAnKleidungsstuecken) FROM Schrank s";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        return query.getSingleResult();
    }

    // 13. UPDATE Query Examples
    public int updateSchrankCapacity(Long schrankId, int newCapacity) {
        String hql = "UPDATE Schrank s SET s.maxAnzahlAnKleidungsstuecken = :newCap WHERE s.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("newCap", newCapacity);
        query.setParameter("id", schrankId);
        return query.executeUpdate();
    }

    public int updateKleidungFarbe(String oldFarbe, String newFarbe) {
        String hql = "UPDATE Kleidungsstueck k SET k.farbe = :newColor WHERE k.farbe = :oldColor";
        Query query = session.createQuery(hql);
        query.setParameter("newColor", newFarbe);
        query.setParameter("oldColor", oldFarbe);
        return query.executeUpdate();
    }

    // 14. DELETE Query Examples
    public int deleteKleidungByFarbe(String farbe) {
        String hql = "DELETE FROM Kleidungsstueck k WHERE k.farbe = :farbe";
        Query query = session.createQuery(hql);
        query.setParameter("farbe", farbe);
        return query.executeUpdate();
    }

    public int deleteSchraenkeWithLowCapacity(int maxCapacity) {
        String hql = "DELETE FROM Schrank s WHERE s.maxAnzahlAnKleidungsstuecken < :maxCap";
        Query query = session.createQuery(hql);
        query.setParameter("maxCap", maxCapacity);
        return query.executeUpdate();
    }
}