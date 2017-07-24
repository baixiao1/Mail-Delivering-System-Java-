package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 * Created by Dipen on 25/05/2017.This is the controller used for the Manage User Screen. and will handle the interaction
 * between view and the model.
 */
public class ManageUserScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView userImage;
    @FXML
    private Label userLable;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label userRole;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label emailAddress;
    @FXML
    private Label username;
    @FXML
    private ComboBox selectUser;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private CheckBox changeRoleCheckBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Label errorLabel;

    public ManageUserScreenController() {
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
            Parent changePasswordScreen = FXMLLoader.load(ManageUserScreenController.class.getResource("/fxml/ChangePasswordScreen.fxml"));
            Scene changePasswordScene = new Scene(changePasswordScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(changePasswordScene);
            tempStage.show();
        } else if (event.toString().contains("addNewUser")) {
            Parent addNewUserScreen = FXMLLoader.load(ManageUserScreenController.class.getResource("/fxml/AddNewUserScreen.fxml"));
            Scene addNewUserScene = new Scene(addNewUserScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(addNewUserScene);
            tempStage.show();
        } else if (event.toString().contains("logout-button")) {
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
        } else if (event.toString().contains("update")) {
            if (selectUser.getValue() == null) {
                errorLabel.setText("Please select a user to update");
            } else if (selectUser.getValue() != null && ((!phoneNumberTextField.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")
                    && !phoneNumberTextField.getText().equals("")) || (phoneNumberTextField.getText().length() <= 7 && phoneNumberTextField.getText().length() >= 7))) {
                errorLabel.setText("Please fill in valid 7 digit phone number");
            } else if (selectUser.getValue() != null && firstNameTextField.getText().equals("") && lastNameTextField.getText().equals("")
                    && emailTextField.getText().equals("") && phoneNumberTextField.getText().equals("") && !changeRoleCheckBox.isSelected()) {
                errorLabel.setText("Please fill in valid information");
            } else {
                if (selectUser.getValue() != null) {
                    String[] content = ((String) selectUser.getValue()).split(" ");

                    boolean vaildUpdate = kpsMain.updateStaffInformation(content[0], content[1], firstNameTextField.getText(),
                            lastNameTextField.getText(), emailTextField.getText(), phoneNumberTextField.getText(), changeRoleCheckBox.isSelected());
                    System.out.println(vaildUpdate);
                    if (!vaildUpdate) {
                        errorLabel.setText("Error please try different information");
                    } else {
                        errorLabel.setText("Information updated successfully");
                    }
                }
            }

        } else if (event.toString().contains("deleteButton")) {
            if (selectUser.getValue() != null) {
                String[] content = ((String) selectUser.getValue()).split(" ");

                if (kpsMain.deleteUser(content[0], content[1])) {
                    clearContent(event);
                }
            }

        } else if (event.toString().contains("selectUser")) {
            String[] content = ((String) selectUser.getValue()).split(" ");
            Staff staff = kpsMain.getMatchedUser(content[0], content[1]);

            firstName.setText("First Name: " + staff.getFirstName());
            userImage.setImage(new Image(ManageUserScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
            lastName.setText("Last Name: " + staff.getLastName());
            emailAddress.setText("Email: " + staff.getEmail());
            phoneNumber.setText("Phone: " + staff.getPhoneNumber());
            username.setText("Username: " + staff.getUserName());
            if (staff.getFirstName().equals(kpsMain.getCurrentStaff().getFirstName()) && staff.getLastName().equals(kpsMain.getCurrentStaff().getLastName())) {
                deleteButton.setDisable(true);
            } else {
                deleteButton.setDisable(false);
            }
            if (staff.isManager()) {
                userRole.setText("Role: Manager");
                changeRoleCheckBox.setDisable(true);
            } else {
                userRole.setText("Role: Clerk");
                changeRoleCheckBox.setDisable(false);
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
        avatar.setImage(new Image(ManageUserScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        for (Staff s : kpsMain.getAllUsers().values()) {
            selectUser.getItems().add(s.getFirstName() + " " + s.getLastName());
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
            changePasswordScreen = FXMLLoader.load(ManageUserScreenController.class.getResource("/fxml/ManageUserScreen.fxml"));
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
            userManagementscreen = FXMLLoader.load(ManageUserScreenController.class.getResource("/fxml/UserSettingScreen.fxml"));
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
        ManageUserScreenController.kpsMain = kpsMain;
    }
}
