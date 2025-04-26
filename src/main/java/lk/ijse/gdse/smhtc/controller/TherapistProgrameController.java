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
import lk.ijse.gdse.smhtc.bo.custom.TherapistProgramBO;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.dto.TherapistProgramDTO;
import lk.ijse.gdse.smhtc.dto.tm.TherapistProgramTM;
import lk.ijse.gdse.smhtc.dto.tm.TherapistTM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistProgrameController implements Initializable {

    TherapistProgramBO therapistProgrameBO = (TherapistProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST_PROGRAM);

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<TherapistProgramTM, String> colProgrameId;

    @FXML
    private TableColumn<TherapistProgramTM, String> colTherapistId;

    @FXML
    private Label lblProgrameId;

    @FXML
    private Label lblTherapistId;

    @FXML
    private Label lblProgrameName;

    @FXML
    private TableView<TherapistProgramTM> tblUser;

    @FXML
    private TextField txtProgrameId;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtProgrameName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String tId = txtTherapistId.getText();
            String pId = txtProgrameId.getText();
            boolean isDeleted = therapistProgrameBO.deleteTherapistProgram(tId,pId);
            if (isDeleted) {
                showAlert(Alert.AlertType.ERROR, "Delete Successfully", "Delete Therapist.");
                //refreshTable();
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
//            if (!validateInputs()) return;
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    txtTherapistId.getText(),
                    txtProgrameId.getText(),
                    null
            );
            boolean isUpdated = therapistProgrameBO.saveTherapistProgram(dto.getTherapistId(), dto.getTherapyProgramId());
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Therapist Programe Saved successfully.");

                //refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, " Failed", "Could not Saved Therapist Programe.");
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
//            if (!validateInputs()) return;
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    txtTherapistId.getText(),
                    txtProgrameId.getText(),
                    null
            );
            boolean isUpdated = therapistProgrameBO.updateTherapistProgram(dto.getTherapistId(), dto.getTherapyProgramId());
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Therapist Programe Updated successfully.");

                //refreshTable();
                refreshPage();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, " Failed", "Could not Updated Therapist Programe.");
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String tId = txtTherapistId.getText();
        refreshTable(tId);
    }

    private void refreshTable(String tId) {
        try {
            List<TherapistProgramDTO> list = therapistProgrameBO.findIdList(tId);
            ObservableList<TherapistProgramTM> tmList = FXCollections.observableArrayList();

            for (TherapistProgramDTO dto : list) {
                tmList.add(new TherapistProgramTM(
                        dto.getTherapistId(),
                        dto.getTherapyProgramId(),
                        dto.getTherapyProgramName()
                ));
            }
            tblUser.setItems(tmList);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load therapists data: " + e.getMessage());
        }
    }

    private void refreshPage() {
        txtTherapistId.clear();
        txtProgrameId.clear();
        txtProgrameName.clear();
        txtTherapistId.requestFocus();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TherapistProgramTM selected = tblUser.getSelectionModel().getSelectedItem();
        if (selected != null) {

            txtTherapistId.setText(selected.getTherapistId());
            txtProgrameId.setText(selected.getTherapyProgramId());
            txtProgrameName.setText(selected.getTherapyProgramName());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colProgrameId.setCellValueFactory(new PropertyValueFactory<>("therapyProgramId"));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
