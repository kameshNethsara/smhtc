package lk.ijse.gdse.smhtc.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.smhtc.bo.custom.UserBO;
import lk.ijse.gdse.smhtc.bo.custom.impl.UserBOImpl;
import lk.ijse.gdse.smhtc.dto.UserDTO;
import lk.ijse.gdse.smhtc.dto.tm.UserTM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    UserBO userBO = new UserBOImpl();

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbJobRole;

    @FXML
    private TableColumn<UserTM, String> colUserEmail;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableColumn<UserTM, String> colUserJobRole;

    @FXML
    private TableColumn<UserTM, String> colUserName;

    @FXML
    private TableColumn<UserTM, String> colUserPassword;

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
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtUserEmail;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserPassword;

    @FXML
    private AnchorPane userPane;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
//        boolean isDeleted = userBO.deleteUser(txtUserId.getText());
//        if (isDeleted) {
//            refreshTable();
//            refreshPage();
//        }
        try {
            boolean isDeleted = userBO.deleteUser(txtUserId.getText());
            if (isDeleted) {
                showAlert(Alert.AlertType.ERROR, "Delete Successfully", "Delete User.");
                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", "could not delete User.");
            e.printStackTrace();
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
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
            showAlert(Alert.AlertType.INFORMATION, "Success", "User saved successfully.");
            refreshTable();
            refreshPage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Save Failed", "Could not save user.");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtUserId.getText();
        userBO.findByUserId(id).ifPresent(user -> {
            txtUserName.setText(user.getUsername());
            txtUserPassword.setText(user.getPassword());
            cmbJobRole.setValue(user.getRole());
            txtUserEmail.setText(user.getEmail());
        });
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!validateInputs()) return;

        UserDTO userDTO = new UserDTO(
                txtUserId.getText(),
                txtUserName.getText(),
                txtUserPassword.getText(),
                cmbJobRole.getValue(),
                txtUserEmail.getText()
        );
        boolean isUpdated = userBO.updateUser(userDTO);
        if (isUpdated) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User Updated successfully.");
            refreshTable();
            refreshPage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Save Failed", "Could not Updated user.");
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

    @FXML
    void onClickTable(MouseEvent event) {
        UserTM selectedItem = tblUser.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtUserId.setText(selectedItem.getId());
            txtUserName.setText(selectedItem.getUsername());
            txtUserPassword.setText(selectedItem.getPassword());
            cmbJobRole.setValue(selectedItem.getRole());
            txtUserEmail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    //    private void refreshTable() {
//        List<UserDTO> allUsers = userBO.getAll();
//        List<UserTM> tmList = allUsers.stream()
//                .map(dto -> new UserTM(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getRole(), dto.getEmail()))
//                .toList();
//        tblUser.getItems().setAll(tmList);
//    }
    private void refreshTable() {
        try {
            List<UserDTO> allUsers = userBO.getAllUser();
            System.out.println("Fetched users: " + allUsers.size()); // Check the count
            List<UserTM> tmList = allUsers.stream()
                    .map(dto -> new UserTM(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getRole(), dto.getEmail()))
                    .toList();
            tblUser.getItems().setAll(tmList);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load users: " + e.getMessage());
        }
    }

    private void refreshPage() {
        //txtUserId.clear();
        txtUserId.setText(userBO.getNextUserId());
        txtUserName.clear();
        txtUserPassword.clear();
        //cmbJobRole.getSelectionModel().clearSelection();
        cmbJobRole.setValue("Admin"); // Reset to default instead of clear
        txtUserEmail.clear();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

        private void initializeTable() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colUserJobRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void handleArrowKeyNavigation(javafx.scene.input.KeyEvent event, TextField next, TextField previous) {
        switch (event.getCode()) {
            case DOWN:
                if (next != null) next.requestFocus();
                break;
            case UP:
                if (previous != null) previous.requestFocus();
                break;
        }
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
        txtUserId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtUserName, cmbJobRole.getEditor()));
        txtUserName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtUserPassword, txtUserId));
        txtUserPassword.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtUserEmail, txtUserName));
        txtUserEmail.setOnKeyPressed(event -> handleArrowKeyNavigation(event, cmbJobRole.getEditor(), txtUserPassword));
        cmbJobRole.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtUserId, txtUserEmail));

        cmbJobRole.getItems().addAll("Admin", "Receptionist");
        cmbJobRole.setValue("Admin"); // Set default value

        refreshTable();
        initializeTable();

        txtUserId.setText( userBO.getNextUserId());

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

    }
}



