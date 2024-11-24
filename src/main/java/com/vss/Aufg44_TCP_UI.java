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

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

public class Aufg44_TCP_UI extends Application {

    private String server;
    private String username;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

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

        new Thread(() -> receiveMessages(textArea)).start();
    }

    private boolean showConnectionDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connection Dialog");
        dialog.setHeaderText("Enter a username and IP Address");

        ButtonType connectButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(connectButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setText("localhost");
        TextField ipAddressField = new TextField();
        ipAddressField.setText("127.0.0.1");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("IP Address:"), 0, 1);
        grid.add(ipAddressField, 1, 1);

        Node connectButton = dialog.getDialogPane().lookupButton(connectButtonType);
        connectButton.setDisable(usernameField.getText().trim().isEmpty() || ipAddressField.getText().trim().isEmpty());

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
            try {
                socket = new Socket(server, 4711);
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return result.isPresent() && server != null && username != null;
    }

    private void sendMessage(TextField textField, TextArea textArea) {
        String message = textField.getText().trim();
        if (!message.isEmpty()) {
            String fullMessage = username + ": " + message;
            textArea.appendText(fullMessage + "\n");
            textField.clear();
            writer.println(fullMessage);
        }
    }

    private void receiveMessages(TextArea textArea) {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> textArea.appendText(finalMessage + "\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}