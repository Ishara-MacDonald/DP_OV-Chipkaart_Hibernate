package main.java.dao;

import main.java.domain.Adres;
import main.java.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO{
    private Session currentSession;
    private Transaction currentTransaction;
    private SessionFactory sessionFactory;

    public AdresDAOHibernate(Session currentSession, Transaction currentTransaction){
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
    public boolean save(Adres adres) {
        try{
            currentSession.save(adres);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try{
            currentSession.update(adres);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try{
            currentSession.delete(adres);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findById(int id) {
        return (Adres) currentSession.createQuery("FROM Adres WHERE adres_id=" + id).getSingleResult();
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        return (Adres) currentSession.createQuery("FROM Adres WHERE reiziger_id = " + reiziger.getId()).getSingleResult();
    }

    @Override
    public List<Adres> findAll() {
        return (List<Adres>) currentSession.createQuery("FROM Adres").list();
    }
}
