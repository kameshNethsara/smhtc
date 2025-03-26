package lk.ijse.gdse.smhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

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
    private TableColumn<?, ?> colPatientAddress;

    @FXML
    private TableColumn<?, ?> colPatientEmail;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colPatientMedicalHistory;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableColumn<?, ?> colPatientTele;

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
    private TableView<?> tblUser;

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

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

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
        txtPatientName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientTele, txtPatientId));
        txtPatientEmail.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientAddress, txtPatientTele));
        txtPatientAddress.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtMedicalHistory, txtPatientEmail));
        txtPatientTele.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtPatientEmail, txtPatientName));
        txtMedicalHistory.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtPatientAddress));

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
