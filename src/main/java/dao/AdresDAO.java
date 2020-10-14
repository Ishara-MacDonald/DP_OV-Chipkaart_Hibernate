package main.java.dao;

import java.util.List;
import main.java.domain.*;

public interface AdresDAO {
    public void openCurrentSessionwithTransaction();
    public void closeCurrentSessionwithTransaction();
    public void openCurrentSession();
    public void closeCurrentSession();
    public void closeConnections();

    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres);

    public Adres findById(int id);
    public Adres findByReiziger(Reiziger reiziger);
    public List<Adres> findAll();
}