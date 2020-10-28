package main.java.dao;

import main.java.domain.OV_Chipkaart;
import main.java.domain.Product;
import main.java.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private Session currentSession;
    private Transaction currentTransaction;
    private SessionFactory sessionFactory;

    public ProductDAOHibernate(Session currentSession, Transaction currentTransaction){
        this.currentSession = currentSession;
        this.currentTransaction = currentTransaction;
    }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public void closeConnections(){
        closeCurrentSession();
        closeCurrentSessionwithTransaction();
    }

    public void setCurrentTransaction(Transaction currentTransaction) { this.currentTransaction = currentTransaction; }
    public Transaction getCurrentTransaction() { return currentTransaction; }

    public void setCurrentSession(Session currentSession) { this.currentSession = currentSession; }
    public Session getCurrentSession() { return currentSession; }

    public void openCurrentSession() { currentSession = sessionFactory.openSession(); }

    public void openCurrentSessionwithTransaction() {
        currentSession = sessionFactory.openSession();
        currentTransaction = currentSession.beginTransaction();
    }

    public void closeCurrentSession() { currentSession.close(); }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    @Override
    public boolean save(Product product) {
        try{
            currentSession.save(product);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try{
            currentSession.update(product);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try{
            currentSession.delete(product);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product findById(int id) {
        return (Product) currentSession.createQuery("from Product where product_nummer =" + id).getSingleResult();
    }

    @Override
    public List<Product> findByOVChipkaart(OV_Chipkaart ovChipkaart) {
        String queryGetKaarten = String.format("SELECT prod.product_nummer, naam, beschrijving, prijs\n" +
                "FROM product prod\n" +
                "INNER JOIN ov_chipkaart_product ovproduct\n" +
                "ON prod.product_nummer = ovproduct.product_nummer\n" +
                "WHERE ovproduct.kaart_nummer = %s",ovChipkaart.getId());
        return (List<Product>) currentSession.createQuery(queryGetKaarten).list();
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) currentSession.createQuery("from Product").list();
    }

    @Override
    public void setRdao(ReizigerDAO rdao) { }
    @Override
    public void setOVdao(OV_ChipkaartDAO ovdao) { }
}
