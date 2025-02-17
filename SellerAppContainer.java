package tp4;  
// D�clare le package dans lequel cette classe est d�finie.

import javafx.application.Application;  
// Importe la classe Application, qui est la base d'une application JavaFX.

import javafx.application.Platform;  
// Importe la classe Platform, utilis�e pour g�rer des t�ches dans le thread JavaFX.

import javafx.scene.Scene;  
// Importe la classe Scene, qui repr�sente une sc�ne dans l'interface graphique.

import javafx.scene.control.*;  
// Importe les contr�les d'interface utilisateur comme Label, TextField, Button, etc.

import javafx.scene.layout.VBox;  
// Importe VBox, un conteneur qui aligne les �l�ments verticalement.

import javafx.stage.Stage;  
// Importe Stage, qui repr�sente la fen�tre principale de l'application.

public class SellerAppContainer extends Application {  
    // D�clare la classe principale qui h�rite de Application pour d�finir une application JavaFX.

    private String productName;  
    // Variable pour stocker le nom du produit � vendre.

    private double bestOffer = 0;  
    // Variable pour suivre la meilleure offre faite lors de l'ench�re.

    public static void main(String[] args) {  
        // M�thode principale, point d'entr�e de l'application.
        launch(args);  
        // Lance l'application JavaFX.
    }

    @Override
    public void start(Stage primaryStage) {  
        // M�thode principale de l'application JavaFX, appel�e pour initialiser l'interface utilisateur.
        primaryStage.setTitle("Seller Application");  
        // D�finit le titre de la fen�tre principale.

        // Champ de saisie pour le nom du produit
        Label productLabel = new Label("Enter Product Name:");  
        // Label qui invite l'utilisateur � saisir le nom du produit.
        TextField productNameField = new TextField();  
        // Champ de texte pour permettre la saisie du nom du produit.

        // Bouton pour lancer l'ench�re
        Button launchAuctionButton = new Button("Launch Auction");  
        // Bouton pour d�marrer le processus d'ench�re.
        launchAuctionButton.setOnAction(e -> {  
            // D�finit l'action � effectuer lorsque le bouton est cliqu�.
            productName = productNameField.getText();  
            // R�cup�re le nom du produit saisi par l'utilisateur.
            if (productName.isEmpty()) {  
                // V�rifie si aucun nom n'a �t� saisi.
                showError("Please enter a product name.");  
                // Affiche un message d'erreur si le champ est vide.
            } else {  
                // Si un nom est saisi :
                launchAuction(primaryStage);  
                // Lance l'ench�re.
            }
        });

        // Label pour afficher la meilleure offre
        Label bestOfferLabel = new Label("Best Offer: None yet.");  
        // Initialise un label indiquant qu'aucune offre n'a encore �t� faite.

        // Mise en page
        VBox vbox = new VBox(10, productLabel, productNameField, launchAuctionButton, bestOfferLabel);  
        // Cr�e une disposition verticale avec un espacement de 10 pixels entre les �l�ments.
        vbox.setPadding(new javafx.geometry.Insets(10));  
        // Ajoute une marge int�rieure de 10 pixels autour du conteneur.

        Scene scene = new Scene(vbox, 300, 200);  
        // Cr�e une sc�ne avec la disposition VBox et des dimensions de 300x200.
        primaryStage.setScene(scene);  
        // Associe la sc�ne � la fen�tre principale.
        primaryStage.show();  
        // Affiche la fen�tre principale.
    }

    // Lance l'interface du commissaire-priseur (ench�re)
    private void launchAuction(Stage sellerStage) {  
        // M�thode pour lancer l'interface d'ench�re et g�rer la transition entre les �crans.
        CommissairePriseurContainer auctionInterface = new CommissairePriseurContainer();  
        // Cr�e une instance de l'interface du commissaire-priseur.
        auctionInterface.setOnAuctionEnd(offer -> {  
            // D�finit une action � effectuer lorsque l'ench�re se termine.
            bestOffer = offer;  
            // Met � jour la meilleure offre re�ue.
            Platform.runLater(() -> {  
                // Ex�cute la mise � jour dans le thread JavaFX.
                sellerStage.show();  
                // Rouvre la fen�tre du vendeur.
                ((Label) ((VBox) sellerStage.getScene().getRoot()).getChildren().get(3))  
                        .setText("Best Offer: " + bestOffer + " USD");  
                // Met � jour le label avec la meilleure offre.
            });
        });

        sellerStage.hide();  
        // Masque la fen�tre du vendeur.
        auctionInterface.start(new Stage());  
        // Lance une nouvelle fen�tre pour l'interface du commissaire-priseur.
    }

    // Affiche des messages d'erreur
    private void showError(String message) {  
        // M�thode pour afficher des messages d'erreur � l'utilisateur.
        Alert alert = new Alert(Alert.AlertType.ERROR);  
        // Cr�e une alerte de type erreur.
        alert.setTitle("Error");  
        // D�finit le titre de la fen�tre d'erreur.
        alert.setHeaderText(null);  
        // Supprime le texte d'en-t�te.
        alert.setContentText(message);  
        // D�finit le contenu du message d'erreur.
        alert.showAndWait();  
        // Affiche l'alerte et attend une r�ponse de l'utilisateur.
    }
}
