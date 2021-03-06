package pl.sklep.model;

/**
 * Created by piotr on 04.08.14.
 */
public class Product {
    private int id;
    private String name;
    private int price;
    private Category category;
    private int amount;

    public int getId() {
        return id;
    }
    public String getIdString(){
        return Integer.toString(id);
    }
    public void setId(int id_product) {
        this.id = id_product;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getAmountString(){
        return Integer.toString(amount);
    }
    public String getPriceString(){
        return Integer.toString(price);
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public int getSummaryPrice(){
        return amount*price;
    }
    public String getSummaryPriceString(){
        return Integer.toString(amount*price);
    }
}
