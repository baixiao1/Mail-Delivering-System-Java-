package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.KPSMain;
import model.staff.Staff;
import view.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017. This is the controller used for the User Setting Screen. and will handle the interaction
 * between view and the model.
 */
public class UserSettingScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private ImageView avatar;
    @FXML
    private Button manageUser;
    @FXML
    private Button addNewUser;

    /**
     * this constructor is used to set the reference for the model.
     */
    public UserSettingScreenController() {
        KPSMain.setLoginScreenController(this);
    }

    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("ChangePassword")) {
            Parent changePasswordScreen = FXMLLoader.load(UserSettingScreenController.class.getResource("/fxml/ChangePasswordScreen.fxml"));
            Scene changePasswordScene = new Scene(changePasswordScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(changePasswordScene);
            tempStage.show();
        } else if (event.toString().contains("manageUser")) {
            Parent manageUserScreen = FXMLLoader.load(UserSettingScreenController.class.getResource("/fxml/ManageUserScreen.fxml"));
            Scene manageUserScene = new Scene(manageUserScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(manageUserScene);
            tempStage.show();

        } else if (event.toString().contains("addNewUser")) {
            Parent addNewUserScreen = FXMLLoader.load(UserSettingScreenController.class.getResource("/fxml/AddNewUserScreen.fxml"));
            Scene addNewUserScene = new Scene(addNewUserScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(addNewUserScene);
            tempStage.show();
        } else if (event.toString().contains("Exit")) {
            Parent returnHomeScreen = FXMLLoader.load(UserSettingScreenController.class.getResource("/fxml/HomeScreen.fxml"));
            Scene returnHomeScene = new Scene(returnHomeScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(returnHomeScene);
            tempStage.show();
        } else if (event.toString().contains("logout")) {
            DialogBox.LogoutMsg("Logout", "Are you sure to logout?", event);
        }
    }

    /**
     * This method is used to handel local screen button actions.
     *
     * @param event
     */
    public void handleButtons(ActionEvent event) {
        if (event.toString().contains("Exit")) {
            returnHome(event);
        }
    }

    /**
     * Everything that should occur before the screen is displayed should go in here.
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        Staff staff = kpsMain.getCurrentStaff();
        userLable.setText((staff.isManager() ? "Manager" : "Clerk") + " " + staff.getFirstName());
        avatar.setImage(new Image(SendMailScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            manageUser.setVisible(false);
            manageUser.setDisable(false);
            addNewUser.setVisible(false);
            addNewUser.setVisible(false);
        }

    }

    /**
     * this method is used to allow the user to return back home.
     *
     * @param event
     */
    private void returnHome(ActionEvent event) {
        Parent homeScreen = null;
        try {
            homeScreen = FXMLLoader.load(UserSettingScreenController.class.getResource("/fxml/UserSettingScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeScene = new Scene(homeScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeScene);
        tempStage.show();
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        UserSettingScreenController.kpsMain = kpsMain;
    }
}
