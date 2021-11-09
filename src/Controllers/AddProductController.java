package Controllers;

import Model.*;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**Controller class for adding new product to inventory. */
public class AddProductController implements Initializable {

    @FXML
    private TextField addProductNameTxt;
    @FXML
    private TextField addProductInvTxt;
    @FXML
    private TextField addProductPriceTxt;
    @FXML
    private TextField addProductMaxTxt;
    @FXML
    private TextField addProductMinTxt;
    @FXML
    private TextField addProdSearchTxt;
    @FXML
    private TableView<Part> addProdParentTable;
    @FXML
    private TableColumn<Part, Integer> parentPartIdCol;
    @FXML
    private TableColumn<Part, String> parentPartNameCol;
    @FXML
    private TableColumn<Part, Integer> parentPartsInventoryCol;
    @FXML
    private TableColumn<Part, Double> parentPartPriceCol;
    @FXML
    private TableView<Part> addProdSiblingTable;
    @FXML
    private TableColumn<Part, Integer> siblingPartIDCol;
    @FXML
    private TableColumn<Part, String> siblingPartNameCol;
    @FXML
    private TableColumn<Part, Integer> siblingPartInventoryCol;
    @FXML
    private TableColumn<Part, Double> siblingPartPriceCol;

    private ObservableList<Part> productParentList = FXCollections.observableArrayList();
    private ObservableList<Part> productSiblingList = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;
    /**
     *Method for existing out of add product screen and returning to Main screen.
     * @param event Cancel Button Clicked.
     */
    @FXML
    void addProductCancelBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Method for saving new product. Data validation is used to ensure correct data is entered each text field.
     * Saves new product data to allProducts array in inventory Class.
     * @param event Save button clicked.
     */
    @FXML
    void addProductSaveBtn(ActionEvent event) throws IOException {
        try {
            int IdS = 1000;
            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() > IdS) {
                    IdS = product.getId();
                    ++IdS;
                }
            }
            String nameS = addProductNameTxt.getText();
            int invS = Integer.parseInt(addProductInvTxt.getText());
            double priceS = Double.parseDouble(addProductPriceTxt.getText());
            int minS = Integer.parseInt(addProductMinTxt.getText());
            int maxS = Integer.parseInt(addProductMaxTxt.getText());

            if (addProductNameTxt.getText().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Product Error.");
                error.setContentText("Please enter valid data in the Name field.");
                error.showAndWait();
                return;
            } else if (minS > maxS) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Product Error.");
                error.setContentText("Min greater than Max.");
                error.showAndWait();
                return;
            } else if (minS < 0 || maxS < 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Product Error.");
                error.setContentText("Min or Max must be a non-negative number.");
                error.showAndWait();
                return;
            } else if (invS < minS || invS > maxS) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Product Error.");
                error.setContentText("Inv must be a number between Min and Max.");
                error.showAndWait();
                return;
            } else if (priceS < 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Product Error.");
                error.setContentText("Price must be a non-negative number.");
                error.showAndWait();
                return;
            }

            Product newProduct = new Product(IdS, nameS, priceS, invS, minS, maxS);
            Inventory.addProduct(newProduct);
            for (Part part : productSiblingList) {
                newProduct.addAssociatedPart(part);

            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Data text field input error.");
            error.setContentText("Please enter valid data for all fields.");
            error.showAndWait();

        }

    }
    /**
     * Method adds selected part from all parts table to associated parts table.
     * @param event Add part button clicked. */
    @FXML
    void productAddPartBtn(ActionEvent event) {
        Part selectedPart = addProdParentTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            productSiblingList.add(selectedPart);
            addProdSiblingTable.setItems(productSiblingList);
            productParentList.remove(selectedPart);
        }
    }
    /**
     * Method c
     * @param event Remove part button clicked. */
    @FXML
    void productRemoveAssociatedPartBtn(ActionEvent event) {
        Part associatedPart = addProdSiblingTable.getSelectionModel().getSelectedItem();
        if (associatedPart != null) {
            productParentList.add(associatedPart);
            addProdParentTable.setItems(productParentList);
            productSiblingList.remove(associatedPart);
        }
    }
    /**
     * Method searches through parts table for a match in either the ID or Name.
     * Throws an alert pop-up if there isn't a match
     * @param event No button, runs when enter is pressed.
     */
    public void addProdSearchTxtOnAction(ActionEvent event) {
        if (addProdSearchTxt.getText().isEmpty()) {
            addProdParentTable.setItems(Inventory.getAllParts());

        } else if ((addProdSearchTxt.getText()).matches("[0-9]+")) {
            addProdParentTable.getSelectionModel().select(Inventory.lookupPartId(Integer.parseInt(addProdSearchTxt.getText())));
        } else
            addProdParentTable.setItems(Inventory.lookupPart(addProdSearchTxt.getText()));
    }
    /**
     * Initialize method for Add Product screen
     * Populates main and associated tables in add product screen
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productParentList.addAll(Inventory.getAllParts());

        addProdParentTable.setItems(productParentList);
        this.parentPartIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.parentPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.parentPartsInventoryCol.setCellValueFactory(new PropertyValueFactory("stock"));
        this.parentPartPriceCol.setCellValueFactory(new PropertyValueFactory("price"));

        addProdSiblingTable.setItems(productSiblingList);
        this.siblingPartIDCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.siblingPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.siblingPartInventoryCol.setCellValueFactory(new PropertyValueFactory("stock"));
        this.siblingPartPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
    }

}
