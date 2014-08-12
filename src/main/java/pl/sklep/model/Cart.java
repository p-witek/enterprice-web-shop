package pl.sklep.model;

import java.util.ArrayList;

/**
 * Created by piotr on 04.08.14.
 */
public class Cart {

    private int id_cart;
    private User user;
    private ArrayList<Product> products;
    private Order order;

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void addProduct(Product product){
        products.add(product);
    }
    public int getId_cart() {
        return id_cart;
    }
    public void setId_cart(int id_koszyk) {
        this.id_cart = id_koszyk;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public int getSummaryPrice(){
        int summaryPrice = 0;
        for (Product p : products){
            summaryPrice += p.getSummaryPrice();
        }
        return summaryPrice;
    }
    public String getSummaryPriceString(){
        return Integer.toString(getSummaryPrice());
    }
}
