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

public class TherapistAvailabilityController implements Initializable {

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
    private ChoiceBox<?> choseBoxAvailability;

    @FXML
    private ChoiceBox<?> choseBoxAvailabilityTime;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colAvailabilityDate;

    @FXML
    private TableColumn<?, ?> colAvailabilityId;

    @FXML
    private TableColumn<?, ?> colAvailabilityTime;

    @FXML
    private TableColumn<?, ?> colTherapistId;

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
    private TableView<?> tblUser;

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
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtAvailabilityId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSearchAll, null));
        txtSessionId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistId, txtSearchAll));
        txtTherapistId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistName, txtSessionId));
        txtTherapistName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtTherapistId));
        txtSearchAll.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSessionId, txtAvailabilityId));
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
