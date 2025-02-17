package tp4;  
// Déclare le package dans lequel cette classe est définie.

import javafx.application.Application;  
// Importe la classe Application, qui est la base d'une application JavaFX.

import javafx.application.Platform;  
// Importe la classe Platform, utilisée pour gérer des tâches dans le thread JavaFX.

import javafx.scene.Scene;  
// Importe la classe Scene, qui représente une scène dans l'interface graphique.

import javafx.scene.control.*;  
// Importe les contrôles d'interface utilisateur comme Label, TextField, Button, etc.

import javafx.scene.layout.VBox;  
// Importe VBox, un conteneur qui aligne les éléments verticalement.

import javafx.stage.Stage;  
// Importe Stage, qui représente la fenêtre principale de l'application.

public class SellerAppContainer extends Application {  
    // Déclare la classe principale qui hérite de Application pour définir une application JavaFX.

    private String productName;  
    // Variable pour stocker le nom du produit à vendre.

    private double bestOffer = 0;  
    // Variable pour suivre la meilleure offre faite lors de l'enchère.

    public static void main(String[] args) {  
        // Méthode principale, point d'entrée de l'application.
        launch(args);  
        // Lance l'application JavaFX.
    }

    @Override
    public void start(Stage primaryStage) {  
        // Méthode principale de l'application JavaFX, appelée pour initialiser l'interface utilisateur.
        primaryStage.setTitle("Seller Application");  
        // Définit le titre de la fenêtre principale.

        // Champ de saisie pour le nom du produit
        Label productLabel = new Label("Enter Product Name:");  
        // Label qui invite l'utilisateur à saisir le nom du produit.
        TextField productNameField = new TextField();  
        // Champ de texte pour permettre la saisie du nom du produit.

        // Bouton pour lancer l'enchère
        Button launchAuctionButton = new Button("Launch Auction");  
        // Bouton pour démarrer le processus d'enchère.
        launchAuctionButton.setOnAction(e -> {  
            // Définit l'action à effectuer lorsque le bouton est cliqué.
            productName = productNameField.getText();  
            // Récupère le nom du produit saisi par l'utilisateur.
            if (productName.isEmpty()) {  
                // Vérifie si aucun nom n'a été saisi.
                showError("Please enter a product name.");  
                // Affiche un message d'erreur si le champ est vide.
            } else {  
                // Si un nom est saisi :
                launchAuction(primaryStage);  
                // Lance l'enchère.
            }
        });

        // Label pour afficher la meilleure offre
        Label bestOfferLabel = new Label("Best Offer: None yet.");  
        // Initialise un label indiquant qu'aucune offre n'a encore été faite.

        // Mise en page
        VBox vbox = new VBox(10, productLabel, productNameField, launchAuctionButton, bestOfferLabel);  
        // Crée une disposition verticale avec un espacement de 10 pixels entre les éléments.
        vbox.setPadding(new javafx.geometry.Insets(10));  
        // Ajoute une marge intérieure de 10 pixels autour du conteneur.

        Scene scene = new Scene(vbox, 300, 200);  
        // Crée une scène avec la disposition VBox et des dimensions de 300x200.
        primaryStage.setScene(scene);  
        // Associe la scène à la fenêtre principale.
        primaryStage.show();  
        // Affiche la fenêtre principale.
    }

    // Lance l'interface du commissaire-priseur (enchère)
    private void launchAuction(Stage sellerStage) {  
        // Méthode pour lancer l'interface d'enchère et gérer la transition entre les écrans.
        CommissairePriseurContainer auctionInterface = new CommissairePriseurContainer();  
        // Crée une instance de l'interface du commissaire-priseur.
        auctionInterface.setOnAuctionEnd(offer -> {  
            // Définit une action à effectuer lorsque l'enchère se termine.
            bestOffer = offer;  
            // Met à jour la meilleure offre reçue.
            Platform.runLater(() -> {  
                // Exécute la mise à jour dans le thread JavaFX.
                sellerStage.show();  
                // Rouvre la fenêtre du vendeur.
                ((Label) ((VBox) sellerStage.getScene().getRoot()).getChildren().get(3))  
                        .setText("Best Offer: " + bestOffer + " USD");  
                // Met à jour le label avec la meilleure offre.
            });
        });

        sellerStage.hide();  
        // Masque la fenêtre du vendeur.
        auctionInterface.start(new Stage());  
        // Lance une nouvelle fenêtre pour l'interface du commissaire-priseur.
    }

    // Affiche des messages d'erreur
    private void showError(String message) {  
        // Méthode pour afficher des messages d'erreur à l'utilisateur.
        Alert alert = new Alert(Alert.AlertType.ERROR);  
        // Crée une alerte de type erreur.
        alert.setTitle("Error");  
        // Définit le titre de la fenêtre d'erreur.
        alert.setHeaderText(null);  
        // Supprime le texte d'en-tête.
        alert.setContentText(message);  
        // Définit le contenu du message d'erreur.
        alert.showAndWait();  
        // Affiche l'alerte et attend une réponse de l'utilisateur.
    }
}
