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
import lk.ijse.gdse.smhtc.bo.custom.TherapyProgrammeBO;
import lk.ijse.gdse.smhtc.bo.custom.impl.TherapyProgrammeBOImpl;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.tm.TherapistTM;
import lk.ijse.gdse.smhtc.dto.tm.TherapyProgrammeTM;

import javax.naming.Name;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgrameController implements Initializable {

    TherapyProgrammeBO therapyProgrammeBO = new TherapyProgrammeBOImpl();

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
    private TableColumn<TherapyProgrammeTM, String> colProgrameDuration;

    @FXML
    private TableColumn<TherapyProgrammeTM, BigDecimal> colProgrameFee;

    @FXML
    private TableColumn<TherapyProgrammeTM, String> colProgrameId;

    @FXML
    private TableColumn<TherapyProgrammeTM, String> colProgrameName;

    @FXML
    private Label lblProgrameDuration;

    @FXML
    private Label lblProgrameFee;

    @FXML
    private Label lblProgrameId;

    @FXML
    private Label lblProgrameName;

    @FXML
    private TableView<TherapyProgrammeTM> tblTP;

    @FXML
    private TextField txtProgrameDuration;

    @FXML
    private TextField txtProgrameFee;

    @FXML
    private TextField txtProgrameId;

    @FXML
    private TextField txtProgrameName;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String id = txtProgrameId.getText();
            boolean isDeleted = therapyProgrammeBO.deleteTherapyProgramme(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Therapy Programme deleted successfully.");
                refreshTable();
                refreshPage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Therapy Programme.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Therapy Programme: " + e.getMessage());
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            String id = txtProgrameId.getText();
            String name = txtProgrameName.getText();
            String duration = txtProgrameDuration.getText();
            BigDecimal fee = new BigDecimal(txtProgrameFee.getText());

            TherapyProgrammeDTO therapyProgrammeDTO = new TherapyProgrammeDTO(id, name, duration, fee);
            boolean isSaved = therapyProgrammeBO.saveTherapyProgramme(therapyProgrammeDTO);

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Therapy Programme saved successfully.");
                refreshTable();
                refreshPage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save Therapy Programme.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save Therapy Programme: " + e.getMessage());
        }
    }

//    @FXML
//    void btnSearchAllOnAction(ActionEvent event) {
//        System.out.println("search btn is working");
//        String id = txtSearch.getText();
//        therapyProgrammeBO.findByTherapyProgrammeId(id).ifPresent(therapyProgramme -> {
//            txtProgrameId.setText(therapyProgramme.getId());
//            txtProgrameName.setText(therapyProgramme.getName());
//            txtProgrameDuration.setText(therapyProgramme.getDuration());
//            txtProgrameFee.setText(String.valueOf(therapyProgramme.getFee()));
//        });
//
//        ///////////////////////////////////////////////////////////////////////////////
//        String name = txtSearch.getText();
//
//        // Retrieve the list of TherapyProgrammeDTO by name
//        List<TherapyProgrammeDTO> therapyProgrammes = therapyProgrammeBO.findByTherapyProgrammeName(name);
//
//        // Check if the list is not empty
//        if (!therapyProgrammes.isEmpty()) {
//            // Take the first matching TherapyProgrammeDTO
//            TherapyProgrammeDTO therapyProgramme = therapyProgrammes.get(0);
//
//            // Set the values in the corresponding fields
//            txtProgrameId.setText(therapyProgramme.getId());
//            txtProgrameName.setText(therapyProgramme.getName());
//            txtProgrameDuration.setText(therapyProgramme.getDuration());
//            txtProgrameFee.setText(String.valueOf(therapyProgramme.getFee()));
//        } else {
//            // If no matching TherapyProgramme is found, you can clear the fields or show an alert
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("No Programme Found");
//            alert.setHeaderText(null);
//            alert.setContentText("No therapy programme found with the given name.");
//            alert.showAndWait();
//
//        }
//
//
//    }

    @FXML
    void btnSearchAllOnAction(ActionEvent event) {
        System.out.println("Search button clicked.");
        String keyword = txtSearch.getText().trim();

        // Try searching by ID first
        Optional<TherapyProgrammeDTO> byId = therapyProgrammeBO.findByTherapyProgrammeId(keyword);

        if (byId.isPresent()) {
            populateProgrammeFields(byId.get());
            return;
        }

        // If not found by ID, try searching by Name
        List<TherapyProgrammeDTO> byNameList = therapyProgrammeBO.findByTherapyProgrammeName(keyword);

        if (!byNameList.isEmpty()) {
            populateProgrammeFields(byNameList.get(0)); // First match
        } else {
            clearProgrammeFields();
            showAlert(Alert.AlertType.ERROR,"No Programme Found", "No therapy programme found with the given ID or Name.");
        }
    }

    private void populateProgrammeFields(TherapyProgrammeDTO therapyProgramme) {
        txtProgrameId.setText(therapyProgramme.getId());
        txtProgrameName.setText(therapyProgramme.getName());
        txtProgrameDuration.setText(therapyProgramme.getDuration());
        txtProgrameFee.setText(String.valueOf(therapyProgramme.getFee()));
    }

    private void clearProgrammeFields() {
        txtProgrameId.clear();
        txtProgrameName.clear();
        txtProgrameDuration.clear();
        txtProgrameFee.clear();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            String id = txtProgrameId.getText();
            String name = txtProgrameName.getText();
            String duration = txtProgrameDuration.getText();
            BigDecimal fee = new BigDecimal(txtProgrameFee.getText());

            TherapyProgrammeDTO therapyProgrammeDTO = new TherapyProgrammeDTO(id, name, duration, fee);
            boolean isUpdated = therapyProgrammeBO.updateTherapyProgramme(therapyProgrammeDTO);

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Therapy Programme updated successfully.");
                refreshTable();
                refreshPage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update Therapy Programme.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update Therapy Programme: " + e.getMessage());
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TherapyProgrammeTM selectedItem = tblTP.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtProgrameId.setText(selectedItem.getId());
            txtProgrameName.setText(selectedItem.getName());
            txtProgrameDuration.setText(selectedItem.getDuration());
            txtProgrameFee.setText(String.valueOf(selectedItem.getFee()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void refreshTable() {
        try {
            List<TherapyProgrammeDTO> list = therapyProgrammeBO.getAllTherapyProgrammes();
            ObservableList<TherapyProgrammeTM> tmList = FXCollections.observableArrayList();

            for (TherapyProgrammeDTO dto : list) {
                tmList.add(new TherapyProgrammeTM(
                        dto.getId(),
                        dto.getName(),
                        dto.getDuration(),
                        dto.getFee()
                ));
            }
            tblTP.setItems(tmList);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Therapy Programme data: " + e.getMessage());
        }
    }

    private void refreshPage() {
        txtProgrameId.setText( therapyProgrammeBO.getNextTherapyProgrammeId());
        txtProgrameName.clear();
        txtProgrameDuration.clear();
        txtProgrameFee.clear();
        txtSearch.clear();

        txtProgrameId.requestFocus();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtProgrameId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameName, null));
        txtProgrameName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameDuration, txtProgrameId));
        txtProgrameDuration.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameFee, txtProgrameName));
        txtProgrameFee.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSearch, txtProgrameDuration));
        txtSearch.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtProgrameDuration));

        setCellValueFactory();
        refreshTable();

        txtProgrameId.setText( therapyProgrammeBO.getNextTherapyProgrammeId());

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtProgrameId.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtProgrameName.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtProgrameDuration.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        txtProgrameFee.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());


    }

    private void setCellValueFactory() {
        colProgrameId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgrameName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProgrameDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colProgrameFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
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

        // Programme ID: TP-001
        if (!txtProgrameId.getText().matches("^TP-\\d{3}$")) {
            setFieldError(txtProgrameId, true);
            valid = false;
        } else {
            setFieldError(txtProgrameId, false);
        }

        // Name: only letters, spaces, and periods, at least 3 characters long
        if (!txtProgrameName.getText().matches("^[A-Za-z\\s.]{3,}$")) {
            setFieldError(txtProgrameName, true);
            valid = false;
        } else {
            setFieldError(txtProgrameName, false);
        }

        // Duration: Check if it's a valid duration format (e.g., "6 months", "1 year")
        if (!txtProgrameDuration.getText().matches("^[A-Za-z0-9\\s]+$")) {
            setFieldError(txtProgrameDuration, true);
            valid = false;
        } else {
            setFieldError(txtProgrameDuration, false);
        }

        // Fee: Ensure it's a valid number (positive decimal or integer)
        try {
            double fee = Double.parseDouble(txtProgrameFee.getText());
            if (fee <= 0) {
                setFieldError(txtProgrameFee, true);
                valid = false;
            } else {
                setFieldError(txtProgrameFee, false);
            }
        } catch (NumberFormatException e) {
            setFieldError(txtProgrameFee, true);
            valid = false;
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
