//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** Primary screen for application. Allows user to Add, Delete, or Modify Products or Parts */
public class MainMenuController implements Initializable {

    public TextField productsSearchTxt;
    public TextField partsSearchTxt;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInventoryCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    Stage stage;
    Parent scene;
    /**
     * Method closes  application
     * @param event Button for exiting application clicked
     */
    @FXML
    void InventoryOnExitBtn(ActionEvent event) throws IOException {
        System.exit(0);
    }

    /**
     * Method loads AddPartForm.fxml.
     * @param event Add button clicked for adding new part
     */
    @FXML
    void partOnAddBtn(ActionEvent event) throws IOException {
        this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        this.scene = (Parent) FXMLLoader.load(this.getClass().getResource("/views/AddPartForm.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }
    /**
     * Method deletes selected part from table.
     *If no part is selected throws an error pop-up box. User must confirm deletion of selected part.
     * @param event Delete button pressed for Parts table array.
     */
    @FXML
    void partOnDeleteBtn(ActionEvent event) {

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Selection error");
            errorAlert.setContentText("Please select one of the items.");
            errorAlert.show();
        } else {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setTitle("Alert");
            alertConfirmation.setHeaderText("Part Deletion Warning");
            alertConfirmation.setContentText("Press OK to confirm part deletion.");
            Optional<ButtonType> confirm = alertConfirmation.showAndWait();

            if (confirm.isPresent() && (confirm.get() == ButtonType.OK)) {
                Inventory.deletePart(selectedPart);
            }

        }
    }
    /**
     * Method loads modify parts screen and transfers selected part details to modify part screen.
     * Throws a warning pop-up if no part is selected
     * @param event Modify part button clicked
     */
    @FXML
    void partOnModifyBtn(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/views/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.sendPart(this.partsTable.getSelectionModel().getSelectedItem(), partsTable.getSelectionModel().getSelectedIndex());

            this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            this.scene = loader.getRoot();
            this.stage.setScene(new Scene(this.scene));
            this.stage.show();

        } catch (NullPointerException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Selection error");
            errorAlert.setContentText("Please select one of the items.");
            errorAlert.show();
        }

    }
    /**
     * Method searches through parts table for a match in either the ID or Name.
     * Throws an alert pop-up if there isn't a match
     * @param event No button, runs when enter is pressed in Parts search text box.
     */
    @FXML
    public void partsSearchOnAction(ActionEvent event) {

        if (partsSearchTxt.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());

        } else if ((partsSearchTxt.getText()).matches("[0-9]+")) {
            partsTable.getSelectionModel().select(Inventory.lookupPartId(Integer.parseInt(partsSearchTxt.getText())));
        } else
            partsTable.setItems(Inventory.lookupPart(partsSearchTxt.getText()));
    }

    // PRODUCTS
    /**
     * Method loads AddProductForm.fxml.
     * @param event Add button clicked for adding new Product
     */
    @FXML
    void productOnAddBtn(ActionEvent event) throws IOException {
        this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        this.scene = FXMLLoader.load(this.getClass().getResource("/views/AddProductForm.fxml"));
        this.stage.setScene(new Scene(this.scene));
        this.stage.show();
    }
    /**
     * Method deletes selected Product from table.
     *If no Product is selected a warning pop-up box is thrown. User must confirm deletion of selected Product.
     * @param event Delete button pressed for Product table array.
     */

    @FXML
    void productOnDeleteBtn(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Selection Error");
            error.setHeaderText("Error: No product selected");
            error.setContentText("Please select a product to delete");
            error.showAndWait();
        } else {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setTitle("Alert");
            alertConfirmation.setHeaderText("Product Deletion Warning");
            alertConfirmation.setContentText("Press OK to confirm product deletion.");
            Optional<ButtonType> confirm = alertConfirmation.showAndWait();

            if (!selectedProduct.getAllAssociatedParts().isEmpty()){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Deletion Error");
                error.setHeaderText("Error: Associated Part");
                error.setContentText("Please remove associated parts from product before deleting");
                error.showAndWait();
                return;
            }

            if (confirm.isPresent() && (confirm.get() == ButtonType.OK)) {
                Inventory.deleteProduct(selectedProduct);
            }
        }

    }
    /**
     * Method loads modify Product screen and transfers selected product data to modify Product screen.
     * Throws a warning pop-up if no Product is selected
     * @param event Modify Product button clicked
     */
    @FXML
    void productOnModifyBtn(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/views/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.sendProduct(this.productsTable.getSelectionModel().getSelectedItem(), productsTable.getSelectionModel().getSelectedIndex());

            this.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            this.scene = loader.getRoot();
            this.stage.setScene(new Scene(this.scene));
            this.stage.show();

        } catch (NullPointerException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Selection error");
            errorAlert.setContentText("Please select one of the Products.");
            errorAlert.show();
        }
    }
    /**
     * Method searches through Product table for a match in either the ID or Name.
     * Throws an alert pop-up if there isn't a match
     * @param event No button, runs when enter is pressed in Product search text box.
     */
    @FXML
    public void productSearchOnAction(ActionEvent event) {
        if (productsSearchTxt.getText().isEmpty()) {
            productsTable.setItems(Inventory.getAllProducts());
        } else if (productsSearchTxt.getText().matches("[0-9]+")) {
            productsTable.getSelectionModel().select(Inventory.lookupProductId(Integer.parseInt(productsSearchTxt.getText())));
        } else {
            productsTable.setItems(Inventory.lookupProduct(productsSearchTxt.getText()));
        }
    }
    /**Initialize method for Main page.
     * Gets the data from Inventory class allProducts and allParts arrays, and populates Products and Parts table.
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.partsTable.setItems(Inventory.getAllParts());
        this.productsTable.setItems(Inventory.getAllProducts());
        this.partIDCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.partNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.partsInventoryCol.setCellValueFactory(new PropertyValueFactory("stock"));
        this.partsPriceCol.setCellValueFactory(new PropertyValueFactory("price"));

        this.productIDCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.productNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.productInventoryCol.setCellValueFactory(new PropertyValueFactory("stock"));
        this.productPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
        this.partsTable.getSelectionModel().select(Inventory.lookupPartId(1));
    }

}
