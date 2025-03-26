package lk.ijse.gdse.smhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TherapyProgrameController implements Initializable {

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
    private TableColumn<?, ?> colProgrameDuration;

    @FXML
    private TableColumn<?, ?> colProgrameFee;

    @FXML
    private TableColumn<?, ?> colProgrameId;

    @FXML
    private TableColumn<?, ?> colProgrameName;

    @FXML
    private Label lblProgrameDuration;

    @FXML
    private Label lblProgrameFee;

    @FXML
    private Label lblProgrameId;

    @FXML
    private Label lblProgrameName;

    @FXML
    private TableView<?> tblUser;

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
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtProgrameId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameName, null));
        txtProgrameName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameDuration, txtProgrameId));
        txtProgrameDuration.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtProgrameFee, txtProgrameName));
        txtProgrameFee.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtSearch, txtProgrameDuration));
        txtSearch.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtProgrameDuration));
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
