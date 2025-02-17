package tp4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class CommissairePriseurContainer extends Application {
    private ObservableList<String> messagesList;
    private double bestOffer = 0;  // To store the best offer price
    private Consumer<Double> onAuctionEnd; // Callback for auction end

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        messagesList = FXCollections.observableArrayList();

        primaryStage.setTitle("Commissaire-Priseur");

        BorderPane borderPane = new BorderPane();
        ListView<String> messagesView = new ListView<>(messagesList);
        VBox vbox = new VBox(10, messagesView);
        borderPane.setCenter(vbox);

        Button showBestOfferButton = new Button("Show Best Offer");
        showBestOfferButton.setOnAction(e -> showBestOffer(primaryStage));

        Button enterPriceButton = new Button("Enter Price");
        enterPriceButton.setOnAction(e -> openSellerInputInterface());

        VBox buttonBox = new VBox(10, showBestOfferButton, enterPriceButton);
        borderPane.setBottom(buttonBox);

        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    // Method to show the best offer and end the auction
    private void showBestOffer(Stage auctionStage) {
        if (bestOffer > 0) {
            messagesList.add("Best offer: " + bestOffer + " USD");
        } else {
            messagesList.add("No offer entered yet.");
        }

        // End the auction and notify the seller interface
        if (onAuctionEnd != null) {
            onAuctionEnd.accept(bestOffer);
        }

        auctionStage.close(); // Close the auction interface
    }

    // Method to open the seller input interface where the seller enters their price
    private void openSellerInputInterface() {
        Stage sellerStage = new Stage();
        sellerStage.setTitle("Seller Input Interface");

        Label priceLabel = new Label("Enter Price:");
        TextField priceField = new TextField();
        Button submitButton = new Button("Submit Price");

        VBox vbox = new VBox(10, priceLabel, priceField, submitButton);

        submitButton.setOnAction(event -> handlePriceSubmission(priceField, sellerStage));

        Scene scene = new Scene(vbox, 300, 200);
        sellerStage.setScene(scene);
        sellerStage.show();
    }

    // Handle the price submission by the seller
    private void handlePriceSubmission(TextField priceField, Stage sellerStage) {
        String enteredPrice = priceField.getText();
        try {
            double price = Double.parseDouble(enteredPrice);
            bestOffer = Math.max(bestOffer, price); // Update the best offer with the entered price
            messagesList.add("New offer: " + price + " USD");
            sellerStage.close();
        } catch (NumberFormatException e) {
            showError("Invalid price entered. Please enter a valid number.");
        }
    }

    // Set a callback to notify when the auction ends
    public void setOnAuctionEnd(Consumer<Double> onAuctionEnd) {
        this.onAuctionEnd = onAuctionEnd;
    }

    // Display an error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
