package main.java.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Reiziger {

    @Id
    @Column(name="reiziger_id")
    private int id;

    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    @OneToOne(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
//            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Adres adres;

    @OneToMany(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
//            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<OV_Chipkaart> kaarten = new ArrayList<>();

//    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum){
//        this.id = id;
//        this.voorletters = voorletters;
//        this.tussenvoegsel = tussenvoegsel;
//        this.achternaam = achternaam;
//        this.geboortedatum = geboortedatum;
//    }
//
    public int getId() { return id; }
//    public String getVoorletters() { return voorletters; }
//    public String getTussenvoegsel() { return tussenvoegsel; }
//    public String getAchternaam() { return achternaam; }
//    public Date getGeboortedatum() { return geboortedatum; }
//    public Adres getAdres() { return adres; }
//
//    public List<OVChipkaart> getKaarten() { return kaarten; }
//
//    public void setId(int id) { this.id = id; }
//    public void setVoorletters(String voorletters) { this.voorletters = voorletters; }
//    public void setTussenvoegsel(String tussenvoegsel) { this.tussenvoegsel = tussenvoegsel; }
//    public void setAchternaam(String achternaam) { this.achternaam = achternaam; }
//    public void setGeboortedatum(Date geboortedatum) { this.geboortedatum = geboortedatum; }
//    public void setAdres(Adres adres) { this.adres = adres; }
//
//    public void deleteOVKaarten(){kaarten.clear();}
//    public void addOVKaart(OVChipkaart ovChipkaart){ kaarten.add(ovChipkaart); }
//    public void deleteOVKaart(OVChipkaart ovChipkaart){
//        kaarten.removeIf(kaart -> kaart.getId() == ovChipkaart.getId());
//    }
//
    public String toString() {
        String sString = String.format("Reiziger { #%s %s. %s %s, geb. %s ", id, voorletters, tussenvoegsel, achternaam, geboortedatum.toString());
        if(adres != null){
            sString = String.format("Reiziger { #%s %s. %s %s, geb. %s, %s ", id, voorletters, tussenvoegsel, achternaam, geboortedatum.toString(), adres.toString());
        }
        for(OV_Chipkaart kaart : kaarten){
            sString += "\n   " + kaart;
        }
        sString+="}";
        return sString;
    }
}
