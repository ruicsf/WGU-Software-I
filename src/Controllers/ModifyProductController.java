package Controllers;

import Model.*;
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
import java.util.Optional;
import java.util.ResourceBundle;
/**Controller class for modifying product GUI. */
public class ModifyProductController implements Initializable {

    public TextField modifyProductIdTxt;
    public TextField modifyProductIndex;
    public TableView modifySiblingPartsTable;
    @FXML
    private TextField modifyProductNameTxt;
    @FXML
    private TextField modifyProductInvTxt;
    @FXML
    private TextField modifyProductPriceTxt;
    @FXML
    private TextField modifyProductMaxTxt;
    @FXML
    private TextField modifyProductMinTxt;
    @FXML
    private TextField modifySearchTxt;
    @FXML
    private TableView<Part> modifyParentPartsTable;
    @FXML
    private TableColumn<Part, Integer> parentPartIdCol;
    @FXML
    private TableColumn<Part, String> parentPartNameCol;
    @FXML
    private TableColumn<Part, Integer> parentPartsInventoryCol;
    @FXML
    private TableColumn<Part, Double> parentPartPriceCol;
    @FXML
    private TableColumn<Part, Integer> siblingPartIDCol;
    @FXML
    private TableColumn<Part, String> siblingPartNameCol;
    @FXML
    private TableColumn<Part, Integer> siblingPartInventoryCol;
    @FXML
    private TableColumn<Part, Double> siblingPartPriceCol;

    Stage stage;
    Parent scene;
    /**
     *A list containing parts for main parts table
     * */
    private ObservableList<Part> modifyProductParentList = FXCollections.observableArrayList();
    /**
     *A list containing parts for associated parts table
     * */
    private ObservableList<Part> modifyProductSiblingList = FXCollections.observableArrayList();

    /**
     * Gets product data from selected product in the main screen and populates text fields in modify product screen
     * @param product Data from selected product populates to text fields.
     * @param selectProductIndex  array index of selected product
     * */
    public void sendProduct(Product product, int selectProductIndex) {
        this.modifyProductIdTxt.setText(String.valueOf(product.getId()));
        this.modifyProductNameTxt.setText(product.getName());
        this.modifyProductInvTxt.setText(String.valueOf(product.getStock()));
        this.modifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        this.modifyProductMinTxt.setText(String.valueOf(product.getMin()));
        this.modifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        modifyProductIndex.setText(String.valueOf(selectProductIndex));

        modifyProductSiblingList.addAll(product.getAllAssociatedParts());

        modifySiblingPartsTable.setItems(modifyProductSiblingList);

        for (Part part : modifyProductSiblingList) {
            modifyProductParentList.remove(part);
        }
    }
    /**Method for saving adding selected part to associated part table.
     * Removes selected part from table and adds it associated parts table.
     * @param event Add button clicked.
     */
    @FXML
    void modifyPartAddBtn(ActionEvent event) {
        Part selectedPart = modifyParentPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            modifyProductSiblingList.add(selectedPart);
            modifySiblingPartsTable.setItems(modifyProductSiblingList);
            modifyProductParentList.remove(selectedPart);
        }
    }
    /**
     *Method for existing out of modify product screen and returning to Main screen.
     * @param event Cancel Button Clicked.
     */
    @FXML
    void modifyProductCancelBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Method for updating product. Data validation is used to ensure correct data is entered each text field.
     * Saves updated product to allProducts array in inventory Class.
     * @param event Save button clicked.
     */
    @FXML
    void modifyProductSaveBtn(ActionEvent event) {
        try {
            int idS = Integer.parseInt(modifyProductIdTxt.getText());
            String nameS = modifyProductNameTxt.getText();
            int invS = Integer.parseInt(modifyProductInvTxt.getText());
            double priceS = Double.parseDouble(modifyProductPriceTxt.getText());
            int minS = Integer.parseInt(modifyProductMinTxt.getText());
            int maxS = Integer.parseInt(modifyProductMaxTxt.getText());

            if (modifyProductNameTxt.getText().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Modify Product Error.");
                error.setContentText("Please enter valid data in the Name field.");
                error.showAndWait();
                return;
            } else if (minS > maxS) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Modify Product Error.");
                error.setContentText("Min greater than Max.");
                error.showAndWait();
                return;
            } else if (minS < 0 || maxS < 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Modify Product Error.");
                error.setContentText("Min or Max must be a non-negative number.");
                error.showAndWait();
                return;
            } else if (invS < minS || invS > maxS) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Modify Product Error.");
                error.setContentText("Inv must be a number between Min and Max.");
                error.showAndWait();
                return;
            } else if (priceS < 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Modify Product Error.");
                error.setContentText("Price must be a non-negative number.");
                error.showAndWait();
                return;
            }

            Product newProduct = new Product(idS, nameS, priceS, invS, minS, maxS);
            newProduct.setId(idS);
            newProduct.getAllAssociatedParts().clear();
            newProduct.getAllAssociatedParts().addAll(modifyProductSiblingList);

            Inventory.updateProduct(Integer.parseInt(modifyProductIndex.getText()), newProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Data text field input error.");
            error.setContentText("Please enter valid data for all fields.");
            error.showAndWait();
        }
    }
    /**
     * Method for removing part from associated parts table, and adds it to available parts table. User must confirm removal.
     * @param event Remove Associated Part button is clicked.
     */
    @FXML
    void modifyRemoveAssociatedBtn(ActionEvent event) {

        modifySiblingPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please confirm you would like to remove selected part.");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            Part part = (Part) modifySiblingPartsTable.getSelectionModel().getSelectedItem();
            modifyProductParentList.add(part);
            modifyProductSiblingList.remove(part);
        }
    }
    /**
     * Method searches through parts table for a match in either the ID or Name.
     * Throws an alert pop-up if there isn't a match
     * @param event No button, runs when enter is pressed in Parts search text box.
     */
    public void modifySearchTxtOnAction(ActionEvent event) {
        if (modifySearchTxt.getText().isEmpty()) {
            modifyParentPartsTable.setItems(Inventory.getAllParts());

        } else if ((modifySearchTxt.getText()).matches("[0-9]+")) {
            modifyParentPartsTable.getSelectionModel().select(Inventory.lookupPartId(Integer.parseInt(modifySearchTxt.getText())));
        } else
            modifyParentPartsTable.setItems(Inventory.lookupPart(modifySearchTxt.getText()));
    }

    /**
     *Initialize method for the modify product page. Populates main table with data from allParts, and associated table with data saved to modifyProductSiblingList.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductParentList.addAll(Inventory.getAllParts());

        this.parentPartIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.parentPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.parentPartsInventoryCol.setCellValueFactory(new PropertyValueFactory("stock"));
        this.parentPartPriceCol.setCellValueFactory(new PropertyValueFactory("price"));

        modifyParentPartsTable.setItems(modifyProductParentList);

        this.siblingPartIDCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.siblingPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.siblingPartInventoryCol.setCellValueFactory(new PropertyValueFactory("stock"));
        this.siblingPartPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
    }

}