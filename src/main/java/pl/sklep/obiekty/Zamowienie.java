package pl.sklep.obiekty;

import java.util.ArrayList;

/**
 * Created by piotr on 30.07.14.
 */
public class Zamowienie {

    private int id;
    private String data;
    private String adres;
    private ArrayList<Produkt> kupioneProdukty;

    public Zamowienie(){
        kupioneProdukty = new ArrayList<Produkt>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getAdres() {
        return adres;
    }

    public void dodajProdukt(Produkt p){
        kupioneProdukty.add(p);
    }

    public ArrayList<Produkt> getKupioneProdukty() {
        return kupioneProdukty;
    }
}
