package view;

/**
 * Created by Dipen on 18/04/2017.This class is used to display the main GUI window on the screen.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Kelburn Postal Smart");
        Image iconImage = new Image(GUI.class.getResourceAsStream("/img/KPS.png"));
        primaryStage.getIcons().add(iconImage);
        primaryStage.setResizable(false);
        //primaryStage.setAlwaysOnTop(true);
        //loads the FXML file for the login scene
        Parent root = FXMLLoader.load(GUI.class.getResource("/fxml/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        // used to close the program properly
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Exiting...");
            System.exit(0);
            Platform.exit();
        });
    }
}

