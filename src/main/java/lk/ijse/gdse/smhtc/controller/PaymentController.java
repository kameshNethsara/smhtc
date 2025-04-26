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
import lk.ijse.gdse.smhtc.bo.custom.PatientBO;
import lk.ijse.gdse.smhtc.bo.custom.PatientProgrammeBO;
import lk.ijse.gdse.smhtc.bo.custom.PaymentBO;
import lk.ijse.gdse.smhtc.bo.custom.TherapyProgrammeBO;
import lk.ijse.gdse.smhtc.dao.custom.PatientProgramDAO;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.PaymentDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.tm.PatientTM;
import lk.ijse.gdse.smhtc.dto.tm.PaymentTM;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgrammeBO programBO = (TherapyProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    PatientProgrammeBO patientProgramBO = (PatientProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT_PROGRAM);


    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearchPatientName;

    @FXML
    private Button btnSearchPaymentId;

    @FXML
    private Button btnSearchProgramName;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PaymentTM, BigDecimal> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colPatientId;

    @FXML
    private TableColumn<PaymentTM, LocalDate> colPaymentDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colProgrameId;

    @FXML
    private TableColumn<PaymentTM, String> colSessionId;

    @FXML
    private DatePicker datePickerPaymentDate;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblPaymentDate;

    @FXML
    private Label lblProgrameId;

    @FXML
    private Label lblProgrameName;

    @FXML
    private Label lblSessionId;

    @FXML
    private Label lblSpecialization;

    @FXML
    private Label lblTherapistAddress;

    @FXML
    private Label lblTherapistId;

    @FXML
    private Label lblTherapistId1;

    @FXML
    private TableView<PaymentTM> tblUser;

    @FXML
    private ChoiceBox<String> cmbPaymentType;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtProgrameId;

    @FXML
    private TextField txtProgrameName;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtPaymentId;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        if (paymentId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a Payment ID to delete.");
            return;
        }

        try {
            boolean isDeleted = paymentBO.delete(paymentId);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment deleted successfully.");
                refreshTable();
                refreshPage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete payment.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the payment: " + e.getMessage());
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
            PaymentDTO dto = new PaymentDTO(
                    txtPaymentId.getText(),
                    txtPatientId.getText(),
                    txtProgrameId.getText(),
                    txtSessionId.getText().isEmpty() ? null : txtSessionId.getText(),
                    new BigDecimal(txtAmount.getText()),
                    cmbPaymentType.getValue(),
                    datePickerPaymentDate.getValue()
            );

            if (cmbPaymentType.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a payment type.");
                return;
            }

            boolean isSaved = paymentBO.save(dto);

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment saved successfully.");
                refreshTable();
                refreshPage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Could not save the payment.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
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
    void btnSearchPaymentIdOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        if (paymentId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Enter a Payment ID to search.");
            return;
        }

        try {
            Optional<PaymentDTO> dto = paymentBO.findById(paymentId);
            if (dto.isPresent()) {
                txtPatientId.setText(dto.get().getPatientId());
                txtProgrameId.setText(dto.get().getTherapyProgramId());
                txtSessionId.setText(dto.get().getTherapySessionId());
                txtAmount.setText(dto.get().getAmount().toString());
                cmbPaymentType.setValue(dto.get().getPaymentType());
                datePickerPaymentDate.setValue(dto.get().getPaymentDate());
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "No payment found for ID: " + paymentId);
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
    void btnUpdateOnAction(ActionEvent event) {
        try {
            // Collect updated payment data from input fields
            PaymentDTO dto = new PaymentDTO(
                    txtPaymentId.getText(),
                    txtPatientId.getText(),
                    txtProgrameId.getText(),
                    txtSessionId.getText(),
                    new BigDecimal(txtAmount.getText()),
                    cmbPaymentType.getValue(),
                    datePickerPaymentDate.getValue()
            );

            boolean isUpdated = paymentBO.update(dto);

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment updated successfully.");
                refreshTable();
                refreshPage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to update the payment.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the payment: " + e.getMessage());
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        PaymentTM selectedPayment = tblUser.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) return;

        // Populate input fields with selected row's data
        txtPaymentId.setText(selectedPayment.getPaymentId());
        txtPatientId.setText(selectedPayment.getPatientId());
        txtProgrameId.setText(selectedPayment.getTherapyProgramId());
        txtSessionId.setText(selectedPayment.getTherapySessionId());
        txtAmount.setText(selectedPayment.getAmount().toString());
        datePickerPaymentDate.setValue(selectedPayment.getPaymentDate());
        cmbPaymentType.setValue(selectedPayment.getPaymentType());

        // Enable update and delete buttons
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnSave.setDisable(true);
    }

    private void refreshTable() {
       try {
        List<PaymentDTO> list = paymentBO.getAll();
        ObservableList<PaymentTM> tmList = FXCollections.observableArrayList();

        for (PaymentDTO dto : list) {
         tmList.add(new PaymentTM(
                 dto.getPaymentId(),
                 dto.getPatientId(),
                 dto.getTherapyProgramId(),
                 dto.getTherapySessionId(),
                 dto.getAmount(),
                 dto.getPaymentType(),
                 dto.getPaymentDate()
         ));
        }
        tblUser.setItems(tmList);
       } catch (Exception e) {
        e.printStackTrace(); // Log any exceptions
        showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Payment data: " + e.getMessage());
       }
    }

    private void refreshPage() {
        txtPatientId.clear();
        txtPatientName.clear();
        txtProgrameId.clear();
        txtProgrameName.clear();
        txtSessionId.clear();
        txtAmount.clear();
        txtPaymentId.setText(paymentBO.getNextPaymentId());
        datePickerPaymentDate.setValue(null);
        cmbPaymentType.setValue("Full-Payment");
        //tblUser.getSelectionModel().clearSelection();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }


 private void setCellValueFactory() {
     colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
     colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
     colProgrameId.setCellValueFactory(new PropertyValueFactory<>("therapyProgramId")); // Fixed
     colSessionId.setCellValueFactory(new PropertyValueFactory<>("therapySessionId"));  // Fixed
     colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
     colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
    }


 @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPatientId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientName, null));
        txtPatientName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameId, txtPatientId));
        txtProgrameId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameName, txtPatientName));
        txtProgrameName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSessionId, txtProgrameId));
        txtSessionId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPaymentId, txtProgrameName));
        txtPaymentId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtAmount, txtSessionId));
        txtAmount.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtPaymentId));

        //cmbPaymentType.getItems().addAll("Cash", "Card", "Online", "Other");
        cmbPaymentType.getItems().addAll("Full-Payment", "Session-Payment", "Installment", "Free");
//        cmbPaymentType.setValue("Full-Payment"); // Set default value if needed

        setCellValueFactory();
        refreshTable();
        txtPaymentId.setText(paymentBO.getNextPaymentId());
        cmbPaymentType.setValue("Full-Payment"); // Set default value if needed

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

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
