package at.campus02.dbp;

import at.campus02.dbp.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
/*
        Schrank s1 =new  Schrank();
        s1.setStandort(Standort.WOHNZIMMER);
        s1.setMaxAnzahlAnKleidungsstuecken(50);
        SchrankDAOImpl schrankDAOImpl = new SchrankDAOImpl();
        schrankDAOImpl.save(s1);
        Schrank s2 = schrankDAOImpl.findById(s1.getId());
        s2.setStandort(Standort.KUECHE);
       // schrankDAOImpl.save(s2); //Fehler weil detacted
        schrankDAOImpl.update(s2); //OK
*/
        SchrankQuery schrankQuery =new SchrankQuery();
        List<Schrank> ergebnis = schrankQuery.getAlleSchraenke();
        for (Schrank schrank : ergebnis) {
            System.out.println(schrank);
        };


         /*

      Kleidungsstueck ks = new Kleidungsstueck();
      ks.setBezeichnung("Handschuhe");
      ks.setFarbe("Rot");



        KleidungsstueckDAO dao=new KleidungsstueckDAO();
        //transiend - ruft session.persist auf
        dao.save(ks);
        ks.setFarbe("Orange");
       dao.update(ks); //merge von Hibernate

        Kleidungsstueck orangeHandschuhe =  dao.findById(9L);
        orangeHandschuhe.setFarbe("Grün");
        dao.update(orangeHandschuhe);

        Kleidungsstueck ksx =dao.findById(5L);
        dao.delete(ksx);

        Kleidungsstueck ksCopy =new Kleidungsstueck(); //transient
        ksCopy.setFarbe(ksx.getFarbe());
        ksCopy.setBezeichnung(ksx.getBezeichnung());
        ksCopy.setFarbe("GElb");
        dao.save(ksCopy);


        // Hibernate SessionFactory erzeugen
        sessionFactory = new Configuration().configure().buildSessionFactory();


        insertUser("Max", "maxi123");
        insertUser("Anna", "annie");

        // Alle User auslesen
        listAllUsers();


        /*
        // Books einfügen
        insertBook("978-3-16-148410-0", "Hibernate Basics");
        insertBook("978-1-23-456789-7", "Advanced Hibernate");

        // Alle Books auslesen
        listAllBooks();
        */
      //  listAllUserAliases();

        //System.out.println("User: " + getUserWithId_createQuery(1));
        //System.out.println("User: " + getUserWithId_session_get(1));

        /*
        User user1 = getUserWithId_session_get(1);
        Book book1 = getBookWithId_session_get(2);

        addFavoriteBook(user1,book1,9);
        listAllFavoriteBooks();




        TestKleidungsstuecke();

        KleidungsstueckDAO k = new KleidungsstueckDAO();
        Kleidungsstueck k1 = new Kleidungsstueck();
        k1.setBezeichnung("Hose");
        k1.setFarbe("Blau");
        k.update(k1);
*/

                // SessionFactory schließen
        //sessionFactory.close();
    }

    // Methode zum Einfügen eines Users
    public static void insertUser(String firstname, String alias) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = new User();
            user.setFirstname(firstname);
            user.setAlias(alias);

            session.persist(user);

            session.getTransaction().commit();
            System.out.println("User gespeichert: " + user);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    // Methode zum Auslesen aller User
    public static void listAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users;
            users = session.createQuery("SELECT u.firstname from User u", User.class)
                    .list();
            System.out.println("Alle User aus der DB:");
            for (User u : users) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertBook(String isbn, String title) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(title);

            session.persist(book);
            session.getTransaction().commit();

            System.out.println("Book gespeichert: " + book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listAllBooks() {
        try (Session session = sessionFactory.openSession()) {
            List<Book> books = session.createQuery("from Book", Book.class).list();
            System.out.println("Alle Books in der DB:");
            for (Book b : books) {
                System.out.println(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listAllUserAliases() {
        try (Session session = sessionFactory.openSession()) {
            // HQL: nur die alias-Spalte abfragen
            List<String> aliases = session.createQuery("SELECT u.alias FROM User u", String.class)
                    .list();

            System.out.println("Alle User-Aliases:");
            for (String alias : aliases) {
                System.out.println(alias);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUserWithId_createQuery(int id) {
        try (Session session = sessionFactory.openSession()) {
            // HQL-Abfrage: User mit bestimmter ID
            User user = session.createQuery("FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResult();  // gibt genau einen User oder null zurück

            if (user != null) {
                System.out.println("User mit ID " + id + ": " + user);
            } else {
                System.out.println("Kein User mit ID " + id + " gefunden.");
            }
            return  user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static User getUserWithId_session_get(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, (long) id);  // ID muss Long sein, falls dein User id ein Long ist

            if (user != null) {
                System.out.println("User mit ID " + id + ": " + user);
            } else {
                System.out.println("Kein User mit ID " + id + " gefunden.");
            }
            return  user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Book getBookWithId_session_get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Book book = session.get(Book.class, (long) id);
            return  book;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static void addFavoriteBook(User user, Book book, int stars) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            FavoriteBook favorite = new FavoriteBook();
            favorite.setUser(user);
            favorite.setBook(book);
            favorite.setStars(stars);

            session.persist(favorite);
            session.getTransaction().commit();

            System.out.println("FavoriteBook gespeichert: " + favorite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listAllFavoriteBooks() {
        try (Session session = sessionFactory.openSession()) {
            List<FavoriteBook> favorites = session.createQuery("FROM FavoriteBook", FavoriteBook.class).list();
            System.out.println("Alle Lieblingsbücher:");
            for (FavoriteBook f : favorites) {
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TestKleidungsstuecke(){

        KleidungsstueckDAO dao = new KleidungsstueckDAO();

        // CREATE
        Kleidungsstueck ks = new Kleidungsstueck("Jacke", "Blau");
        dao.save(ks);
        System.out.println("Gespeichert: " + ks);

        // READ
        Kleidungsstueck gefunden = dao.findById(ks.getId());
        System.out.println("Gefunden: " + gefunden);

        // UPDATE
        gefunden.setFarbe("Rot");
        dao.update(gefunden);
        System.out.println("Aktualisiert: " + dao.findById(gefunden.getId()));

        // DELETE
        dao.delete(gefunden);
        System.out.println("Nach dem Löschen: " + dao.findById(gefunden.getId()));
    }



}