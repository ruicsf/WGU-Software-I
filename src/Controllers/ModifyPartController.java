//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Controller class for Modify Part GUI page
 */
public class ModifyPartController {

    @FXML
    private RadioButton modifyInHouseRadio;
    @FXML
    private RadioButton modifyOutsourcedRadio;
    @FXML
    private Label PartMachineIDModify;
    @FXML
    private TextField idTxtModify;
    @FXML
    private TextField partNameTxtModify;
    @FXML
    private TextField partInvTxtModify;
    @FXML
    private TextField partPriceTxtModify;
    @FXML
    private TextField partMaxTxtModify;
    @FXML
    private TextField partDynamicTxtModify;
    @FXML
    private TextField MPfieldIndex;
    @FXML
    private TextField partMinTxtModify;

    Stage stage;
    Parent scene;
    /**Method for changing text label .
     * @param event In House Radio Button Clicked
     */
    @FXML
    void onInHouseModify(ActionEvent event) {
        PartMachineIDModify.setText("Machine ID");
    }

    /**Method for changing text label.
     * @param event Outsourced Radio Button Clicked
     */
    @FXML
    void onOutsourcedModify(ActionEvent event) {
        PartMachineIDModify.setText("Company Name");
    }

    /**
     *Method for existing out of add part screen and returning to Main screen.
     * @param event Cancel Button Clicked.
     */
    @FXML
    public void toMainMenuBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Method for saving part. Data validation is used to ensure correct data is entered each text field.
     * Attempts to update data to allParts array in inventory Class.
     * @param event Save button clicked.
     */
    @FXML
    void partOnSaveBtnModify(ActionEvent event) throws IOException {
        try {
            int idS = Integer.parseInt(idTxtModify.getText());
            String nameS = partNameTxtModify.getText();
            int invS = Integer.parseInt(partInvTxtModify.getText());
            double priceS = Double.parseDouble(partPriceTxtModify.getText());
            int minS = Integer.parseInt(partMinTxtModify.getText());
            int maxS = Integer.parseInt(partMaxTxtModify.getText());

            if (modifyInHouseRadio.isSelected()) {
                int machineIdS = Integer.parseInt(partDynamicTxtModify.getText());
                InHouse modifyPartUpdate = new InHouse(idS, nameS, priceS, invS, minS, maxS, machineIdS);
                modifyPartUpdate.setId(idS);
                Inventory.updatePart(Integer.parseInt(MPfieldIndex.getText()), modifyPartUpdate);

            } else if (modifyOutsourcedRadio.isSelected()) {
                String modifyCompanyName = partDynamicTxtModify.getText();
                Outsourced outsourcedPart = new Outsourced(idS, nameS, priceS, invS, minS, maxS, modifyCompanyName);
                outsourcedPart.setId(idS);
                Inventory.updatePart(Integer.parseInt(MPfieldIndex.getText()), outsourcedPart);


                if (nameS.isEmpty()) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Modify Part Error.");
                    error.setContentText("Please enter valid data in the Name field.");
                    error.showAndWait();
                    return;
                } else if ((minS < 0) || (maxS < 0)) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Modify Part Error.");
                    error.setContentText("Min or Max must be a valid non-negative number.");
                    error.showAndWait();
                    return;
                } else if (minS > maxS) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Modify Part Error.");
                    error.setContentText("The value of Min must be less than Max.");
                    error.showAndWait();
                    return;
                } else if (invS < minS || invS > maxS) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Modify Part Error.");
                    error.setContentText("The value of Inv must be a number between Min and Max.");
                    error.showAndWait();
                    return;
                } else if (priceS < 0) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Modify Part Error.");
                    error.setContentText("Price must be a non-negative number.");
                    error.showAndWait();
                    return;
                }
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error: Input Data ");
            alert.setContentText("Enter valid values for all text fields.)");
            alert.show();
        }
    }

    /**
     * Gets part data from selected part in the main screen and populates text fields in modify part screen
     * @param part Data from selected part populates the text fields.
     * @param selectPartIndex  array index of selected part
     * */
    public void sendPart(Part part, int selectPartIndex) {
        if (part instanceof InHouse) {
            this.partDynamicTxtModify.setText(String.valueOf(((InHouse) part).getMachineID()));
            modifyInHouseRadio.setSelected(true);
        }

        if (part instanceof Outsourced) {
            this.partDynamicTxtModify.setText(((Outsourced) part).getCompanyName());
            modifyOutsourcedRadio.setSelected(true);
        }
        this.idTxtModify.setText(String.valueOf(part.getId()));
        this.partNameTxtModify.setText(part.getName());
        this.partInvTxtModify.setText(String.valueOf(part.getStock()));
        this.partPriceTxtModify.setText(String.valueOf(part.getPrice()));
        this.partMaxTxtModify.setText(String.valueOf(part.getMax()));
        this.partMinTxtModify.setText(String.valueOf(part.getMin()));
        MPfieldIndex.setText(String.valueOf(selectPartIndex));
    }
}

