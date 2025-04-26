package lk.ijse.gdse.smhtc.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.gdse.smhtc.bo.BOFactory;
import lk.ijse.gdse.smhtc.bo.custom.UserBO;
import lk.ijse.gdse.smhtc.dto.UserDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    private Button btnCreate;

    @FXML
    private JFXComboBox<String> cmbJobRole;

    @FXML
    private Label lblJobRole;

    @FXML
    private Label lblUserEmail;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblUserPassword;

    @FXML
    private TextField txtUserEmail;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserPassword;

    public void btnCreateOnAction(ActionEvent actionEvent){
        if (!validateInputs()) return;

        UserDTO userDTO = new UserDTO(
                txtUserId.getText(),
                txtUserName.getText(),
                txtUserPassword.getText(),
                cmbJobRole.getValue(),
                txtUserEmail.getText()
        );
        boolean isSaved = userBO.saveUser(userDTO);
        if (isSaved) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "New user account create successfully.");
            refreshPage();
            // CLOSE the popup window after successful save
            Button sourceButton = (Button) actionEvent.getSource();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed", "Could not create new user account.");
        }
    }

    @FXML
    void handleComboBoxAction(ActionEvent event) {
        String selectedRole = cmbJobRole.getValue().toString();
        if (selectedRole == null) {
            System.out.println("No role selected");
            return; // Exit if no selection
        }

        switch (selectedRole) {
            case "Admin":
                System.out.println("Admin selected");
                break;
            case "Receptionist":
                System.out.println("Receptionist selected");
                break;
            default:
                System.out.println("Other role selected: " + selectedRole);
        }
    }

    private void refreshPage() {
        txtUserId.setText(userBO.getNextUserId());
        txtUserName.clear();
        txtUserPassword.clear();
        //cmbJobRole.getSelectionModel().clearSelection();
        cmbJobRole.setValue("Admin"); // Reset to default instead of clear
        txtUserEmail.clear();

    }

    private boolean validateInputs() {
        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String password = txtUserPassword.getText();
        String email = txtUserEmail.getText();
        String role = cmbJobRole.getValue();

        String idRegex = "^U\\d{3}$";
        String nameRegex = "^[A-Za-z ]{3,}$";
        String passwordRegex = "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{6,}$";
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        // Reset borders first
        txtUserId.setStyle("-fx-border-color: transparent;");
        txtUserName.setStyle("-fx-border-color: transparent;");
        txtUserPassword.setStyle("-fx-border-color: transparent;");
        txtUserEmail.setStyle("-fx-border-color: transparent;");

        if (!id.matches(idRegex)) {
            txtUserId.setStyle("-fx-border-color: red;");
            showAlert(Alert.AlertType.ERROR, "Invalid User ID", "User ID must be in format U001.");
            txtUserId.requestFocus();
            return false;
        }

        if (!name.matches(nameRegex)) {
            txtUserName.setStyle("-fx-border-color: red;");
            showAlert(Alert.AlertType.ERROR, "Invalid Name", "Name must be at least 3 letters and only contain letters.");
            txtUserName.requestFocus();
            return false;
        }

        if (!password.matches(passwordRegex)) {
            txtUserPassword.setStyle("-fx-border-color: red;");
            showAlert(Alert.AlertType.ERROR, "Invalid Password", "Password must be at least 6 characters (letters/digits/symbols).");
            txtUserPassword.requestFocus();
            return false;
        }

        if (!email.matches(emailRegex)) {
            txtUserEmail.setStyle("-fx-border-color: red;");
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Enter a valid email address.");
            txtUserEmail.requestFocus();
            return false;
        }

        if (role == null || role.isEmpty()) {
            cmbJobRole.setStyle("-fx-border-color: red;");
            showAlert(Alert.AlertType.ERROR, "Invalid Role", "Please select a job role.");
            cmbJobRole.requestFocus();
            return false;
        } else {
            cmbJobRole.setStyle("-fx-border-color: transparent;");
        }

        return true; // All inputs are valid
    }


    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbJobRole.getItems().addAll("Admin", "Receptionist");
        cmbJobRole.setValue("Admin"); // Set default value

        txtUserId.setText( userBO.getNextUserId());
    }


}


