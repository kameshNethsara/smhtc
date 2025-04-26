package lk.ijse.gdse.smhtc.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.gdse.smhtc.bo.BOFactory;
import lk.ijse.gdse.smhtc.bo.custom.UserBO;
import lk.ijse.gdse.smhtc.dto.UserDTO;
import lk.ijse.gdse.smhtc.util.SendMailUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    UserDTO userDTO = new UserDTO();
    @FXML
    private JFXButton btnConformOTP;

    @FXML
    private JFXButton btnConformUserName;

    @FXML
    private JFXButton btnSendOTP;

    @FXML
    private JFXButton btnSignin;

    @FXML
    private Label lblEmail;

    @FXML
    private JFXTextField txtOTP;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtPassword1;

    @FXML
    private JFXTextField txtUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassword.setVisible(false);
        txtPassword1.setVisible(false);
        btnSignin.setVisible(false);
    }

    @FXML
    void btnConformUserNameOnAction(ActionEvent event) throws SQLException {
        String username = txtUserName.getText();
        String newPassword = txtPassword.getText();
        String confirmPassword = txtPassword1.getText(); // confirm field eka gannawa

        if (!username.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            if (newPassword.equals(confirmPassword)) {
                boolean isUpdated = userBO.updateUserPassword(username, newPassword);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!", ButtonType.OK).showAndWait();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Password update failed!", ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Password and Confirm Password do not match!", ButtonType.OK).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Username, Password and Confirm Password must be filled!", ButtonType.OK).showAndWait();
        }
    }

    static String otp;
    @FXML
    void btnSendOTPOnAction(ActionEvent event) {

        String Email = lblEmail.getText();
        otp = createOtp();
        SendMailUtil.sendEmailWithGmail(Email, "Your SpiceUp verification code is: ", otp);
    }

    private String createOtp() {
        Random rand = new Random();
        int randomNum = rand.nextInt(999999);
        return String.valueOf(randomNum);
    }

    @FXML
    void btnConformOTPOnAction(ActionEvent event) {
        if(txtOTP.getText().equals(otp)) {
            txtPassword.setVisible(true);
            txtPassword1.setVisible(true);
            btnSignin.setVisible(true);
        } else {
            new Alert(Alert.AlertType.WARNING, "OTP is invalid!", ButtonType.OK).showAndWait();
        }

    }

    @FXML
    void btnSigninOnAction(ActionEvent event) {

        if(!txtPassword.getText().equals(txtPassword1.getText())) {
            new Alert(Alert.AlertType.WARNING, "Password Mismatch , Different Password", ButtonType.OK).showAndWait();
        }else{

            boolean isUpdated = userBO.updateUserPassword(txtUserName.getText(),txtPassword.getText());
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Password changed successfully!", ButtonType.OK).showAndWait();
                // CLOSE the popup window after successful save
                Button sourceButton = (Button) event.getSource();
                Stage stage = (Stage) sourceButton.getScene().getWindow();
                stage.close();
            }else{ new Alert(Alert.AlertType.ERROR, "Password change Failed!", ButtonType.OK).showAndWait();}
        }
    }
}
