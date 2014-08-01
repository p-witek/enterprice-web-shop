package pl.sklep.obiekty;

import java.util.ArrayList;

/**
 * Created by piotr on 01.08.14.
 */
public class Koszyk {
    private int id;
    private ArrayList<Produkt> produkty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Produkt> getProdukty() {
        return produkty;
    }

    public void setProdukty(ArrayList<Produkt> produkty) {
        this.produkty = produkty;
    }

    public void add(Produkt prod){
        produkty.add(prod);
    }
}
