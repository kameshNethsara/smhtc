package lk.ijse.gdse.smhtc.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.smhtc.bo.custom.UserBO;
import lk.ijse.gdse.smhtc.bo.custom.impl.UserBOImpl;

import java.io.IOException;
import java.sql.SQLException;

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

    UserBO userBO = new UserBOImpl();
    @FXML
    void btnSigninOnAction(ActionEvent event) throws IOException {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            // Attempt login via BO
            userBO.checklogin(username, password).ifPresentOrElse(role -> {
                try {
                    checkJobPosition(role); // Load the appropriate dashboard
                } catch (IOException e) {
                    showAlert("Error", "Unable to load dashboard", e.getMessage());
                    e.printStackTrace();
                }
            }, () -> {
                // If login fails
                showAlert("Login Failed", "Invalid Credentials", "Please check your username and password.");
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Something went wrong", e.getMessage());
        }
    }

    private void checkJobPosition(String role) throws IOException {
        AnchorPane load;

        switch (role) {
            case "Admin" -> load = FXMLLoader.load(getClass().getResource("/view/panel/AdminView.fxml"));
            case "Receptionist" -> load = FXMLLoader.load(getClass().getResource("/view/panel/ReceptionistView.fxml"));
            default -> {
                showAlert("Access Denied", "Unknown Role", "User role not recognized.");
                return;
            }
        }

        loginPanel.getChildren().clear();
        loginPanel.getChildren().add(load);

    }

    // Reusable alert method
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
