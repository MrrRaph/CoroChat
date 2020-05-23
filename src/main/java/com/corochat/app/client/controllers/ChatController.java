package com.corochat.app.client.controllers;

import animatefx.animation.ZoomOutDown;
import com.corochat.app.client.models.UserModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    private String username="userModel";

    @FXML
    private AnchorPane anchRoot;
    @FXML
    private Pane pnlChat;
    @FXML
    private Circle btnClose;
    @FXML
    private Circle btnReduce;
    @FXML
    private Button btnSend;
    @FXML
    private TextFlow emojiList;
    @FXML
    private Button btnEmoji;
    @FXML
    private TextArea txtMsg;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox textBox;
    @FXML
    private AnchorPane chatPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Node text : emojiList.getChildren()){
            text.setOnMouseClicked(event -> {
                if(txtMsg.getText().equals(""))
                    txtMsg.setText(txtMsg.getText()+""+((Text)text).getText());
                else
                    txtMsg.setText(txtMsg.getText()+" "+((Text)text).getText());
                emojiList.setVisible(false);
            });
        }

        scrollPane.vvalueProperty().bind(textBox.heightProperty());
    }

    @FXML
    public void handleCloseAction(MouseEvent event) {
        if (event.getSource() == this.btnClose) {
            ZoomOutDown zoomOutDown = new ZoomOutDown(this.anchRoot);
            zoomOutDown.setOnFinished(e -> System.exit(0));
            zoomOutDown.play();
        }
    }

    @FXML
    public void handleReduceAction(MouseEvent event) {
        if (event.getSource() == this.btnReduce) {
            ((Stage)((Circle) event.getSource()).getScene().getWindow()).setIconified(true);
        }
    }

    public void handleEmojiAction(ActionEvent actionEvent) {
        if(emojiList.isVisible()){
            emojiList.setVisible(false);
        }else {
            emojiList.setVisible(true);
        }
    }

    public void handleSendAction(ActionEvent actionEvent) {

        System.out.println(txtMsg.getText().trim());

        Text text=new Text(txtMsg.getText());
        text.setFill(Color.BLACK);
        text.getStyleClass().add("message");
        TextFlow flow=new TextFlow();

        flow.getChildren().add(text);
        flow.setMaxWidth(200);

        HBox hbox=new HBox(12);

        flow.getStyleClass().add("textFlow");
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(flow);

        hbox.getStyleClass().add("hbox");
        textBox.toFront();
        Platform.runLater(() -> textBox.getChildren().addAll(hbox));

        txtMsg.setText("");
        txtMsg.requestFocus();
    }

    public void handleEnterKey(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println(txtMsg.getText().trim());
            txtMsg.selectAll();
            txtMsg.requestFocus();
            txtMsg.setText("");
        }
    }
}
