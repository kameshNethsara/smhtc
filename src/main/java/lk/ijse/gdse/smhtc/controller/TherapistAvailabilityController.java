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
import lk.ijse.gdse.smhtc.bo.custom.TherapistAvailabiltyBO;
import lk.ijse.gdse.smhtc.bo.custom.TherapistBO;
import lk.ijse.gdse.smhtc.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.dto.tm.TherapistAvailabilityTM;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistAvailabilityController implements Initializable {

    TherapistAvailabiltyBO therapistAvailabiltyBO = (TherapistAvailabiltyBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST_AVAILABILITY);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearchAll;

    @FXML
    private Button btnSearchTherapistName;

    @FXML
    private Button btnUpdate;

    @FXML
    private ChoiceBox<String> choseBoxAvailability;

    @FXML
    private ChoiceBox<String> choseBoxAvailabilityTime;

    @FXML
    private TableColumn<TherapistAvailabilityTM, String> colAvailability;

    @FXML
    private TableColumn<TherapistAvailabilityTM, LocalDate> colAvailabilityDate;

    @FXML
    private TableColumn<TherapistAvailabilityTM, String> colAvailabilityId;

    @FXML
    private TableColumn<TherapistAvailabilityTM, LocalTime> colAvailabilityTime;

    @FXML
    private TableColumn<TherapistAvailabilityTM, String> colTherapistId;

    @FXML
    private DatePicker datePickerAvailabilityDate;

    @FXML
    private Label lblAvailability;

    @FXML
    private Label lblAvailabilityDate;

    @FXML
    private Label lblAvailabilityId;

    @FXML
    private Label lblAvailabilityTime;

    @FXML
    private Label lblSessionId;

    @FXML
    private Label lblTherapistId;

    @FXML
    private Label lblTherapistName;

    @FXML
    private TableView<TherapistAvailabilityTM> tblUser;

    @FXML
    private TextField txtAvailabilityId;

    @FXML
    private TextField txtSearchAll;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String availabilityId = txtAvailabilityId.getText();
            if (availabilityId.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation", "Select an entry to delete!");
                return;
            }

            boolean isDeleted = therapistAvailabiltyBO.delete(availabilityId);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Availability deleted!");
                refreshTable();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Delete failed!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete: " + e.getMessage());
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            // Validate inputs
            if (txtAvailabilityId.getText().isEmpty() ||
                    txtTherapistId.getText().isEmpty() ||
                    datePickerAvailabilityDate.getValue() == null ||
                    choseBoxAvailability.getValue() == null ||
                    choseBoxAvailabilityTime.getValue() == null
            ) {
                showAlert(Alert.AlertType.WARNING, "Validation", "Fill all required fields!");
                return;
            }

            // Create DTO
            TherapistAvailabilityDTO dto = new TherapistAvailabilityDTO(
                    txtAvailabilityId.getText(),
                    txtTherapistId.getText(),
                    txtSessionId.getText(),
                    choseBoxAvailability.getValue().toString(),
                    datePickerAvailabilityDate.getValue(),
                    LocalTime.parse(choseBoxAvailabilityTime.getValue().toString())
            );

            // Save to database
            boolean isSaved = therapistAvailabiltyBO.save(dto);
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Availability saved!");
                refreshTable();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Save failed!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save: " + e.getMessage());
        }
    }

    @FXML
    void btnSearchAllOnAction(ActionEvent event) {
        String query = txtSearchAll.getText();
        if (query.isEmpty()) {
            refreshTable(); // Reload all data
        } else {
            // Implement search logic (e.g., by therapist ID or date)
            // Example: Filter the table based on the search query
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
            // Validate inputs
            if (txtAvailabilityId.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation", "Select an entry to update!");
                return;
            }

            // Create DTO
            TherapistAvailabilityDTO dto = new TherapistAvailabilityDTO(
                    txtAvailabilityId.getText(),
                    txtTherapistId.getText(),
                    txtSessionId.getText(),
                    choseBoxAvailability.getValue().toString(),
                    datePickerAvailabilityDate.getValue(),
                    LocalTime.parse(choseBoxAvailabilityTime.getValue().toString())
            );

            // Update database
            boolean isUpdated = therapistAvailabiltyBO.update(dto);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Availability updated!");
                refreshTable();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Update failed!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update: " + e.getMessage());
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TherapistAvailabilityTM selected = (TherapistAvailabilityTM) tblUser.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtAvailabilityId.setText(selected.getAvailabilityId());
            txtTherapistId.setText(selected.getTherapistId());
            txtSessionId.setText(selected.getSessionId());
            choseBoxAvailability.setValue(selected.getAvailability());
            datePickerAvailabilityDate.setValue(selected.getAvailableDate());
            choseBoxAvailabilityTime.setValue(selected.getAvailableTime().toString());
        }
    }

    private void refreshTable() {
        try {
            List<TherapistAvailabilityDTO> list = therapistAvailabiltyBO.getAll();
            ObservableList<TherapistAvailabilityTM> tmList = FXCollections.observableArrayList();

            for (TherapistAvailabilityDTO dto : list) {
                tmList.add(new TherapistAvailabilityTM(
                        dto.getAvailabilityId(),
                        dto.getTherapistId(),
                        dto.getSessionId(),
                        dto.getAvailability(),
                        dto.getAvailableDate(),
                        dto.getAvailableTime()
                ));
            }

            tblUser.setItems(tmList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load data: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtAvailabilityId.clear();
        txtTherapistId.clear();
        txtSessionId.clear();
        choseBoxAvailability.getSelectionModel().clearSelection();
        datePickerAvailabilityDate.setValue(null);
        choseBoxAvailabilityTime.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtAvailabilityId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSearchAll, null));
        txtSessionId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistId, txtSearchAll));
        txtTherapistId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistName, txtSessionId));
        txtTherapistName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtTherapistId));
        txtSearchAll.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSessionId, txtAvailabilityId));


            // Initialize table columns
            colAvailabilityId.setCellValueFactory(new PropertyValueFactory<>("availabilityId"));
            colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
            colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
            colAvailabilityDate.setCellValueFactory(new PropertyValueFactory<>("availableDate"));
            colAvailabilityTime.setCellValueFactory(new PropertyValueFactory<>("availableTime"));

            // Populate ChoiceBoxes
            choseBoxAvailability.getItems().addAll("Available", "Unavailable");
            choseBoxAvailabilityTime.getItems().addAll(
                    "08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00"
            );

            // Load initial data
            refreshTable();

            // Set up navigation
            txtAvailabilityId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSearchAll, null));
            // ... (rest of key navigation code)


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
