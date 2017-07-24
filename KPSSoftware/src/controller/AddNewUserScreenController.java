package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
 * Created by Dipen on 25/05/2017. This is the controller used for the Add New User Screen. and will handle the interaction
 * between view and the model.
 */
public class AddNewUserScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView avatar;
    @FXML
    private TextField firstNameTextfield;
    @FXML
    private TextField lastNameTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField phoneTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private CheckBox managerCheckBox;
    @FXML
    private CheckBox clerkCheckBox;

    public AddNewUserScreenController() {
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
            Parent changePasswordScreen = FXMLLoader.load(AddNewUserScreenController.class.getResource("/fxml/ChangePasswordScreen.fxml"));
            Scene changePasswordScene = new Scene(changePasswordScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(changePasswordScene);
            tempStage.show();
        } else if (event.toString().contains("ManageUser")) {
            Parent manageUserScreen = FXMLLoader.load(AddNewUserScreenController.class.getResource("/fxml/ManageUserScreen.fxml"));
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
            if (firstNameTextfield.getText().equals("") || lastNameTextfield.getText().equals("") || emailTextfield.getText().equals("") ||
                    phoneTextfield.getText().equals("") || usernameTextfield.getText().equals("") || (!managerCheckBox.isSelected() && !clerkCheckBox.isSelected())) {
                errorLabel.setText("Error :( Please make sure all the information is provided Correctly");
            } else if (!phoneTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || phoneTextfield.getText().length() <= 7) {
                errorLabel.setText("Please fill in valid phone numbers");
            } else {
                String userName = usernameTextfield.getText();
                //Default password of the user
                String password = "123456";
                boolean isManager = managerCheckBox.isSelected();
                String firstName = firstNameTextfield.getText();
                String lastName = lastNameTextfield.getText();
                String email = emailTextfield.getText();
                String phoneNumber = phoneTextfield.getText();

                boolean userCreated = kpsMain.addNewUser(userName, password, isManager, firstName, lastName, email, phoneNumber);
                if (!userCreated) {
                    errorLabel.setText("Error :( Please try again with a different username");
                } else {
                    DialogBox.displayMsg("User Information", "UserName: " + userName + '\n' + "Default Password: " + password);
                    errorLabel.setText("New Staff added");
                    clearContent(event);
                }
            }
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
        avatar.setImage(new Image(AddNewUserScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));

    }

    /**
     * clears the screen
     *
     * @param event
     */
    private void clearContent(ActionEvent event) {
        Parent changePasswordScreen = null;
        try {
            changePasswordScreen = FXMLLoader.load(AddNewUserScreenController.class.getResource("/fxml/AddNewUserScreen.fxml"));
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
            userManagementscreen = FXMLLoader.load(AddNewUserScreenController.class.getResource("/fxml/UserSettingScreen.fxml"));
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
        AddNewUserScreenController.kpsMain = kpsMain;
    }
}

