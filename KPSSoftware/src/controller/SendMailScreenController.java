package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.KPSMain;
import model.location.Location;
import model.mail.Mail;
import model.mail.Priority;
import model.staff.Staff;
import view.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.This is the controller used for the Send Mail Screen. and will handle the interaction
 * between view and the model.
 */
public class SendMailScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Label priceLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label costLabel;
    @FXML
    private ImageView avatar;
    @FXML
    private ComboBox<String> originCombobox;
    @FXML
    private ComboBox<String> destinationCombobox;
    @FXML
    private TextField weightTextfield;
    @FXML
    private TextField volumeTextfield;
    @FXML
    private ComboBox<String> priorityCombobox;
    @FXML
    private Button reviewLogsButton;

    private Mail tempMail;

    public SendMailScreenController() {
        KPSMain.setLoginScreenController(this);
    }


    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/RouteDiscontinueScreen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/PriceUpdateScreen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/TransportCostScreen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/NewRouteScreen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/BusinessFiguresScreen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            Parent reviewLogScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/ReviewLogScreen.fxml"));
            Scene reviewLogScene = new Scene(reviewLogScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(reviewLogScene);
            tempStage.show();
        } else if (event.toString().contains("logout")) {
            DialogBox.LogoutMsg("Logout", "Are you sure to logout?",event);
        }
    }

    /**
     * This method is used to handel local screen button actions.
     *
     * @param event
     */
    public void handleButtons(ActionEvent event) {
        if (event.toString().contains("Check Price")) {

            String origin = originCombobox.getValue();
            String destination = destinationCombobox.getValue();
            String weightString = weightTextfield.getText().trim();
            String volumeString = volumeTextfield.getText().trim();
            String priorityString = priorityCombobox.getValue();

            if (origin == null || destination == null || weightString.equals("") || volumeString.equals("") || priorityString == null) {
                errorLabel.setText("Please fill in all the Information");
                return;
            }

            if (!weightString.matches("[0-9]{1,13}(\\.[0-9]*)?") || !volumeString.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                errorLabel.setText("Please fill in valid numbers in weight or volume");
                return;
            }

            double weight = Double.parseDouble(weightString);
            double volume = Double.parseDouble(volumeString);
            Priority priority = Priority.createPriorityFrom(priorityString);

            tempMail = kpsMain.processMail(origin.trim(), destination.trim(), weight, volume, priority);

            if (tempMail != null) {
                errorLabel.setText("Please confirm to send this mail");

                priceLabel.setText(String.format("%.2f", kpsMain.getTempMailRevenue(tempMail)));
                costLabel.setText(String.format("%.2f", kpsMain.getTempMailExpenditure(tempMail)));
                totalPriceLabel.setVisible(true);
                priceLabel.setVisible(true);
                totalCostLabel.setVisible(true);
                costLabel.setVisible(true);

                originCombobox.setDisable(true);
                destinationCombobox.setDisable(true);
                weightTextfield.setDisable(true);
                volumeTextfield.setDisable(true);
                priorityCombobox.setDisable(true);

                ((Button) event.getSource()).setText("Deliver");
            } else {
                errorLabel.setText("We don't support sending mails from " + origin + " to " + destination + " with " + priorityString + " yet");
            }

        } else if (event.toString().contains("Deliver")) {
            if (tempMail == null) {
                clearContent(event);
                return;
            }

            if (kpsMain.deliverMail(tempMail)) {
                clearContent(event);
                errorLabel.setText("Mail was successfully sent");
                tempMail = null;
            } else {
                clearContent(event);
                errorLabel.setText("Logging event failed, please try again");
                tempMail = null;
            }

        } else if (event.toString().contains("reset")) {
            clearContent(event);
        } else if (event.toString().contains("discard")) {
            returnHome(event);
        }
    }


    /**
     * Everything that should occur before the screen is displayed should go in here.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Staff staff = kpsMain.getCurrentStaff();
        userLable.setText((staff.isManager() ? "Manager": "Clerk")+" "+ staff.getFirstName());
        avatar.setImage(new Image(SendMailScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        for (Location loc : kpsMain.getAvailableOrigins()) {
            originCombobox.getItems().add(loc.getLocationName());
        }
        for (Location loc : kpsMain.getAvailableDestinations()) {

            destinationCombobox.getItems().add(loc.getLocationName());
        }
        priorityCombobox.getItems().add(Priority.Domestic_Standard.toString());
        priorityCombobox.getItems().add(Priority.Domestic_Air.toString());
        priorityCombobox.getItems().add(Priority.International_Standard.toString());
        priorityCombobox.getItems().add(Priority.International_Air.toString());
        totalPriceLabel.setVisible(false);
        priceLabel.setVisible(false);
        totalCostLabel.setVisible(false);
        costLabel.setVisible(false);

    }

    /**
     * clears the screen
     * @param event
     */
    private void clearContent(ActionEvent event) {
        Parent sendMailScreen = null;
        try {
            sendMailScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/SendMailScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sendMailScene = new Scene(sendMailScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(sendMailScene);
        tempStage.show();
    }

    /**
     * returns the user back to the home screen
     * @param event
     */
    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/HomeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();
    }


    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        SendMailScreenController.kpsMain = kpsMain;
    }
}
