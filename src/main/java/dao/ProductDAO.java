package main.java.dao;

import java.util.List;
import main.java.domain.*;

public interface ProductDAO {
    public void openCurrentSessionwithTransaction();
    public void closeCurrentSessionwithTransaction();
    public void openCurrentSession();
    public void closeCurrentSession();
    public void closeConnections();

    public boolean save(Product product);
    public boolean update(Product product);
    public boolean delete(Product product);

    public void setRdao(ReizigerDAO reizigerDAO);
    public void setOVdao(OV_ChipkaartDAO oVdao);

    public Product findById(int id);
    public List<Product> findByOVChipkaart(OV_Chipkaart ovChipkaart);
    public List<Product> findAll();

}

