/**
 * Created by thiago on 22/10/16.
 */

public class Product {
    private int id;
    private String name;
    private double price;
    private int toBuy;
    private int inStock;

    //Constructor
    public Product(String name,double price,int id){
        this.name = name;
        this.price = price;
        this.id = id;
        this.toBuy = 0;
        this.inStock = 0;
    }

    //Getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setToBuy(int toBuy) {
        this.toBuy = toBuy;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getInStock() {
        return inStock;
    }

    public int getToBuy() {
        return toBuy;
    }
}

