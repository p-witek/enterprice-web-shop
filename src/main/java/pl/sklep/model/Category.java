package pl.sklep.model;

/**
 * Created by piotr on 04.08.14.
 */
public class Category {
    private int id_category;
    private String name;

    public int getId_category() {
        return id_category;
    }
    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
