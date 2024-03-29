package main.java.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class OV_Chipkaart {

    @Id
    @Column(name="kaart_nummer")
    private int id;
    private int klasse;
    private float saldo;
    private LocalDate geldig_tot;

    @ManyToOne
    @JoinColumn(name="reiziger_id")
    private Reiziger reiziger;

    @ManyToMany(
            mappedBy = "kaarten"
    )
    private List<Product> producten = new ArrayList<>();

    public OV_Chipkaart(int kaartnr, LocalDate geldig_tot, int klasse, float saldo, Reiziger reiziger){
        this.id = kaartnr;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OV_Chipkaart() {

    }

    //
    public int getId() { return id; }
    public LocalDate getGeldig_tot() { return geldig_tot; }
    public int getKlasse() { return klasse; }
    public float getSaldo() { return saldo; }
    public Reiziger getReiziger() { return reiziger; }
//    public List<Product> getProducten() { return producten; }
//
    public void setId(int id) { this.id = id; }
    public void setGeldig_tot(LocalDate geldig_tot) { this.geldig_tot = geldig_tot; }
    public void setKlasse(int klasse) { this.klasse = klasse; }
    public void setSaldo(float saldo) { this.saldo = saldo; }
    public void setReiziger(Reiziger reiziger){ this.reiziger = reiziger; }
//    public void setProducten(List<Product> producten) { this.producten = producten; }
//
//    public boolean addProduct(Product product){ return producten.add(product); }
//    public void deleteProduct(Product newProduct){
//        producten.removeIf(product -> product.getId() == newProduct.getId());
//    }
//
    public String toString() {
//        String sString =

//        for(Product product : producten){
//            sString += "\n" + product.getNaam();
//        }

//        sString += " }";
        return String.format("OVChipkaart { #%s geldig tot: %s, klasse: %s, saldo: %s}", id, geldig_tot, klasse, saldo);
    }
}
