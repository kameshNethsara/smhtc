package lk.ijse.gdse.smhtc.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.smhtc.bo.BOFactory;
import lk.ijse.gdse.smhtc.bo.custom.UserBO;

import java.io.IOException;
import java.util.Objects;

public class LoginControler {

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

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
    private JFXTextField txtPasswordVisible;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    void btnSigninOnAction(ActionEvent event) {
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

    @FXML
    void onMouseClickedForgetPassword(MouseEvent event) {
        try {
            // Load the SignupView.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login/ForgotPasswordView.fxml"));
            AnchorPane fpPane = fxmlLoader.load();

            // Create a new stage (popup window)
            Stage stage = new Stage();
            stage.setTitle("Forgot Password");

            // Set scene
            Scene scene = new Scene(fpPane);
            stage.setScene(scene);

            // Disable resizing
            stage.setResizable(false);

            // Optional: Make it a modal popup (blocks interaction with the login window until closed)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the popup
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Forgot Password window", e.getMessage());
        }
    }


    @FXML
    void signUpOnMouseClicked(MouseEvent event) {
        try {
            // Load the SignupView.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login/SignupView.fxml"));
            AnchorPane signupPane = fxmlLoader.load();

            // Create a new stage (popup window)
            Stage stage = new Stage();
            stage.setTitle("Sign Up");

            // Set scene
            Scene scene = new Scene(signupPane);
            stage.setScene(scene);

            // Disable resizing
            stage.setResizable(false);

            // Optional: Make it a modal popup (blocks interaction with the login window until closed)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the popup
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Sign Up window", e.getMessage());
        }
    }


    @FXML
    void setPasswordInvisible(MouseEvent event) {
        txtPasswordVisible.setVisible(false);
        txtPassword.setVisible(true);
        txtPassword.setText(txtPasswordVisible.getText());
        imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/eye-invisible-removebg.png")).toExternalForm()));
    }

    @FXML
    void setPasswordVisible(MouseEvent event) {
        txtPasswordVisible.setVisible(true);
        txtPassword.setVisible(false);
        txtPasswordVisible.setText(txtPassword.getText());
        imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/eye-visible-removebg.png")).toExternalForm()));
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
