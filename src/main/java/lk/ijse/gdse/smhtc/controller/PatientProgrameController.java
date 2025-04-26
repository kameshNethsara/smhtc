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
import lk.ijse.gdse.smhtc.bo.custom.PatientProgrammeBO;
import lk.ijse.gdse.smhtc.bo.custom.impl.PatientProgrammeBOImpl;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.PatientProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.tm.CustomPatientProgrammeTM;
import lk.ijse.gdse.smhtc.dto.tm.PatientTM;
import lk.ijse.gdse.smhtc.dto.tm.PaymentTM;
import lk.ijse.gdse.smhtc.dto.tm.TherapyProgrammeTM;
import lk.ijse.gdse.smhtc.entity.PatientProgramId;
import lk.ijse.gdse.smhtc.entity.Payment;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PatientProgrameController implements Initializable {

   // PatientProgrammeBO patientProgrammeBO = new PatientProgrammeBOImpl();
   PatientProgrammeBO patientProgrammeBO = (PatientProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT_PROGRAM);


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
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomPatientProgrammeTM, String> colPRogrameId;

    @FXML
    private TableColumn<CustomPatientProgrammeTM, String> colPatientId;

    @FXML
    private TableColumn<CustomPatientProgrammeTM, String> colPaymentId;

    @FXML
    private TableColumn<CustomPatientProgrammeTM, LocalDate> colRegisterDate;

    @FXML
    private DatePicker datePickerRegisterDate;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblProgrameId;

    @FXML
    private Label lblProgrameName;

    @FXML
    private Label lblRegisterDate;

    @FXML
    private Label lblProgramFee;

    @FXML
    private Label lblBalance;

    @FXML
    private TableView<CustomPatientProgrammeTM> tblUser;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtProgrameId;

    @FXML
    private TextField txtProgrameName;

    @FXML
    private TextField txtSearchAll;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String patientId = txtPatientId.getText();
            String programId = txtProgrameId.getText();

            // Validation
            if (patientId.isEmpty() || programId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter both Patient ID and Program ID.").show();
                return;
            }

            // Build the composite primary key
            PatientProgramId pk = new PatientProgramId(patientId, programId);

            // Call the BO layer to delete
            boolean isDeleted = patientProgrammeBO.deletePatientProgramme(pk);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Patient Programme deleted successfully.").show();
                refreshPage(); // Optional: method to reset input fields
                refreshTable(); // Optional: refresh the table view if present
            } else {
                new Alert(Alert.AlertType.ERROR, "No matching Patient Programme found to delete.").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the Patient Programme.").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please correct the highlighted fields.");
            return;
        }

        try {
            // Step 1: Gather inputs
            String patientId = txtPatientId.getText();
            String programId = txtProgrameId.getText();
            String paymentId = null;
            if(!txtPaymentId.getText().isEmpty()){
                paymentId = txtPaymentId.getText();
            }
            BigDecimal fee =lblProgramFee.getText().isEmpty() ? null : new BigDecimal(lblProgramFee.getText());
            BigDecimal balance = lblBalance.getText().isEmpty() ? null : new BigDecimal(lblBalance.getText());
            String registrationDate = datePickerRegisterDate.getValue().toString();

            // Step 2: Validation (optional but recommended)
//            if (patientId.isEmpty() || programId.isEmpty() || paymentId.isEmpty() || registrationDate.isEmpty()) {
//                new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
//                return;
//            }
            //after write like up line
            if (patientId.isEmpty() || programId.isEmpty() || registrationDate.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
                return;
            }

            // Step 3: Create DTO
            PatientProgrammeDTO dto = new PatientProgrammeDTO(
                    patientId,
                    programId,
                    paymentId,
                    fee,
                    balance,
                    registrationDate
            );

            // Step 4: Call BO method to save
            boolean isSaved = patientProgrammeBO.savePatientProgramme(dto);

            // Step 5: Feedback to user
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Patient Programme Saved Successfully!").show();
                refreshPage(); // Optional: clear after saving
                refreshTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Save Patient Programme!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving!").show();
        }
    }

    @FXML
    void btnSearchAllOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchPatientNameOnAction(ActionEvent event) {
        try {
            String patientName = txtPatientName.getText();

            // Check if the input field is empty
            if (patientName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a patient name.").show();
                return;
            }

            // Call BO to search for patients by name
            List<PatientDTO> patientList = patientProgrammeBO.findByPatientName(patientName);

            // If at least one match is found
            if (!patientList.isEmpty()) {
                PatientDTO dto = patientList.get(0); // Take the first matched patient
                txtPatientId.setText(dto.getId()); // Set the ID in the text field
            } else {
                // No match found
                new Alert(Alert.AlertType.INFORMATION, "No patient found with this name.").show();
                txtPatientId.clear(); // Clear the ID field
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the patient.").show();
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
            List<TherapyProgrammeDTO> list = patientProgrammeBO.findByTherapyProgrammeName(programName);

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
            // Step 1: Get data from form fields
            String patientId = txtPatientId.getText();
            String programId = txtProgrameId.getText();
            //String paymentId = txtPaymentId.getText();
            String paymentId = null;
            if(!txtPaymentId.getText().isEmpty()){
                paymentId = txtPaymentId.getText();
            }
            BigDecimal fee =lblProgramFee.getText().isEmpty() ? null : new BigDecimal(lblProgramFee.getText());
            BigDecimal balance = lblBalance.getText().isEmpty() ? null : new BigDecimal(lblBalance.getText());
            String registrationDate = datePickerRegisterDate.getValue() != null
                    ? datePickerRegisterDate.getValue().toString()
                    : "";

            // Step 2: Basic validation
//            if (patientId.isEmpty() || programId.isEmpty() || paymentId.isEmpty() || registrationDate.isEmpty()) {
//                new Alert(Alert.AlertType.WARNING, "Please fill in all fields before updating.").show();
//                return;
//            }
            //after write like up line
            if (patientId.isEmpty() || programId.isEmpty() || registrationDate.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields before updating.").show();
                return;
            }

            // Step 3: Create a DTO with the updated data
            PatientProgrammeDTO dto = new PatientProgrammeDTO(
                    patientId,
                    programId,
                    paymentId,
                    fee,
                    balance,
                    registrationDate
            );

            // Step 4: Call the BO method to update
            boolean isUpdated = patientProgrammeBO.updatePatientProgramme(dto);

            // Step 5: Show confirmation or error message
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Patient programme updated successfully!").show();
                refreshTable();   // Optional: refresh table after update
                refreshPage();    // Optional: clear fields after update
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update patient programme.").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating.").show();
        }
    }


    @FXML
    void onClickTable(MouseEvent event) {
        // Step 1: Get the selected row from the table
        CustomPatientProgrammeTM selected = tblUser.getSelectionModel().getSelectedItem();

        // Step 2: Check if a row is actually selected
        if (selected == null) return;

        // Step 3: Populate the form fields with the selected data
        txtPatientId.setText(selected.getPatientId());
        txtProgrameId.setText(selected.getProgrammeId());
        txtPaymentId.setText(selected.getPaymentId());
        datePickerRegisterDate.setValue(selected.getRegisterDate());
        lblProgramFee.setText(String.valueOf(selected.getFee()));

        if (selected.getBalance() == null){
            lblBalance.setText("0.00");
        }else {
            lblBalance.setText(String.valueOf(selected.getBalance()));
        }


        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void refreshTable() {
        try {
            // Fetch the list of patient programmes
            List<PatientProgrammeDTO> list = patientProgrammeBO.getAllPatientProgrammes();

            // Create an observable list for the TableView
            ObservableList<CustomPatientProgrammeTM> tmList = FXCollections.observableArrayList();

            // Loop through each DTO in the list
            for (PatientProgrammeDTO dto : list) {
                // Convert the String date to LocalDate (inside the loop for each dto)
                String registrationDateString = dto.getRegistrationDate();
                LocalDate registrationDate = LocalDate.parse(registrationDateString);

                // Add each DTO as a CustomPatientProgrammeTM to the observable list
                tmList.add(new CustomPatientProgrammeTM(
                        dto.getPatientId(),
                        dto.getProgramId(),
                        dto.getPaymentId(),
                        dto.getFee(),
                        dto.getBalance(),
                        registrationDate
                ));
            }

            // Set the items of the TableView to the observable list
            tblUser.setItems(tmList);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load patient programmes data: " + e.getMessage());
        }
    }

    private void refreshPage() {
        txtPatientId.clear();
        txtPatientName.clear();
        txtProgrameId.clear();
        txtProgrameName.clear();
        txtPaymentId.clear();
        datePickerRegisterDate.setValue(null);
        lblProgramFee.setText("");
        lblBalance.setText("");

        //txtProgrameId.requestFocus();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPatientName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameId, txtPatientId));
        txtProgrameName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPaymentId, txtProgrameId));
        txtPaymentId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSearchAll, txtProgrameName));
        txtPatientId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientName, null));
//        txtPaymentId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSearchAll, txtProgrameName));
//        txtProgrameId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameName, txtPatientName));
        txtSearchAll.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtPaymentId));

        setCellValueFactory();
        refreshTable();

//        txtPatientId.setText( patientProgrammeBO.getNextPatientId());
//        txtProgrameId.setText( patientProgrammeBO.getNextProgramId());
//        txtPaymentId.setText( patientProgrammeBO.getNextPaymentId());

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtProgrameId.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        //txtProgrameName.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtPatientId.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        //txtPatientName.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        //txtPaymentId.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
    }

    private void setCellValueFactory() {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colPRogrameId.setCellValueFactory(new PropertyValueFactory<>("programmeId"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colRegisterDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
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

//        // Name: only letters and spaces and .
//        if (!txtPatientName.getText().matches("^[A-Za-z\\s.]{3,}$")) {
//            setFieldError(txtPatientName, true);
//            valid = false;
//        } else {
//            setFieldError(txtPatientName, false);
//        }

        // Programme ID: TP-001
        if (!txtProgrameId.getText().matches("^TP-\\d{3}$")) {
            setFieldError(txtProgrameId, true);
            valid = false;
        } else {
            setFieldError(txtProgrameId, false);
        }

//        // Name: only letters, spaces, and periods, at least 3 characters long
//        if (!txtProgrameName.getText().matches("^[A-Za-z\\s.]{3,}$")) {
//            setFieldError(txtProgrameName, true);
//            valid = false;
//        } else {
//            setFieldError(txtProgrameName, false);
//        }

//        // Payment ID: PAY-001
//        if (!txtPaymentId.getText().matches("^PAY-\\d{3}$")) {
//            setFieldError(txtPaymentId, true);
//            valid = false;
//        } else {
//            setFieldError(txtPaymentId, false);
//        }

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
