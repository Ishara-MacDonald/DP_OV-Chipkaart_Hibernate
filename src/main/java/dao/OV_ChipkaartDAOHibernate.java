package main.java.dao;

import main.java.domain.Adres;
import main.java.domain.OV_Chipkaart;
import main.java.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class OV_ChipkaartDAOHibernate implements OV_ChipkaartDAO{
    private Session currentSession;
    private Transaction currentTransaction;
    private SessionFactory sessionFactory;

    public OV_ChipkaartDAOHibernate(Session currentSession, Transaction currentTransaction){
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
    public boolean save(OV_Chipkaart ovChipkaart) {
        try{
            currentSession.save(ovChipkaart);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OV_Chipkaart ovChipkaart) {
        try{
            currentSession.update(ovChipkaart);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OV_Chipkaart ovChipkaart) {
        try{
            currentSession.delete(ovChipkaart);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OV_Chipkaart findById(int id) {
        return (OV_Chipkaart) currentSession.createQuery("FROM OV_Chipkaart WHERE kaart_nummer=" + id).getSingleResult();
    }

    @Override
    public List<OV_Chipkaart> findByReiziger(Reiziger reiziger) {
        return (List<OV_Chipkaart>) currentSession.createQuery("FROM OV_Chipkaart WHERE reiziger_id = " + reiziger.getId()).list();
    }



    @Override
    public List<OV_Chipkaart> findAll() {
        return (List<OV_Chipkaart>) currentSession.createQuery("FROM OV_Chipkaart").list();
    }
}
