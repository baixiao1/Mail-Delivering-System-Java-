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
import javafx.scene.control.PasswordField;
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
 * Created by Dipen on 25/05/2017. This is the controller used for the Change Password Screen. and will handle the interaction
 * between view and the model.
 */
public class ChangePasswordScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private ImageView avatar;
    @FXML
    private Button manageUser;
    @FXML
    private Button addNewUser;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField retypePassword;
    @FXML
    private Label errorLabel;

    public ChangePasswordScreenController() {
        KPSMain.setLoginScreenController(this);
    }

    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("addNewUser")) {
            Parent changePasswordScreen = FXMLLoader.load(ChangePasswordScreenController.class.getResource("/fxml/AddNewUserScreen.fxml"));
            Scene changePasswordScene = new Scene(changePasswordScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(changePasswordScene);
            tempStage.show();
        } else if (event.toString().contains("manageUser")) {
            Parent manageUserScreen = FXMLLoader.load(ChangePasswordScreenController.class.getResource("/fxml/ManageUserScreen.fxml"));
            Scene manageUserScene = new Scene(manageUserScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(manageUserScene);
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
        if (event.toString().contains("reset")) {
            clearContent(event);
        } else if (event.toString().contains("discard")) {
            returnUserManagement(event);
        } else if (event.toString().contains("accept")) {
            Staff currentStaff = kpsMain.getCurrentStaff();

            String currentPass = currentStaff.getPassword();
            String oldPassField = oldPassword.getText();
            String newPassField = newPassword.getText();
            String retypePassField = retypePassword.getText();

            if (!oldPassField.equals(currentPass)) {
                errorLabel.setText("Old Passwords not match");
                return;
            } else if (!newPassField.equals(retypePassField)) {
                errorLabel.setText("New passwords and re typed password do not match");
                return;
            } else if (!newPassField.matches("^[a-zA-Z0-9]*$")) {
                errorLabel.setText("New passwords must only contain alphanumeric");
                return;
            } else if (newPassField.length() < 5) {
                errorLabel.setText("New passwords must be minimum 5 characters");
                return;
            }

            errorLabel.setText("Password change successful");
            oldPassword.clear();
            newPassword.clear();
            retypePassword.clear();

            kpsMain.changeCurrentStaffPassword(newPassField);
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
        avatar.setImage(new Image(ChangePasswordScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            manageUser.setVisible(false);
            manageUser.setDisable(false);
            addNewUser.setDisable(false);
            addNewUser.setVisible(false);
        }
    }

    /**
     * clears the screen
     *
     * @param event
     */
    private void clearContent(ActionEvent event) {
        Parent changePasswordScreen = null;
        try {
            changePasswordScreen = FXMLLoader.load(ChangePasswordScreenController.class.getResource("/fxml/ChangePasswordScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene changePasswordScene = new Scene(changePasswordScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(changePasswordScene);
        tempStage.show();
    }

    /**
     * returns the user back to the home screen
     *
     * @param event
     */
    private void returnUserManagement(ActionEvent event) {
        Parent userManagementscreen = null;
        try {
            userManagementscreen = FXMLLoader.load(ChangePasswordScreenController.class.getResource("/fxml/UserSettingScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene userManagementScene = new Scene(userManagementscreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(userManagementScene);
        tempStage.show();
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        ChangePasswordScreenController.kpsMain = kpsMain;
    }
}
