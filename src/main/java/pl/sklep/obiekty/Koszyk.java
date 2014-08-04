package pl.sklep.obiekty;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by piotr on 01.08.14.
 */
public class Koszyk extends ArrayList<Produkt> {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
