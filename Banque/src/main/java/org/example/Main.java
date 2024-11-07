package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private double balance = 0.0; // Initial balance
    private Label balanceLabel; // Label to display balance on the main page

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Bank Management System");

        // Show the main menu
        showMainMenu();
    }

    private void showMainMenu() {
        // Create main menu layout
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-alignment: center;");

        // Display current balance
        balanceLabel = new Label("Balance: " + String.format("%.2f", balance) + " USD");
        balanceLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        grid.add(balanceLabel, 0, 0, 2, 1);

        // Create buttons for sections
        Button banqueButton = createLargeButton("Banque", this::showBanqueSection);
        Button clientButton = createLargeButton("Client", this::showClientSection);
        Button compteButton = createLargeButton("Compte", this::showCompteSection);
        Button transactionButton = createLargeButton("Transaction", this::showTransactionSection);
        Button rechercheClientButton = createLargeButton("Recherche Client", this::showRechercheClientSection);

        // Add buttons to the grid
        grid.add(banqueButton, 0, 1);
        grid.add(clientButton, 1, 1);
        grid.add(compteButton, 0, 2);
        grid.add(transactionButton, 1, 2);
        grid.add(rechercheClientButton, 0, 3, 2, 1);

        // Set up the main menu scene
        Scene mainScene = new Scene(grid, 800, 500); // Larger scene
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void showBanqueSection() {
        VBox layout = createFormWithRetourButton("Banque Section");
        layout.getChildren().addAll(
                new Label("Nom de la Banque:"), new TextField(),
                new Label("id"), new TextField(),
                new Label("Pays"), new TextField()
        );
        addSubmitButton(layout);
    }

    private void showClientSection() {
        VBox layout = createFormWithRetourButton("Client Section");
        layout.getChildren().addAll(
                new Label("Nom:"), new TextField(),
                new Label("Prénom:"), new TextField(),
                new Label("Adresse:"), new TextField(),
                new Label("Numéro de Téléphone:"), new TextField(),
                new Label("Email:"), new TextField()
        );
        addSubmitButton(layout);
    }

    private void showCompteSection() {
        VBox layout = createFormWithRetourButton("Compte Section");

        // Create input fields for account creation
        TextField creationDateField = new TextField();
        TextField updateDateField = new TextField();
        TextField currencyField = new TextField();
        TextField initialBalanceField = new TextField(); // Field for initial balance

        // Add the input fields to the layout
        layout.getChildren().addAll(
                new Label("Date de Creation:"), creationDateField,
                new Label("Date Update:"), updateDateField,
                new Label("Devise:"), currencyField,
                new Label("Solde Initial:"), initialBalanceField
        );

        // Add the Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            try {
                // Get the initial balance entered by the user
                double initialBalance = Double.parseDouble(initialBalanceField.getText());

                // Update the balance
                balance = initialBalance;

                // Optionally, print the account creation details for debugging
                System.out.println("Account created with initial balance: " + initialBalance);

                // After account creation, go back to the main menu
                showMainMenu();
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter a valid initial balance.");
            }
        });

        layout.getChildren().add(submitButton);

        Scene scene = new Scene(layout, 800, 500); // Larger form
        primaryStage.setScene(scene);
    }

    private void showTransactionSection() {
        VBox layout = createFormWithRetourButton("Transaction Section");

        // Add Transaction-specific fields
        TextField amountField = new TextField();
        ComboBox<String> typeField = new ComboBox<>();
        typeField.getItems().addAll("Deposit", "Withdrawal");

        layout.getChildren().addAll(
                new Label("Reference:"), new TextField(),
                new Label("Pays de la banque qui va recevoir l'argent:"), typeField,
                new Label("Montant:"), amountField,
                new Label("ID du receveur de l'argent:"), new TextField()
        );

        // Submit button logic for transactions
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String type = typeField.getValue();

                if (type == null) {
                    showAlert("Transaction Error", "Please select a transaction type.");
                    return;
                }

                // Update balance based on transaction type
                if (type.equals("Deposit")) {
                    balance += amount;
                    showTransactionSuccessAlert(type); // Show success alert for Deposit
                } else if (type.equals("Withdrawal")) {
                    if (balance >= amount) {
                        balance -= amount;
                        showTransactionSuccessAlert(type); // Show success alert for Withdrawal
                    } else {
                        showAlert("Transaction Error", "Insufficient funds for withdrawal.");
                        return;
                    }
                }

                // Update balance on the main menu and return
                showMainMenu();
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Please enter a valid amount.");
            }
        });

        layout.getChildren().add(submitButton);

        Scene scene = new Scene(layout, 800, 500); // Larger form
        primaryStage.setScene(scene);
    }

    private void showTransactionSuccessAlert(String type) {
        // Create a success alert dialog with a custom icon
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transaction Successful");
        alert.setHeaderText(null);
        alert.setContentText("Transaction successful!");



        alert.showAndWait();
    }


    private void showRechercheClientSection() {
        VBox layout = createFormWithRetourButton("Recherche Client Section");
        layout.getChildren().addAll(
                new Label("ID Client:"), new TextField(),
                new Label("Nom:"), new TextField(),
                new Label("Prénom:"), new TextField()
        );
        addSubmitButton(layout);
    }

    private VBox createFormWithRetourButton(String sectionTitle) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        // Section title
        Label title = new Label(sectionTitle);
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Retour button
        Button retourButton = new Button("Retour");
        retourButton.setOnAction(event -> showMainMenu());

        layout.getChildren().addAll(title, retourButton);
        return layout;
    }

    private Button createLargeButton(String buttonText, Runnable action) {
        Button button = new Button(buttonText);
        button.setStyle("-fx-font-size: 18px; -fx-padding: 20px 40px; -fx-background-color: #87CEFA; -fx-border-color: #4682B4; -fx-border-width: 2px;");
        button.setOnAction(event -> action.run());
        return button;
    }

    private void addSubmitButton(VBox layout) {
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            System.out.println("Form submitted.");
            showMainMenu();
        });
        layout.getChildren().add(submitButton);

        Scene scene = new Scene(layout, 800, 500); // Larger scene
        primaryStage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
