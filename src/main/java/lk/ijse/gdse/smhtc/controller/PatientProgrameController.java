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

public class PatientProgrameController implements Initializable {

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
    private TableColumn<?, ?> colPRogrameId;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colRegisterDate;

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
    private TableView<?> tblUser;

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
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

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
