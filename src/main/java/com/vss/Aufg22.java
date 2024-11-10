package com.vss;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Aufg22 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
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
                textArea.appendText("You: " + message + "\n");
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
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
