package tp4;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SellerInputInterface extends Application {
    // Observable list to store entered data (optional, can be used for logging or displaying)
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the stage
        primaryStage.setTitle("Seller Input Interface");

        // Create UI elements
        Label priceLabel = new Label("Enter Price:");
        TextField priceField = new TextField();
        Button submitButton = new Button("Submit Price");

        // Container for UI elements
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(priceLabel, priceField, submitButton);

        // Handle button click to submit price
        submitButton.setOnAction(event -> handlePriceSubmission(priceField));

        // Set up the scene and show the window
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handlePriceSubmission(TextField priceField) {
        String enteredPrice = priceField.getText();
        try {
            // Try parsing the price to ensure it's a valid number
            double price = Double.parseDouble(enteredPrice);
            // Display or process the price (here we just print it)
            System.out.println("Seller entered price: " + price);
            // You can also send the price to other agents or components of your system here
        } catch (NumberFormatException e) {
            // Handle invalid input
            showError("Invalid price entered. Please enter a valid number.");
        }
    }

    private void showError(String message) {
        // Display an error dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
