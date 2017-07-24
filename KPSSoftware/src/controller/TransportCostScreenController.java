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
import model.route.Route;
import model.staff.Staff;
import view.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.This is the controller used for the Transport Cost Screen. and will handle the interaction
 * between view and the model.
 */
public class TransportCostScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private ComboBox<String> routeCombobox;
    @FXML
    private Label errorLabel;
    @FXML
    private Label notificationLabel;
    @FXML
    private Label orginLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label transportFirmLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label weightCostLabel;
    @FXML
    private Label volumeCostLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private TextField weightTextfield;
    @FXML
    private TextField volumeTextfield;
    @FXML
    private ImageView avatar;

    public TransportCostScreenController() {
        KPSMain.setLoginScreenController(this);
    }

    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("sendMail")) {
            Parent sendMailScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/SendMailScreen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/RouteDiscontinueScreen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/PriceUpdateScreen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/NewRouteScreen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/BusinessFiguresScreen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            Parent reviewLogScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/ReviewLogScreen.fxml"));
            Scene reviewLogScene = new Scene(reviewLogScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(reviewLogScene);
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
        if (event.toString().contains("accept")) {
            if (routeCombobox.getValue() == null) {
                errorLabel.setText("Please fill in all the information");
            } else if (!weightTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || !volumeTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                errorLabel.setText("Please fill in valid numbers in weight or volume");
            } else {
                String[] selectedText = (routeCombobox.getValue()).split(" ");

                int routeID = Integer.parseInt(selectedText[0]);
                Route route = kpsMain.getRoute(routeID);
                double oldWeightCost = route.getCostPerGram();
                double oldVolumeCost = route.getCostPerVolume();
                double weightCost = Double.parseDouble(weightTextfield.getText());
                double volumeCost = Double.parseDouble(volumeTextfield.getText());
                kpsMain.updateRouteTransportCost(routeID, weightCost, volumeCost);
                errorLabel.setText("Transport cost was successfully updated");
                transportCostUpdateNotification(route, oldWeightCost, oldVolumeCost);
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
        userLable.setText((staff.isManager() ? "Manager" : "Clerk") + " " + staff.getFirstName());
        avatar.setImage(new Image(TransportCostScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        for (Integer i : kpsMain.getAllRoutes().keySet()) {
            Route route = kpsMain.getAllRoutes().get(i);
            routeCombobox.getItems().add(route.id + " " + route.getStartLocation().getLocationName() + " ->" + route.getEndLocation().getLocationName() + " : " + route.routeType.toString());
        }
        notificationLabel.setVisible(false);
        orginLabel.setVisible(false);
        destinationLabel.setVisible(false);
        transportFirmLabel.setVisible(false);
        typeLabel.setVisible(false);
        weightCostLabel.setVisible(false);
        volumeCostLabel.setVisible(false);
        durationLabel.setVisible(false);


    }

    /**
     * clears the screen
     *
     * @param event
     */
    private void clearContent(ActionEvent event) {
        Parent transportCostUpdateScreen = null;
        try {
            transportCostUpdateScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/TransportCostScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(transportCostUpdateScene);
        tempStage.show();
    }

    /**
     * returns the user back to the home screen
     *
     * @param event
     */
    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/HomeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();
    }

    /**
     * this is a helpper method that is used to display the transport cost update notification.
     *
     * @param route
     * @param oldWeightCost
     * @param oldVolumeCost
     */
    private void transportCostUpdateNotification(Route route, double oldWeightCost, double oldVolumeCost) {
        orginLabel.setText("Affected Origin: " + route.getStartLocation().getLocationName());
        destinationLabel.setText("Affected Destination: " + route.getEndLocation().getLocationName());
        typeLabel.setText("Type: " + route.routeType.toString());
        weightCostLabel.setText("Old Weight Cost: $" + String.format("%.2f", oldWeightCost) + " New Cost: $" + String.format("%.2f", route.getCostPerGram()));
        volumeCostLabel.setText("Old Volume Cost: $" + String.format("%.2f", oldVolumeCost) + " New Cost: $" + String.format("%.2f", route.getCostPerVolume()));

        transportFirmLabel.setText("Transport Firm: " + route.getTransportFirm());
        durationLabel.setText("Duration " + String.format("%.2f", route.getDuration()) + " Hours");
        notificationLabel.setVisible(true);
        orginLabel.setVisible(true);
        destinationLabel.setVisible(true);
        typeLabel.setVisible(true);
        transportFirmLabel.setVisible(true);
        durationLabel.setVisible(true);
        weightCostLabel.setVisible(true);
        volumeCostLabel.setVisible(true);
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        TransportCostScreenController.kpsMain = kpsMain;
    }
}
