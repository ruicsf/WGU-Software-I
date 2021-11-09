package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**Controller class for adding new parts to inventory. */
public class AddPartController {
    public TextField partNameTxt;
    public TextField partInvTxt;
    public TextField partPriceTxt;
    public TextField partMaxTxt;
    public TextField partDynamicTxt;
    public TextField partMinTxt;
    public TextField partIdText;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private Label addPartMachineID;

    Stage stage;
    Parent scene;

    /**
     *Method for existing out of add part screen and returning to Main screen.
     * @param event Cancel Button Clicked.
     */
    public void addPartCancelBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Method for changing text label .
     * @param event In House Radio Button Clicked
     */
    public void onInHouse(ActionEvent event) {
        addPartMachineID.setText("Machine ID");
    }
    /**Method for changing text label.
     * @param event Outsourced Radio Button Clicked
     */
    public void onOutsourced(ActionEvent event) {
        addPartMachineID.setText("Company Name");
    }
    /**Method for saving new part. Data validation is used to ensure correct data is entered each text field.
     * Saves new part data to allParts array in inventory Class.
     * @param event Save button clicked.
     */
    @FXML
    void partOnSaveBtn(ActionEvent event) throws IOException {
        try {
            int id = 0;
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() > id)
                    id = (part.getId());
                ++id;
            }
            String name = partNameTxt.getText();
            int inv = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int machineID;
            String CompanyName;

            if (name.isEmpty()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Part Error.");
                error.setContentText("Please enter valid data in the Name field.");
                error.showAndWait();
                return;
            } else if ((min < 0) || (max < 0)) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Part Error.");
                error.setContentText("Min or Max must be a valid non-negative number.");
                error.showAndWait();
                return;
            } else if (min > max) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Part Error.");
                error.setContentText("The value of Min must be less than Max.");
                error.showAndWait();
                return;
            } else if (inv < min || inv > max) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Part Error.");
                error.setContentText("The value of Inv must be a number between Min and Max.");
                error.showAndWait();
                return;
            } else if (price < 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Add Part Error.");
                error.setContentText("Price must be a non-negative number.");
                error.showAndWait();
                return;
            }

            try {
                if (inHouseRadio.isSelected()) {
                    machineID = Integer.parseInt(partDynamicTxt.getText());
                    InHouse newPartInHouse = new InHouse(id, name, price, inv, min, max, machineID);
                    Inventory.addPart(newPartInHouse);

                }

            } catch (Exception ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Error: Data Entry Error");
                error.setContentText("Invalid entry. Machine ID should be numbers only");
                error.showAndWait();
            }
            if (outsourcedRadio.isSelected()) {
                CompanyName = partDynamicTxt.getText();
                Outsourced newOutsourcedPart = new Outsourced(id, name, price, inv, min, max, CompanyName);
                Inventory.addPart(newOutsourcedPart);

            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error: Blank text fields");
            error.setContentText("Cannot have blank fields");
            error.showAndWait();
        }
    }

}
