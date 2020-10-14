package main.java.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Adres {

    @Id
    @Column(name="adres_id")
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    private int reiziger_id;

//    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id){
//        this.id = id;
//        this.postcode = postcode;
//        this.huisnummer = huisnummer;
//        this.straat = straat;
//        this.woonplaats = woonplaats;
//        this.reiziger_id = reiziger_id;
//    }
//
//    public int getId() { return id; }
//    public String getPostcode() { return postcode; }
//    public String getHuisnummer() { return huisnummer; }
//    public String getStraat() { return straat; }
//    public String getWoonplaats() { return woonplaats; }
//    public int getReiziger_id() { return reiziger_id; }
//
//    public void setId(int id) { this.id = id; }
//    public void setPostcode(String postcode) { this.postcode = postcode; }
//    public void setHuisnummer(String huisnummer) { this.huisnummer = huisnummer; }
//    public void setStraat(String straat) { this.straat = straat; }
//    public void setWoonplaats(String woonplaats) { this.woonplaats = woonplaats; }
//    public void setReiziger_id(int reiziger_id) { this.reiziger_id = reiziger_id; }
//
    @Override
    public String toString() {
        return String.format("Adres { #%s %s %s %s %s } ", id, postcode, straat, huisnummer, woonplaats);
    }
}