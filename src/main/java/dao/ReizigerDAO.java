package main.java.dao;

import java.util.List;
import main.java.domain.*;

public interface ReizigerDAO {
    public void openCurrentSessionwithTransaction();
    public void closeCurrentSessionwithTransaction();
    public void openCurrentSession();
    public void closeCurrentSession();
    public void closeConnections();

    public boolean save(Reiziger reiziger);
    public boolean update(Reiziger reiziger);
    public boolean delete(Reiziger reiziger);

    public void setAdao(AdresDAO adresDAO);
    public void setOVdao(OV_ChipkaartDAO oVdao);

        public Reiziger findById(int id);
    public List<Reiziger> findByGbdatum(String datum);
    public List<Reiziger> findAll();
}

