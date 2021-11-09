package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/** Inventory management application main class.  The application can be used to create, edit and delete parts and product objects.
 *
 * @author Rui Fernandes
 *
 * FUTURE ENHANCEMENT To extend functionality I would make it to where the number of availabe parts in inventory is more accurate. For example, by subtracting parts associated with products when parts are added to selected products.
 * */

public class Main extends Application {
    /** Main class start method. Loads MainMenu.fxml */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainMenu.fxml"));
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
    /** Main class main method. Pre-populates Objects for part and products table. */
    public static void main(String[] args) {
        //Add part rows
        Inventory.getAllParts().add(new InHouse(1, "Brakes", 10, 15, 1, 22, 1));
        Inventory.getAllParts().add(new InHouse(2, "Wheel", 16, 11, 1, 22, 2));
        Inventory.getAllParts().add(new InHouse(3, "Seat", 10, 15, 1, 22, 3));
        //Add product rows
        Inventory.getAllProducts().add(new Product(1000, "Giant Bike", 299.99, 9, 1, 20));
        Inventory.getAllProducts().add(new Product(1001, "Tricycle", 99.99, 10, 1, 20));
        launch(args);
    }
}
