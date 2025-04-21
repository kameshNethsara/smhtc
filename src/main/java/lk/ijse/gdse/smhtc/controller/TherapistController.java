package lk.ijse.gdse.smhtc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.smhtc.bo.custom.TherapistBO;
import lk.ijse.gdse.smhtc.bo.custom.impl.TherapistBOImpl;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.dto.tm.TherapistTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistController implements Initializable {

    TherapistBO therapistBO = new TherapistBOImpl();

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
    private Button btnTherapistAvailability;

    @FXML
    private TableColumn<TherapistTM, String> colSpecialization;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistAddress;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistEmail;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistId;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistName;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistTele;

    @FXML
    private Label lblSpecialization;

    @FXML
    private Label lblTherapistAddress;

    @FXML
    private Label lblTherapistEmail;

    @FXML
    private Label lblTherapistId;

    @FXML
    private Label lblTherapistName;

    @FXML
    private Label lblTherapistTele;

    @FXML
    private TableView<TherapistTM> tblUser;

    @FXML
    private AnchorPane therapistPane;

    @FXML
    private TextField txtSpecialization;

    @FXML
    private TextField txtTherapistAddress;

    @FXML
    private TextField txtTherapistEmail;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    private TextField txtTherapistTele;

    private Button selectedButton = null; // Track the selected button

    @FXML
    void navigateToTherapistAvailability(ActionEvent event) {
        try {
            therapistPane.getChildren().clear();
            Parent TherapistAvailabilityView = FXMLLoader.load(getClass().getResource("/view/navigation/TherapistAvailabilityView.fxml"));
            therapistPane.getChildren().add(TherapistAvailabilityView);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact the developer").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String id = txtTherapistId.getText();
            boolean isDeleted = therapistBO.deleteTherapist(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.ERROR, "Delete Successfully", "Delete Therapist.");
                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", "could not delete therapist.");
            e.printStackTrace();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            if (!validateInputs()) return;
            TherapistDTO dto = new TherapistDTO(
                    txtTherapistId.getText(),
                    txtTherapistName.getText(),
                    txtTherapistTele.getText(),
                    txtTherapistEmail.getText(),
                    txtTherapistAddress.getText(),
                    txtSpecialization.getText()
            );
            boolean isSaved = therapistBO.saveTherapist(dto);
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Therapist Saved successfully.");

                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Save Failed", "Could not save user.");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
       String id = txtTherapistId.getText();
        therapistBO.findByTherapistId(id).ifPresent(therapist -> {
            txtTherapistName.setText(therapist.getName());
            txtTherapistTele.setText(therapist.getPhone());
            txtTherapistEmail.setText(therapist.getEmail());
            txtTherapistAddress.setText(therapist.getAddress());
            txtSpecialization.setText(therapist.getSpecialization());
        });
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            if (!validateInputs()) return;
            TherapistDTO dto = new TherapistDTO(
                    txtTherapistId.getText(),
                    txtTherapistName.getText(),
                    txtTherapistTele.getText(),
                    txtTherapistEmail.getText(),
                    txtTherapistAddress.getText(),
                    txtSpecialization.getText()
            );
            boolean isUpdated = therapistBO.updateTherapist(dto);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Therapist Updated successfully.");

                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Save Failed", "Could not Updated Therapist.");
            e.printStackTrace();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TherapistTM selected = tblUser.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtTherapistId.setText(selected.getId());
            txtTherapistName.setText(selected.getName());
            txtTherapistTele.setText(selected.getPhone());
            txtTherapistEmail.setText(selected.getEmail());
            txtTherapistAddress.setText(selected.getAddress());
            txtSpecialization.setText(selected.getSpecialization());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtTherapistId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistName, null));
        txtTherapistName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistTele, txtTherapistId));
        txtTherapistEmail.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistAddress, txtTherapistTele));
        txtTherapistAddress.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSpecialization, txtTherapistEmail));
        txtTherapistTele.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistEmail, txtTherapistName));
        txtSpecialization.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtTherapistAddress));

        setCellValueFactory();
        refreshTable();

        txtTherapistId.setText( therapistBO.getNextTherapistId());

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtTherapistId.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtTherapistName.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtTherapistTele.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtTherapistEmail.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtTherapistAddress.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtSpecialization.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());

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

    private void setCellValueFactory() {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTherapistTele.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTherapistEmail.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colTherapistAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
    }

    private void refreshTable() {
        try {
            List<TherapistDTO> list = therapistBO.getAllTherapists();
            ObservableList<TherapistTM> tmList = FXCollections.observableArrayList();

            for (TherapistDTO dto : list) {
                tmList.add(new TherapistTM(
                        dto.getId(),
                        dto.getName(),
                        dto.getPhone(),
                        dto.getEmail(),
                        dto.getAddress(),
                        dto.getSpecialization()
                ));
            }
            tblUser.setItems(tmList);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load therapists data: " + e.getMessage());
        }
    }

    private void refreshPage() {
        txtTherapistId.setText( therapistBO.getNextTherapistId());
        txtTherapistName.clear();
        txtTherapistTele.clear();
        txtTherapistEmail.clear();
        txtTherapistAddress.clear();
        txtSpecialization.clear();
        txtTherapistId.requestFocus();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInputs() {
        boolean valid = true;

        // Therapist ID: T001
        if (!txtTherapistId.getText().matches("^T\\d{3}$")) {
            setFieldError(txtTherapistId, true);
            valid = false;
        } else {
            setFieldError(txtTherapistId, false);
        }

        // Name: only letters and spaces
        if (!txtTherapistName.getText().matches("^[A-Za-z\\s]{3,}$")) {
            setFieldError(txtTherapistName, true);
            valid = false;
        } else {
            setFieldError(txtTherapistName, false);
        }

        // Phone: 10 digits starting with 0
        if (!txtTherapistTele.getText().matches("^(0\\d{9})$")) {
            setFieldError(txtTherapistTele, true);
            valid = false;
        } else {
            setFieldError(txtTherapistTele, false);
        }

        // Email
        if (!txtTherapistEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            setFieldError(txtTherapistEmail, true);
            valid = false;
        } else {
            setFieldError(txtTherapistEmail, false);
        }

        // Address: not empty
        if (txtTherapistAddress.getText().trim().isEmpty()) {
            setFieldError(txtTherapistAddress, true);
            valid = false;
        } else {
            setFieldError(txtTherapistAddress, false);
        }

        // Specialization: at least 3 letters
        if (!txtSpecialization.getText().matches("^[A-Za-z\\s]{3,}$")) {
            setFieldError(txtSpecialization, true);
            valid = false;
        } else {
            setFieldError(txtSpecialization, false);
        }

        return valid;
    }
    private void setFieldError(TextField field, boolean isError) {
        if (isError) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            field.setStyle("-fx-border-color: transparent;"); // reset to default
        }
    }



}
