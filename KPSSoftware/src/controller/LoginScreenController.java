package controller;

/**
 * Created by Dipen on 18/04/2017. This is the controller used for the Login Screen. and will handle the interaction
 * between view and the model.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.KPSMain;

import java.io.IOException;


public class LoginScreenController {
    //this is the the main model class with whom all the interactions will take palce.
    private static KPSMain kpsMain;
    //this variables are links to there FXML files counterparts
    @FXML
    private TextField usernameTextfield;
    @FXML
    private PasswordField passwordTextfield;
    @FXML
    private Label authticationError;


    public LoginScreenController() {
        KPSMain.setLoginScreenController(this);
    }


    /**
     * this method is connected to the login button as well is the text fields, and is used to perform the steps
     * required for logging into the system.
     */
    @FXML
    public void performLogin(ActionEvent event) throws IOException {
        String username = usernameTextfield.getText();
        String password = passwordTextfield.getText();
        // checks given user name and password against database if correct will take you user the KPS Software.
        if (kpsMain.authenticateLogin(username, password)) {
            // for testing us the  username: Bob password: test123
            Parent homescreen = FXMLLoader.load(LoginScreenController.class.getResource("/fxml/HomeScreen.fxml"));
            Scene homeSecne = new Scene(homescreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(homeSecne);
            tempStage.show();
        } else {
            // the details are wrong will ask the user re-enter.
            authticationError.setText("Incorrect try again :(");
            usernameTextfield.clear();
            passwordTextfield.clear();
        }
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        LoginScreenController.kpsMain = kpsMain;
    }
}
