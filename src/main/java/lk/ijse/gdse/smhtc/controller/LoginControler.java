package lk.ijse.gdse.smhtc.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginControler {

    @FXML
    private Pane btnPane;

    @FXML
    private JFXButton btnSignin;

    @FXML
    private ImageView imgEye;

    @FXML
    private Label lblForgetPassword;

    @FXML
    private Label lblSignUp;

    @FXML
    private AnchorPane loginPanel;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    void btnSigninOnAction(ActionEvent event) throws IOException {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        AnchorPane load;

        if (("a".equals(username) && "a".equals(password))) {
            loginPanel.getChildren().clear();
            load = FXMLLoader.load(getClass().getResource("/view/panel/AdminView.fxml"));
            //load = FXMLLoader.load(getClass().getResource("/view/navigation/TherapistView.fxml"));

        } else {
            loginPanel.getChildren().clear();
            load = FXMLLoader.load(getClass().getResource("/view/panel/ReceptionistView.fxml"));
            //load = FXMLLoader.load(getClass().getResource("/view/navigation/TherapistView.fxml"));
        }

        loginPanel.getChildren().add(load);
    }

    @FXML
    void onMouseClickedForgetPassword(MouseEvent event) {

    }

    @FXML
    void setPasswordInvisible(MouseEvent event) {

    }

    @FXML
    void setPasswordVisible(MouseEvent event) {

    }

    @FXML
    void initialize() {
        EneterPressed();
    }
    void EneterPressed() {
        txtUserName.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> btnSignin.fire();
            }
        });

        txtPassword.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> btnSignin.fire();
            }
        });
    }

}
