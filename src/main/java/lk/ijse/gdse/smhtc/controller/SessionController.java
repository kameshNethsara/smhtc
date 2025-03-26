package lk.ijse.gdse.smhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SessionController implements Initializable {

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
    private ChoiceBox<?> choseBoxSessionTime;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colSessionDate;

    @FXML
    private TableColumn<?, ?> colSessionId;

    @FXML
    private TableColumn<?, ?> colSessionTime;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTherapistId;

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
    private TableView<?> tblUser;

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

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchAllOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchPatientNameOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchProgramNameOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchTherapistNameOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

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

}
