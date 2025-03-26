package lk.ijse.gdse.smhtc.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReceptionistController implements Initializable {

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPatientPrograme;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnPrograme;

    @FXML
    private Button btnSession;

    @FXML
    private Button btnTherapist;

    @FXML
    private Button btnLogout;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane rightSubPane;

    @FXML
    private Label lblDateTime;

    private Button selectedButton = null; // Track the selected button

    @FXML
    void navigateToAccount(ActionEvent event) {
        navigateTo("/view/navigation/UserView.fxml", btnAccount);
    }

    @FXML
    void navigateToDashboard(ActionEvent event) {
        navigateTo("/view/navigation/DashboardView.fxml", btnDashboard);
    }

    @FXML
    void navigateToPatient(ActionEvent event) {
        navigateTo("/view/navigation/PatientView.fxml", btnPatient);
    }


    @FXML
    void navigateToPatientProgram(ActionEvent event) {
        navigateTo("/view/navigation/PatientProgrameView.fxml", btnPatientPrograme);
    }
    @FXML
    void navigateToPayment(ActionEvent event) {
        navigateTo("/view/navigation/PaymentView.fxml", btnPayment);
    }

    @FXML
    void navigateToPrograme(ActionEvent event) {
        navigateTo("/view/ProgrameView.fxml", btnPrograme);
    }

    @FXML
    void navigateToSession(ActionEvent event) {
        navigateTo("/view/navigation/SessionView.fxml", btnSession);
    }

    @FXML
    void navigateToTherapist(ActionEvent event) {
        navigateTo("/view/navigation/TherapistView.fxml", btnTherapist);
    }

    @FXML
    void navigateToLogout(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //setLogoutSelected();
            System.out.println("User logged out!");
//        navigateTo("/view/LoginView.fxml", btnLogout);
            try {
                mainPane.getChildren().clear();
                Parent loginView = FXMLLoader.load(getClass().getResource("/view/login/LoginView.fxml"));
                mainPane.getChildren().add(loginView);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact the developer").show();
                e.printStackTrace();
            }
        }
    }

    AnchorPane load;

    public void navigateTo(String fxmlPath, Button clickedButton) {
        try {
            if (fxmlPath == null || fxmlPath.trim().isEmpty()) {
                throw new IllegalArgumentException("FXML path cannot be null or empty.");
            }

            if (fxmlPath.equals("/view/navigation/DashboardView.fxml")) {
                // If the Dashboard is requested, directly show the existing view
                fxmlPath = "/view/navigation/DashboardView.fxml";
                rightSubPane.getChildren().clear();
                Parent dashboardView = FXMLLoader.load(getClass().getResource(fxmlPath));
                rightSubPane.getChildren().add(dashboardView);
            } else {
                // Load and set the requested view
                rightSubPane.getChildren().clear();
                Parent newView = FXMLLoader.load(getClass().getResource(fxmlPath));
                rightSubPane.getChildren().add(newView);
            }

            // Remove selection from previous button
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("selected-button");
                selectedButton.getStyleClass().remove("selected-logout");
            }

            // Set the new button as selected
            clickedButton.getStyleClass().add("selected-button");
            selectedButton = clickedButton;

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact the developer").show();
            e.printStackTrace();
        }
    }

//    private void setLogoutSelected() {
//        // Remove previous selection
//        if (selectedButton != null) {
//            selectedButton.getStyleClass().remove("selected-button");
//            selectedButton.getStyleClass().remove("selected-logout");
//        }
//
//        // Set Logout button as selected
//        btnLogout.getStyleClass().add("selected-logout");
//        selectedButton = btnLogout;
//    }

    //=====================================================================================//

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showDateTime();
        btnTherapist.setDisable(true);
        btnPrograme.setDisable(true);
        btnAccount.setDisable(true);
    }

    private void showDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalDateTime now = LocalDateTime.now();
                    lblDateTime.setText(now.format(formatter)); // Update label
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE); // Keep running
        timeline.play();
    }
    private void setupKeyboardNavigation() {
        Button[] buttons = {btnDashboard, btnPatient, btnPatientPrograme, btnPayment, btnPrograme, btnSession, btnTherapist, btnAccount, btnLogout};

        mainPane.setOnKeyPressed(event -> {
            if (selectedButton == null) {
                selectedButton = btnDashboard; // Default selection
                selectedButton.requestFocus();
                return;
            }

            int index = -1;
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i] == selectedButton) {
                    index = i;
                    break;
                }
            }

            if (event.getCode().toString().equals("DOWN")) {
                index = (index + 1) % buttons.length;
            } else if (event.getCode().toString().equals("UP")) {
                index = (index - 1 + buttons.length) % buttons.length;
            } else if (event.getCode().toString().equals("ENTER")) {
                selectedButton.fire(); // Simulate button click
                return;
            }

            selectedButton.getStyleClass().remove("selected-button");
            selectedButton = buttons[index];
            selectedButton.getStyleClass().add("selected-button");
            selectedButton.requestFocus();
        });

        mainPane.requestFocus();
    }

}
