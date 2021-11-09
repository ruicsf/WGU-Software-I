package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
/**
 * Class for Inventory*/
public class Inventory {

    /**
     * Array for parts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Array for products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Filtered array list for parts
     */
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    /**
     * Filtered array list for products
     */
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * Method for getting all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * Method for getting all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /**
     * Method for getting all filtered parts
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }
    /**
     * Method for getting all filtered products
     */
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * Method for adding parts
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }
    /**
     * Method for adding products
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Method for searching parts by name. Returns filtered list if it matches search text
     *  @param name The part name to search.
     *  @return filteredList with matching part if found, empty list if not found.
     */
    public static ObservableList<Part> lookupPart(String name) {
        if (!(Inventory.getFilteredParts().isEmpty())) {
            Inventory.getFilteredParts().clear();
        }
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().toLowerCase().contains(name.toLowerCase())) {
                Inventory.getFilteredParts().add(part);
            }
        }
        return Inventory.getFilteredParts();
    }
    /**
     * Method for searching products by name. Returns filtered list if it matches search text
     * @param name The product name to search.
     * @return filteredList with matching part if found, empty list if not found.
     */
    public static ObservableList<Product> lookupProduct(String name) {
        if (!(Inventory.getFilteredProducts().isEmpty())) {
            Inventory.getFilteredProducts().clear();
        }
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                Inventory.getFilteredProducts().add(product);
            }
        }
        return Inventory.getFilteredProducts();
    }

    /**
     * Method for searching parts by ID. Returns filtered list if it matches search text
     * @param partId The part ID to search.
     * @return The part object, Information pop-up if not found indicating no match.
     */
    public static Part lookupPartId(int partId) {
        for (Part part : Inventory.getAllParts())
            if (part.getId() == partId) {
                return part;
            }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not Found");
        alert.setHeaderText("Part ID not found.");
        alert.setContentText("Part ID does not exist. Please try again.");
        alert.showAndWait();
        return null;
    }
    /**
     * Method for searching product by ID. Returns filtered list if it matches search text
     * @param ProductId The part ID to search.
     * @return The product object, Information pop-up if not found indicating no match.
     */
    public static Product lookupProductId(int ProductId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == ProductId)
                return product;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not Found");
        alert.setHeaderText("Product ID not found.");
        alert.setContentText("Product ID does not exist. Please try again.");
        alert.showAndWait();
        return null;
    }

    /**
     * Method for updating modified part.
     * @param index Part position in array.
     * @param newPart Part object to update.
     */
    public static void updatePart(int index, Part newPart) {
        Inventory.getAllParts().set(index, newPart);
    }
    /**
     * Method for updating modified product.
     * @param index Product position in array.
     * @param newProduct Product object to update.
     */
    public static void updateProduct(int index, Product newProduct) {
        Inventory.getAllProducts().set(index, newProduct);
    }

    /**
     * Method for deleting part.
     * @param selectedPart part to delete.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Method for deleting part.
     * @param selectedProduct part to delete.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

}

