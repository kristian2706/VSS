package com.vss;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

public class Aufg35 extends Application {

    private String server;
    private String username;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        while (!showConnectionDialog()) {

        }

        primaryStage.setTitle("Chat Client (" + username + " at " + server + ")");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        TextField textField = new TextField();
        textField.setPromptText("Enter your message");
        textField.setPrefHeight(30);

        Button sendButton = new Button("Send");


        sendButton.setOnAction(e -> sendMessage(textField, textArea));
        textField.setOnAction(e -> sendMessage(textField, textArea));

        HBox inputBox = new HBox(5, textField, sendButton);

        BorderPane layout = new BorderPane();
        layout.setCenter(textArea);
        layout.setBottom(inputBox);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean showConnectionDialog() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connection Dialog");
        dialog.setHeaderText("Enter a username and IP Address");


        ButtonType connectButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(connectButtonType, ButtonType.CANCEL);

        // Username and IP address labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField ipAddressField = new TextField();
        ipAddressField.setPromptText("IP Address");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("IP Address:"), 0, 1);
        grid.add(ipAddressField, 1, 1);

        // Enable/Disable connect button depending on whether a username was entered.
        Node connectButton = dialog.getDialogPane().lookupButton(connectButtonType);
        connectButton.setDisable(true);

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            connectButton.setDisable(newValue.trim().isEmpty() || ipAddressField.getText().trim().isEmpty());
        });
        ipAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            connectButton.setDisable(newValue.trim().isEmpty() || usernameField.getText().trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> usernameField.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == connectButtonType) {
                return new Pair<>(usernameField.getText(), ipAddressField.getText());
            } else if (dialogButton == ButtonType.CANCEL) {
                System.exit(0);
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernameIpPair -> {
            server = usernameIpPair.getValue();
            username = usernameIpPair.getKey();
        });

        return result.isPresent() && server != null && username != null;
    }

    private void sendMessage(TextField textField, TextArea textArea) {
        String message = textField.getText().trim();
        if (!message.isEmpty()) {
            textArea.appendText(username + ": " + message + "\n");
            textField.clear();
        }
    }


}
