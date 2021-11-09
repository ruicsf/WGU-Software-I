package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for Products
 */
public class Product {
/** RUNTIME ERROR: While saving a product only after adding an associated part to a product, I kept getting a nullPointerException error.
 * I used the debugger to see when the error was occurring and noticed it was happening when it in the process of adding the part to associatedParts list.
 * The reason for this was because I forgot to make associatedParts = to an FXCollections.observableArrayList*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Method adds associated part to associated part list. Used for adding part to array
     * @param part used for adding part object to associated part table.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**Method deletes part from associated part array.
     * @param selectedAssociatedPart Deletes selected associated part.
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        return associatedParts.remove(selectedAssociatedPart);
    }
    /**Method returns all associated parts from array.
     * @return associatedParts array.
     * */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}

