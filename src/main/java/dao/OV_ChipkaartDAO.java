package main.java.dao;

import main.java.domain.*;
import java.util.List;

public interface OV_ChipkaartDAO {
    public void openCurrentSessionwithTransaction();
    public void closeCurrentSessionwithTransaction();
    public void openCurrentSession();
    public void closeCurrentSession();
    public void closeConnections();

    public boolean save(OV_Chipkaart ovChipkaart);
    public boolean update(OV_Chipkaart ovChipkaart);
    public boolean delete(OV_Chipkaart ovChipkaart);

    public OV_Chipkaart findById(int id);
    public List<OV_Chipkaart> findByReiziger(Reiziger reiziger);
    public List<OV_Chipkaart> findAll();
}
