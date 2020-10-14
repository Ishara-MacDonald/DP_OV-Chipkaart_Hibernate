package main.java.dao;

import main.java.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO{
    private Session currentSession;
    private Transaction currentTransaction;
    private SessionFactory sessionFactory;

    public ReizigerDAOHibernate(Session currentSession, Transaction currentTransaction){
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
    public boolean save(Reiziger reiziger) {
        try{
            currentSession.save(reiziger);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            currentSession.update(reiziger);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            currentSession.delete(reiziger);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        return (Reiziger) currentSession.createQuery("from Reiziger where reiziger_id=" + id).getSingleResult();
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
            return (List<Reiziger>) currentSession.createQuery(String.format("FROM Reiziger WHERE geboortedatum = '%s'", Date.valueOf(datum))).list();
    }

    @Override
    public List<Reiziger> findAll() {
        return (List<Reiziger>) currentSession.createQuery("from Reiziger").list();
    }

    @Override
    public void setAdao(AdresDAO adao) { }
    @Override
    public void setOVdao(OV_ChipkaartDAO ovdao) { }
}
