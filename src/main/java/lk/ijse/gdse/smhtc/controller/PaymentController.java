package lk.ijse.gdse.smhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

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
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colProgrameId;

    @FXML
    private TableColumn<?, ?> colSessionId;

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
    private TextField txtSessionId;

    @FXML
    private TextField txtSpecialization;

    @FXML
    private TextField txtTherapistEmail;

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
    void btnSearchPatientNameOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchPaymentIdOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchProgramNameOnAction(ActionEvent event) {

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
        txtProgrameName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSessionId, txtProgrameId));
        txtSessionId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSpecialization, txtProgrameName));
        txtSpecialization.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistEmail, txtSessionId));
        txtTherapistEmail.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtSpecialization));
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
