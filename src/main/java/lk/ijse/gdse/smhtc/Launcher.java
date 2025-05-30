package lk.ijse.gdse.smhtc;

import jakarta.persistence.Transient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Launcher extends Application {
    public static void main(String[] args) {
        initializeDataBase();
        launch();

    }

    private static void initializeDataBase() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.close();

    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/login/LoginView.fxml"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.setTitle("The Serenity Mental Health Therapy Center");
            // load the icon image
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Serenity-Mental-Health-Therapy-Center-logo-2.jpg")));
//            stage.setResizable(true);
//            stage.setWidth(1000); // Adjust width
//            stage.setHeight(650); // Adjust height
            stage.setResizable(false); // Optional: Disable resizing if needed

            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Consider logging the error in a real application
        }
    }
}
