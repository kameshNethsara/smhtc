package lk.ijse.gdse.smhtc.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.smhtc.bo.custom.PatientBO;
import lk.ijse.gdse.smhtc.bo.custom.impl.PatientBOImpl;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.dto.tm.PatientTM;
import lk.ijse.gdse.smhtc.dto.tm.TherapistTM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {
    PatientBO patientBO = new PatientBOImpl();

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
    private TableColumn<PatientTM, String> colPatientAddress;

    @FXML
    private TableColumn<PatientTM, String> colPatientEmail;

    @FXML
    private TableColumn<PatientTM, String> colPatientId;

    @FXML
    private TableColumn<PatientTM, String> colPatientMedicalHistory;

    @FXML
    private TableColumn<PatientTM, String> colPatientName;

    @FXML
    private TableColumn<PatientTM, String> colPatientTele;

    @FXML
    private Label lblMedicalHistory;

    @FXML
    private Label lblPatientAddress;

    @FXML
    private Label lblPatientEmail;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblPatientTele;

    @FXML
    private AnchorPane patientPane;

    @FXML
    private TableView<PatientTM> tblPatient;

    @FXML
    private TextField txtMedicalHistory;

    @FXML
    private TextField txtPatientAddress;

    @FXML
    private TextField txtPatientEmail;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPatientTele;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String id = txtPatientId.getText();
            boolean isDeleted = patientBO.deletePatient(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.ERROR, "Delete Successfully", "Delete Patient.");
                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", "could not delete Patient.");
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
            PatientDTO dto = new PatientDTO(
                    txtPatientId.getText(),
                    txtPatientName.getText(),
                    txtPatientTele.getText(),
                    txtPatientEmail.getText(),
                    txtPatientAddress.getText(),
                    txtMedicalHistory.getText()
            );
            boolean isSaved = patientBO.savePatient(dto);
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient Saved successfully.");

                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Save Failed", "Could not save Patient.");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtPatientId.getText();
        patientBO.findByPatientId(id).ifPresent(therapist -> {
            txtPatientName.setText(therapist.getName());
            txtPatientTele.setText(therapist.getPhone());
            txtPatientEmail.setText(therapist.getEmail());
            txtPatientAddress.setText(therapist.getAddress());
            txtMedicalHistory.setText(therapist.getMedicalHistory());
        });
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            if (!validateInputs()) return;
            PatientDTO dto = new PatientDTO(
                    txtPatientId.getText(),
                    txtPatientName.getText(),
                    txtPatientTele.getText(),
                    txtPatientEmail.getText(),
                    txtPatientAddress.getText(),
                    txtMedicalHistory.getText()
            );
            boolean isUpdated = patientBO.updatePatient(dto);
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
        PatientTM selectedPatient = tblPatient.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            //after see
            txtPatientId.setText(selectedPatient.getId());
            txtPatientName.setText(selectedPatient.getName());
            txtPatientTele.setText(selectedPatient.getEmail());
            txtPatientAddress.setText(selectedPatient.getAddress());
            txtPatientEmail.setText(selectedPatient.getPhone());
            txtMedicalHistory.setText(selectedPatient.getMedicalHistory());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void refreshTable() {
        try {
            List<PatientDTO> list = patientBO.getAllPatients();
            ObservableList<PatientTM> tmList = FXCollections.observableArrayList();

            for (PatientDTO dto : list) {
                tmList.add(new PatientTM(
                        dto.getId(),
                        dto.getName(),
                        dto.getPhone(),
                        dto.getEmail(),
                        dto.getAddress(),
                        dto.getMedicalHistory()
                ));
            }
            tblPatient.setItems(tmList);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load therapists data: " + e.getMessage());
        }
    }

    private void refreshPage() {
        txtPatientId.setText(patientBO.getNextPatientId());
        txtPatientName.clear();
        txtPatientTele.clear();
        txtPatientEmail.clear();
        txtPatientAddress.clear();
        txtMedicalHistory.clear();
        txtPatientId.requestFocus();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void setCellValueFactory(){
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPatientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPatientTele.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colPatientAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPatientMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPatientId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientName, null));
        txtPatientName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientTele, txtPatientId));
        txtPatientEmail.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientAddress, txtPatientTele));
        txtPatientAddress.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtMedicalHistory, txtPatientEmail));
        txtPatientTele.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientEmail, txtPatientName));
        txtMedicalHistory.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtPatientAddress));

        setCellValueFactory();
        refreshTable();

        txtPatientId.setText( patientBO.getNextPatientId());

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtPatientId.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtPatientName.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtPatientEmail.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtPatientAddress.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtPatientTele.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtMedicalHistory.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());

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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInputs() {
        boolean valid = true;

        // Patient ID: P001
        if (!txtPatientId.getText().matches("^P\\d{3}$")) {
            setFieldError(txtPatientId, true);
            valid = false;
        } else {
            setFieldError(txtPatientId, false);
        }

        // Name: only letters and spaces and .
        if (!txtPatientName.getText().matches("^[A-Za-z\\s.]{3,}$")) {
            setFieldError(txtPatientName, true);
            valid = false;
        } else {
            setFieldError(txtPatientName, false);
        }

        // Phone: 10 digits starting with 0
        if (!txtPatientTele.getText().matches("^(0\\d{9})$")) {
            setFieldError(txtPatientTele, true);
            valid = false;
        } else {
            setFieldError(txtPatientTele, false);
        }

        // Email
        if (!txtPatientEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            setFieldError(txtPatientEmail, true);
            valid = false;
        } else {
            setFieldError(txtPatientEmail, false);
        }

        // Address: not empty
        if (txtPatientAddress.getText().trim().isEmpty()) {
            setFieldError(txtPatientAddress, true);
            valid = false;
        } else {
            setFieldError(txtPatientAddress, false);
        }

        // Validate medical history: allows letters, numbers, common punctuation (.,()'-), and spaces; minimum 5 characters
        if (!txtMedicalHistory.getText().matches("^[A-Za-z0-9.,()\\s'-]{5,}$")) {
            setFieldError(txtMedicalHistory, true);
            valid = false;
        } else {
            setFieldError(txtMedicalHistory, false);
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
