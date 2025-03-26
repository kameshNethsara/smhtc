package lk.ijse.gdse.smhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TherapistController implements Initializable {

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
    private Button btnTherapistAvailability;

    @FXML
    private TableColumn<?, ?> colSpecialization;

    @FXML
    private TableColumn<?, ?> colTherapistAddress;

    @FXML
    private TableColumn<?, ?> colTherapistEmail;

    @FXML
    private TableColumn<?, ?> colTherapistId;

    @FXML
    private TableColumn<?, ?> colTherapistName;

    @FXML
    private TableColumn<?, ?> colTherapistTele;

    @FXML
    private Label lblSpecialization;

    @FXML
    private Label lblTherapistAddress;

    @FXML
    private Label lblTherapistEmail;

    @FXML
    private Label lblTherapistId;

    @FXML
    private Label lblTherapistName;

    @FXML
    private Label lblTherapistTele;

    @FXML
    private TableView<?> tblUser;

    @FXML
    private AnchorPane therapistPane;

    @FXML
    private TextField txtSpecialization;

    @FXML
    private TextField txtTherapistAddress;

    @FXML
    private TextField txtTherapistEmail;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    private TextField txtTherapistTele;

    private Button selectedButton = null; // Track the selected button

    @FXML
    void navigateToTherapistAvailability(ActionEvent event) {
        try {
            therapistPane.getChildren().clear();
            Parent TherapistAvailabilityView = FXMLLoader.load(getClass().getResource("/view/navigation/TherapistAvailabilityView.fxml"));
            therapistPane.getChildren().add(TherapistAvailabilityView);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact the developer").show();
            e.printStackTrace();
        }
    }

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
        txtTherapistId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistName, null));
        txtTherapistName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistTele, txtTherapistId));
        txtTherapistEmail.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistAddress, txtTherapistTele));
        txtTherapistAddress.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSpecialization, txtTherapistEmail));
        txtTherapistTele.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtTherapistEmail, txtTherapistName));
        txtSpecialization.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtTherapistAddress));
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
