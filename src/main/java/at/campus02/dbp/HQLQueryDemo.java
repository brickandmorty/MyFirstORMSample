package at.campus02.dbp;

import at.campus02.dbp.model.Kleidungsstueck;
import at.campus02.dbp.model.Schrank;
import at.campus02.dbp.model.Standort;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

/**
 * Demo-Klasse für die Verwendung der HQL Query Examples
 */
public class HQLQueryDemo {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Test-Daten erstellen
            createTestData(session);

            // HQL Query Examples initialisieren
            HQLQueryExamples hqlExamples = new HQLQueryExamples(session);

            // Demo der verschiedenen Query-Typen
            demonstrateQueries(hqlExamples);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }

    private static void createTestData(Session session) {
        System.out.println("=== Erstelle Test-Daten ===");

        // Schränke erstellen
        Schrank schrank1 = new Schrank(Standort.WOHNZIMMER, 15);
        Schrank schrank2 = new Schrank(Standort.VORAUM, 8);
        Schrank schrank3 = new Schrank(Standort.KUECHE, 5);
        Schrank schrank4 = new Schrank(Standort.WOHNZIMMER, 20);

        session.persist(schrank1);
        session.persist(schrank2);
        session.persist(schrank3);
        session.persist(schrank4);

        // Kleidungsstücke erstellen
        Kleidungsstueck kleidung1 = new Kleidungsstueck("T-Shirt", "blau");
        Kleidungsstueck kleidung2 = new Kleidungsstueck("Hose", "schwarz");
        Kleidungsstueck kleidung3 = new Kleidungsstueck("Jacke", "grün");
        Kleidungsstueck kleidung4 = new Kleidungsstueck("Hemd", "blau");
        Kleidungsstueck kleidung5 = new Kleidungsstueck("Pullover", "rot");

        session.persist(kleidung1);
        session.persist(kleidung2);
        session.persist(kleidung3);
        session.persist(kleidung4);
        session.persist(kleidung5);

        session.flush();
        System.out.println("Test-Daten erstellt!\n");
    }

    private static void demonstrateQueries(HQLQueryExamples hqlExamples) {
        System.out.println("=== HQL Query Demonstrationen ===\n");

        // 1. WHERE Queries
        System.out.println("1. WHERE Queries:");
        System.out.println("--- Schränke im Wohnzimmer:");
        List<Schrank> wohnzimmerSchraenke = hqlExamples.findSchrankByStandort(Standort.WOHNZIMMER);
        wohnzimmerSchraenke.forEach(s -> System.out.println("  " + s));

        System.out.println("--- Schränke mit mindestens 10 Plätzen:");
        List<Schrank> grosseSchraenke = hqlExamples.findSchraenkeWithMinCapacity(10);
        grosseSchraenke.forEach(s -> System.out.println("  " + s));

        System.out.println("--- Kleidung mit 'shirt' in Bezeichnung:");
        List<Kleidungsstueck> shirts = hqlExamples.findKleidungByBezeichnung("shirt");
        shirts.forEach(k -> System.out.println("  " + k));
        System.out.println();

        // 2. ORDER BY Queries
        System.out.println("2. ORDER BY Queries:");
        System.out.println("--- Schränke nach Kapazität absteigend:");
        List<Schrank> orderedSchraenke = hqlExamples.findAllSchraenkeOrderedByCapacity();
        orderedSchraenke.forEach(s -> System.out.println("  " + s));
        System.out.println();

        // 3. GROUP BY and Aggregation
        System.out.println("3. GROUP BY und Aggregation:");
        System.out.println("--- Anzahl Schränke pro Standort:");
        List<Object[]> schrankCount = hqlExamples.countSchraenkeByStandort();
        schrankCount.forEach(result -> 
            System.out.println("  " + result[0] + ": " + result[1] + " Schränke"));

        System.out.println("--- Durchschnittliche Kapazität pro Standort:");
        List<Object[]> avgCapacity = hqlExamples.avgMaxCapacityByStandort();
        avgCapacity.forEach(result -> 
            System.out.printf("  %s: %.2f Plätze%n", result[0], result[1]));

        System.out.println("--- Min/Max Kapazität pro Standort:");
        List<Object[]> minMaxCap = hqlExamples.minMaxCapacityByStandort();
        minMaxCap.forEach(result -> 
            System.out.printf("  %s: Min=%d, Max=%d%n", result[0], result[1], result[2]));
        System.out.println();

        // 4. HAVING Clause
        System.out.println("4. GROUP BY mit HAVING:");
        System.out.println("--- Standorte mit mehreren Schränken:");
        List<Object[]> multipleSchraenke = hqlExamples.findStandorteWithMultipleSchraenke();
        multipleSchraenke.forEach(result -> 
            System.out.println("  " + result[0] + ": " + result[1] + " Schränke"));
        System.out.println();

        // 5. Pagination
        System.out.println("5. Pagination:");
        System.out.println("--- Erste 2 Schränke (Seite 1):");
        List<Schrank> page1 = hqlExamples.findSchraenkePaginated(1, 2);
        page1.forEach(s -> System.out.println("  " + s));

        System.out.println("--- Nächste 2 Schränke (Seite 2):");
        List<Schrank> page2 = hqlExamples.findSchraenkePaginated(2, 2);
        page2.forEach(s -> System.out.println("  " + s));
        System.out.println();

        // 6. Aliases
        System.out.println("6. Queries mit Aliases:");
        System.out.println("--- Schränke mit Aliases (ID, Ort, Kapazität):");
        List<Object[]> schrankAliases = hqlExamples.findSchraenkeWithAliases();
        schrankAliases.forEach(result -> 
            System.out.printf("  ID=%d, Ort=%s, Kapazität=%d%n", result[0], result[1], result[2]));

        System.out.println("--- Kleidung mit Aliases (ID, Name, Farbe):");
        List<Object[]> kleidungAliases = hqlExamples.findKleidungWithAliases();
        kleidungAliases.forEach(result -> 
            System.out.printf("  ID=%d, Name=%s, Farbe=%s%n", result[0], result[1], result[2]));
        System.out.println();

        // 7. Complex Aggregation
        System.out.println("7. Komplexe Aggregation:");
        System.out.println("--- Standorte mit Gesamtkapazität (min. Kapazität 5):");
        List<Object[]> complexQuery = hqlExamples.complexQueryWithAliases("", 5);
        complexQuery.forEach(result -> 
            System.out.printf("  %s: %d Schränke, Gesamtkapazität=%d%n", 
                result[0], result[1], result[2]));
        System.out.println();

        // 8. Single Aggregation Results
        System.out.println("8. Einzelne Aggregations-Ergebnisse:");
        System.out.println("--- Gesamtanzahl Schränke: " + hqlExamples.countAllSchraenke());
        System.out.println("--- Gesamtanzahl Kleidungsstücke: " + hqlExamples.countAllKleidungsstuecke());
        System.out.printf("--- Gesamtkapazität aller Schränke: %.0f%n", hqlExamples.getTotalCapacityOfAllSchraenke());
        System.out.printf("--- Durchschnittliche Kapazität: %.2f%n", hqlExamples.getAverageCapacityOfSchraenke());
        System.out.println("--- Maximale Kapazität: " + hqlExamples.getMaxCapacityOfSchraenke());
        System.out.println("--- Minimale Kapazität: " + hqlExamples.getMinCapacityOfSchraenke());
        System.out.println();

        // 9. IN Operator
        System.out.println("9. IN Operator:");
        System.out.println("--- Kleidung in blauen und roten Farben:");
        List<String> colors = Arrays.asList("blau", "rot");
        List<Kleidungsstueck> coloredClothing = hqlExamples.findKleidungByColors(colors);
        coloredClothing.forEach(k -> System.out.println("  " + k));
        System.out.println();

        // 10. BETWEEN Operator
        System.out.println("10. BETWEEN Operator:");
        System.out.println("--- Schränke mit Kapazität zwischen 8 und 18:");
        List<Schrank> mediumSchraenke = hqlExamples.findSchraenkeInCapacityRange(8, 18);
        mediumSchraenke.forEach(s -> System.out.println("  " + s));
        System.out.println();
    }
}