package lk.ijse.gdse.smhtc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.smhtc.bo.BOFactory;
import lk.ijse.gdse.smhtc.bo.custom.*;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.TherapySessionDTO;
import lk.ijse.gdse.smhtc.dto.tm.TherapySessionTM;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class SessionController implements Initializable {

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgrammeBO programBO = (TherapyProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    PatientProgrammeBO patientProgramBO = (PatientProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT_PROGRAM);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapySessionBO sessionBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearchAll;

    @FXML
    private Button btnSearchPatientName;

    @FXML
    private Button btnSearchProgramName;

    @FXML
    private Button btnSearchTherapistName;

    @FXML
    private Button btnUpdate;

    @FXML
    private ChoiceBox<LocalTime> choseBoxSessionTime;

    @FXML
    private TableColumn<TherapySessionTM, String> colPatientId;

    @FXML
    private TableColumn<TherapySessionTM, String> colProgramName;

    @FXML
    private TableColumn<TherapySessionTM, LocalDate> colSessionDate;

    @FXML
    private TableColumn<TherapySessionTM, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionTM, LocalTime> colSessionTime;

    @FXML
    private TableColumn<TherapySessionTM, String> colStatus;

    @FXML
    private TableColumn<TherapySessionTM, String> colTherapistId;

    @FXML
    private DatePicker datePickerSessionDate;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblProgrameId;

    @FXML
    private Label lblProgrameName;

    @FXML
    private Label lblSessionDate;

    @FXML
    private Label lblSessionId;

    @FXML
    private Label lblSessionTime;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTherapistId;

    @FXML
    private Label lblTherapistName;

    @FXML
    private TableView<TherapySessionTM> tblUser;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtProgrameId;

    @FXML
    private TextField txtProgrameName;

    @FXML
    private TextField txtSearchAll;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String id = txtSessionId.getText();
            boolean isDeleted = sessionBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.ERROR, "Delete Successfully", "Delete Session.");
                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Delete Failed", "could not delete Session.");
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
            //if (!validateInputs()) return;
            TherapySessionDTO dto = new TherapySessionDTO(
                    txtSessionId.getText(),
                    txtPatientId.getText(),
                    txtProgrameId.getText(),
                    txtTherapistId.getText(),
                    datePickerSessionDate.getValue(),
                    choseBoxSessionTime.getValue(),
                    txtStatus.getText()
            );
            boolean isSaved = sessionBO.save(dto);
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
    void btnSearchAllOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchPatientNameOnAction(ActionEvent event) {
        try {
            String patientName = txtPatientName.getText();

            // Check if program name is empty
            if (patientName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a patient name.").show();
                return;
            }

            // Search for therapy programs by name
            List<PatientDTO> list = patientBO.findByName(patientName);

            // If at least one result is found
            if (!list.isEmpty()) {
                PatientDTO dto = list.get(0); // Take the first match
                txtPatientId.setText(dto.getId()); // Set the ID in the Program ID field
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "No patient found with name: " + patientName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to search: " + e.getMessage());
        }
    }

    @FXML
    void btnSearchProgramNameOnAction(ActionEvent event) {
        try {
            String programName = txtProgrameName.getText();

            // Check if program name is empty
            if (programName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a program name.").show();
                return;
            }

            // Search for therapy programs by name
            List<TherapyProgrammeDTO> list = patientProgramBO.findByTherapyProgrammeName(programName);

            // If at least one result is found
            if (!list.isEmpty()) {
                TherapyProgrammeDTO dto = list.get(0); // Take the first match
                txtProgrameId.setText(dto.getId()); // Set the ID in the Program ID field
            } else {
                // No matching program found
                new Alert(Alert.AlertType.INFORMATION, "No program found with this name.").show();
                txtProgrameId.clear(); // Clear the ID field
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the program.").show();
        }
    }

    @FXML
    void btnSearchTherapistNameOnAction(ActionEvent event) {
        try {
            String therapistName = txtTherapistName.getText();

            // Check if program name is empty
            if (therapistName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a Therapist name.").show();
                return;
            }

            // Search for therapy programs by name
            List<TherapistDTO> list = therapistBO.findByName(therapistName);

            // If at least one result is found
            if (!list.isEmpty()) {
                TherapistDTO dto = list.get(0); // Take the first match
                txtTherapistId.setText(dto.getId()); // Set the ID in the Program ID field
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "No Therapist found with name: " + therapistName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to search: " + e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            //if (!validateInputs()) return;
            TherapySessionDTO dto = new TherapySessionDTO(
                    txtSessionId.getText(),
                    txtPatientId.getText(),
                    txtProgrameId.getText(),
                    txtTherapistId.getText(),
                    datePickerSessionDate.getValue(),
                    (LocalTime) choseBoxSessionTime.getValue(),
                    txtStatus.getText()
            );
            boolean isUpdated = sessionBO.update(dto);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient Updated successfully.");

                refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Save Failed", "Could not Update Patient.");
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TherapySessionTM selected = (TherapySessionTM) tblUser.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        txtSessionId.setText(selected.getSessionId());
        txtPatientId.setText(selected.getPatientId());
        txtProgrameId.setText(selected.getTherapyProgramId());
        txtTherapistId.setText(selected.getTherapistId());
        datePickerSessionDate.setValue(selected.getSessionDate());
        choseBoxSessionTime.setValue(selected.getSessionTime());
        txtStatus.setText(selected.getStatus());

        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnSave.setDisable(true);
    }


    private void refreshTable() {
        try {
            List<TherapySessionDTO> allSessions = sessionBO.getAll();
            ObservableList<TherapySessionTM> tmList = FXCollections.observableArrayList();

            for (TherapySessionDTO dto : allSessions) {
                tmList.add(new TherapySessionTM(
                        dto.getSessionId(),
                        dto.getPatientId(),
                        dto.getTherapyProgramId(),
                        dto.getTherapistId(),
                        dto.getSessionDate(),
                        dto.getSessionTime(),
                        dto.getStatus()
                ));
            }

            tblUser.setItems(tmList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load sessions.");
        }
    }


    private void refreshPage() {
        txtSessionId.clear();
        txtPatientId.clear();
        txtPatientName.clear();
        txtProgrameId.clear();
        txtProgrameName.clear();
        txtTherapistId.clear();
        txtTherapistName.clear();
        txtStatus.clear();
        choseBoxSessionTime.setValue(null);
        datePickerSessionDate.setValue(null);

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void setCellValueFactory(){
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colSessionDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colSessionTime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPatientId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientName, null));
        txtPatientName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameId, txtPatientId));
        txtProgrameId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameName, txtPatientName));
        txtProgrameName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistId, txtProgrameId));
        txtTherapistId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistName, txtProgrameName));
        txtTherapistName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSessionId, txtTherapistId));
        txtSessionId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtStatus, txtTherapistName));
        txtStatus.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtSessionId));
        txtSearchAll.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, null));

        setCellValueFactory();
        refreshTable();
        comboBoxTime();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

//        txtSessionId.setText( sessionBO.getNextSessionPK());

    }
    private void comboBoxTime(){
        ObservableList<LocalTime> times = FXCollections.observableArrayList();

        // Add 30-minute intervals between 08:00 and 17:00
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        while (!startTime.isAfter(endTime)) {
            times.add(startTime);
            startTime = startTime.plusMinutes(30);
        }

        choseBoxSessionTime.setItems(times);
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

}
