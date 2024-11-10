package com.vss;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Aufg35 extends Application {

    private String server;
    private String username;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        if (!showLoginDialog()) {

            return;
        }

        primaryStage.setTitle("Chat Client (" + username + " at " + server + ")");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        TextField textField = new TextField();
        textField.setPrefWidth(350);
        textField.setPrefHeight(30);
        textField.setPromptText("Enter your message");

        Runnable sendMessage = () -> {
            String message = textField.getText();
            if (!message.trim().isEmpty()) {
                textArea.appendText(username + ": " + message + "\n");
                textField.clear();
            }
        };
        textField.setOnAction(e -> sendMessage.run());

        Button button = new Button("Send");
        button.setOnAction(e -> sendMessage.run());

        HBox hBox = new HBox(5, textField, button);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textArea);
        borderPane.setBottom(hBox);

        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean showLoginDialog() {
        Dialog<String> inputDialog = new Dialog<>();
        inputDialog.setTitle("Please enter your chat server and username");
        VBox inputs = new VBox(10);
        inputs.setPrefWidth(300);

        Label ipLbl = new Label("IP Address: ");
        TextField ip_input = new TextField();
        ip_input.setText("localhost"); // Default value

        Label userLbl = new Label("Username: ");
        TextField username_input = new TextField();
        username_input.setText("user"); // Default value

        inputs.getChildren().addAll(ipLbl, ip_input, userLbl, username_input);
        inputDialog.getDialogPane().setContent(inputs);
        inputDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        inputDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String serverIp = ip_input.getText().trim();
                String userName = username_input.getText().trim();

                if (!serverIp.isEmpty() && !userName.isEmpty()) {
                    server = serverIp;
                    username = userName;
                    return server + " " + username;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Both fields are required. Please try again.", ButtonType.OK);
                    alert.showAndWait();
                }
            } else if(dialogButton == ButtonType.CANCEL) {
                System.exit(0);
            }
            return null;
        });


        Optional<String> result;
        do {
            result = inputDialog.showAndWait();
            if (result.isEmpty()) {
                return false;
            }
        } while (server == null || username == null);

        return true;
    }
}
