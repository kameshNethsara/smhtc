package lk.ijse.gdse.smhtc.controller;

import com.jfoenix.controls.JFXComboBox;
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

public class UserController implements Initializable {

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
    private JFXComboBox<?> cmbJobRole;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colUserJobRole;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserPassword;

    @FXML
    private Label lblJobRole;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblUserPassword;

    @FXML
    private TableView<?> tblUser;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserPassword;

    @FXML
    private AnchorPane userPane;

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
    void handleComboBoxAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUserId.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtUserName, null));
        txtUserName.setOnKeyPressed(event -> handleArrowKeyNavigation(event, txtUserPassword, txtUserId));
        txtUserPassword.setOnKeyPressed(event -> handleArrowKeyNavigation(event, cmbJobRole.getEditor(), txtUserName));
        cmbJobRole.setOnKeyPressed(event -> handleArrowKeyNavigation(event, null, txtUserPassword));
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
//C:\Users\kames\AppData\Local\SceneBuilder\SceneBuilder.exe