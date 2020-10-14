package main.java.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @Column(name="product_nummer")
    private int id;
    private String naam;
    private float prijs;
    private String beschrijving;

    @ManyToMany
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = @JoinColumn(name = "product_nummer"),
            inverseJoinColumns = @JoinColumn(name = "kaart_nummer")
    )
    private List<OV_Chipkaart> kaarten = new ArrayList<>();

//    public Product(int id, String naam, String beschrijving, float prijs){
//        this.id = id;
//        this.naam = naam;
//        this.beschrijving = beschrijving;
//        this.prijs = prijs;
//    }
//
//    public int getId() { return id; }
//    public String getNaam() { return naam; }
//    public String getBeschrijving() { return beschrijving; }
//    public float getPrijs() { return prijs; }
//    public ArrayList<OVChipkaart> getKaarten() { return kaarten; }
//
//    public void setId(int id) { this.id = id; }
//    public void setNaam(String naam) { this.naam = naam; }
//    public void setBeschrijving(String beschrijving) { this.beschrijving = beschrijving; }
//    public void setPrijs(float prijs) { this.prijs = prijs; }
//    public void setKaarten(ArrayList<OVChipkaart> kaarten) { this.kaarten = kaarten; }
//
//    public boolean addKaart(OVChipkaart kaart){ return kaarten.add(kaart); }
//    public void deleteCard(OVChipkaart ovChipkaart){
//        kaarten.removeIf(kaart -> kaart.getId() == ovChipkaart.getId());
//    }

    public String toString() {
        String sString = String.format("Product { #%s, naam: %s, beschrijving: %s, prijs: %s", id, naam, beschrijving, prijs);

        for(OV_Chipkaart kaart : kaarten){
            sString += ",\n   " + kaart;
        }
        sString += " }";

        return sString;
    }
}
