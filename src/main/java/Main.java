package main.java;

import main.java.dao.*;
import main.java.domain.Adres;
import main.java.domain.OV_Chipkaart;
import main.java.domain.Product;
import main.java.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create a Hibernate session factory
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
        testDAOHibernate();
    }


    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println(o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static Session openNewSession(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        return factory.openSession();
    }
    private static Transaction openTransaction(Session session){
        return session.beginTransaction();
    }

    private static void testDAOHibernate(){
        Session session = openNewSession();
        Transaction transaction = openTransaction(session);
        ReizigerDAO rdao = new ReizigerDAOHibernate(session, transaction);
        ProductDAO pdao = new ProductDAOHibernate(session, transaction);
        OV_ChipkaartDAO odao = new OV_ChipkaartDAOHibernate(session, transaction);
        AdresDAO adao = new AdresDAOHibernate(session, transaction);

        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        printAllInList(reizigers);

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        LocalDate gbdatum = LocalDate.of(1981, 3,14);
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", gbdatum);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        System.out.println(rdao.findAll().size() + " reizigers");
        System.out.println();

        // Vind de nieuwe reiziger in de database en geef deze terug
        System.out.println(String.format("[Test] Systeem vind reiziger de volgende reiziger bij ReizigerDAO.findById(77):\n %s", rdao.findById(77).toString()));
        System.out.println();

        // Update de nieuwe reiziger in de database en geef deze terug
        sietske.setTussenvoegsel("de");
        sietske.setAchternaam("Boer");
        System.out.println("[Test] ReizigerDAO.update() geeft de volgende resultaten:\nVoor de update:");
        System.out.println(sietske);
        rdao.update(sietske);
        System.out.println("Na de update:");
        System.out.println(sietske);
        System.out.println();

        // Vind reizigers met de geboortedatum '2002-12-03'
        System.out.println("[Test] ReizigerDAO.findByGbdatum('2002-12-03') geeft de volgende reizigers:");
        printAllInList(rdao.findByGbdatum("2002-12-03"));

        // Verwijder de nieuwe reiziger en haal deze uit de database
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        System.out.println(rdao.findAll().size() + " reizigers");

        rdao.closeCurrentSessionwithTransaction();
        rdao.closeCurrentSession();


        System.out.println("\n---------- Test AdresDAO -------------");
        Reiziger newReiziger = new Reiziger(77, "S", "", "Boers", LocalDate.of(1981,3,14));
        Adres newAdres = new Adres(7, "3455XD", "10", "de Landlaan","Utrecht", newReiziger);
        rdao.save(newReiziger);

        // Haal alle reizigers op uit de database
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for(Adres a : adao.findAll()){
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        System.out.print(String.format("[Test] Eerst %s adressen, na AdresDAO.save()", adao.findAll().size()));
        adao.save(newAdres);
        System.out.println(String.format(" %s adressen", adao.findAll().size()));
        System.out.println();

        System.out.println("[Test] Systeem vind het volgende adres bij AdresDAO.findById(7):");
        System.out.println(adao.findById(7));
        System.out.println();

        System.out.println(String.format("[Test] AdresDAO.update() geeft de volgende resultaten:\nVoor de update: %s", adao.findById(7)));
        newAdres.setPostcode("2342DX");
        adao.update(newAdres);
        System.out.println(String.format("Na de update: %s", adao.findById(7)));
        System.out.println();

        System.out.println("[Test] AdresDAO.findByReiziger() geeft het volgende adres:");
        System.out.println(adao.findByReiziger(rdao.findById(1)));
        System.out.println();

        System.out.print(String.format("[Test] Eerst %s adressen, na AdresDAO.save()", adao.findAll().size()));
        adao.delete(newAdres);
        System.out.println(String.format(" %s adressen", adao.findAll().size()));

        System.out.println("\n---------- Test OVChipkaartDAO -------------");
        OV_Chipkaart newOVChipkaart1 = new OV_Chipkaart(23, LocalDate.of(2024,9,13), 1, (float) 50.0, newReiziger);
        OV_Chipkaart newOVChipkaart2 = new OV_Chipkaart(26, LocalDate.of(2024,9,13), 2, (float) 25.0, newReiziger);
//        Adres newAdres = new Adres(7, "3455XD", "10", "de Landlaan","Utrecht", 77);
        rdao.save(newReiziger);

        // Haal alle reizigers op uit de database
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende ov-chipkaarten:");
        printAllInList(odao.findAll());
        System.out.println();

        System.out.printf("[Test] Eerst %s ov-chipkaarten, 2x na OVChipkaartDAO.save() ", odao.findAll().size());
        odao.save(newOVChipkaart1);
        odao.save(newOVChipkaart2);
        System.out.printf("%s ov-chipkaarten", odao.findAll().size());
        System.out.println("\n");

        System.out.println("[Test] Systeem vind het volgende ov-chipkaart bij OVChipkaartDAO.findById(23):");
        System.out.println(odao.findById(23));
        System.out.println();

        System.out.printf("[Test] OVChipkaartDAO.update() geeft de volgende resultaten:\nVoor de update: %s%n", odao.findById(23));
        newOVChipkaart1.setKlasse(2);
        odao.update(newOVChipkaart1);
        System.out.printf("Na de update: %s\n", odao.findById(23));
        System.out.println();

        System.out.println("[Test] OVChipkaartDAO.findByReiziger() geeft het volgende ov-chipkaart:");
        printAllInList(odao.findByReiziger(newReiziger));
        System.out.println();

        rdao.save(newReiziger);
        newReiziger.addOVKaart(newOVChipkaart1);
        newReiziger.addOVKaart(newOVChipkaart2);

        Product productOne = new Product(25, "StudentenOV", "OV Product voor Studenten", 25.00f);

        productOne.addKaart(newOVChipkaart1);
        productOne.addKaart(newOVChipkaart2);

        OV_Chipkaart testKaart = odao.findById(35283);

        System.out.println("\n---------- Test ProductDAO -------------");
        System.out.println();

        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        printAllInList(pdao.findAll());
        System.out.println();

        System.out.printf("[Test] Eerst %s producten, na ProductDAO.save() ", pdao.findAll().size());
        pdao.save(productOne);
        System.out.printf("%s producten", pdao.findAll().size());
        System.out.println("\n");

        System.out.printf("[Test] ProductDAO.update() geeft de volgende resultaten:\nVoor de update: %s%n", pdao.findById(25));
        productOne.setNaam("Studenten Product");
        pdao.update(productOne);
        System.out.printf("Na de update: %s\n", pdao.findById(25));
        System.out.println();

        System.out.println("[Test] Systeem vind de volgende producten bij ProductDAO.findByOVChipkaart(35283)");
        printAllInList(pdao.findByOVChipkaart(testKaart));
        System.out.println();

        System.out.println("[Test] Systeem vind de volgende producten bij ProductDAO.findByOVChipkaart(33)");
        printAllInList(pdao.findByOVChipkaart(newOVChipkaart1));
        System.out.println();

        System.out.printf("[Test] Eerst %s producten, na ProductDAO.delete() ", pdao.findAll().size());
        pdao.delete(productOne);
        System.out.printf("%s producten", pdao.findAll().size());
        System.out.println("\n");

        System.out.println("[Test] OVChipkaartDAO.findByReiziger() na OVChipkaartDAO.delete()");
        odao.delete(newOVChipkaart1);
        printAllInList(odao.findByReiziger(newReiziger));
        System.out.println();

        System.out.printf("[Test] Eerst %s ov-chipkaarten, na OVChipkaartDAO.delete()", odao.findAll().size());
        odao.delete(newOVChipkaart2);
        System.out.printf(" %s ov-chipkaarten", odao.findAll().size());
        System.out.println();

        rdao.delete(newReiziger);

        rdao.closeConnections();
        adao.closeConnections();
        odao.closeConnections();
        pdao.closeConnections();
    }

    private static void printAllInList(List<?> objectList){
        for(Object object : objectList){
            System.out.println(object);
        }
    }

//    private static SessionFactory getSessionFactory() {
//        Configuration configuration = new Configuration().configure();
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties());
//        return configuration.buildSessionFactory(builder.build());
//    }
}
