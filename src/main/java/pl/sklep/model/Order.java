package pl.sklep.model;

/**
 * Created by piotr on 04.08.14.
 */
public class Order {
    private String date;
    private String address;
    private int id;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getId() {
        return id;
    }
    public String getIdString(){
        return Integer.toString(id);
    }
    public void setId(int id) {
        this.id = id;
    }
}
