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
 * Created by Dipen on 25/04/2017.This is the controller used for the Route Discontinue Screen. and will handle the interaction
 * between view and the model.
 */
public class RouteDiscontinueScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private ComboBox<String> routeCombobox;
    @FXML
    private ImageView avatar;
    @FXML
    private Label affectedOriginLabel;
    @FXML
    private Label affectedDestinationLabel;
    @FXML
    private Label transportFirmLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label notificationLabel;

    public RouteDiscontinueScreenController() {
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
            Parent sendMailScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/SendMailScreen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/PriceUpdateScreen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/TransportCostScreen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/NewRouteScreen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/BusinessFiguresScreen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            Parent reviewLogScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/ReviewLogScreen.fxml"));
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
            //this is view button in the gui
            String selectedItem = routeCombobox.getValue();

            if (selectedItem == null) {
                return;
            }

            String[] selectedText = selectedItem.split(" ");
            int routeID = Integer.parseInt(selectedText[0]);

            Route route = kpsMain.getRoute(routeID);
            if (route != null) {
                discontinueNotification(route);
            }

        } else if (event.toString().contains("reset")) {
            clearContent(event);

        } else if (event.toString().contains("discard")) {
            returnHome(event);
        } else if (event.toString().contains("discontinueButton")) {
            String selectedItem = routeCombobox.getValue();

            if (selectedItem == null) {
                return;
            }

            String[] selectedText = selectedItem.split(" ");
            int routeID = Integer.parseInt(selectedText[0]);
            kpsMain.deactivateRoute(routeID);
            Route route = kpsMain.getRoute(routeID);

            if (route != null) {
                discontinueNotification(route);
            }
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
        avatar.setImage(new Image(RouteDiscontinueScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        for (Integer i : kpsMain.getAllRoutes().keySet()) {
            Route route = kpsMain.getAllRoutes().get(i);
            routeCombobox.getItems().add(route.id + " " + route.getStartLocation().getLocationName() + " ->" + route.getEndLocation().getLocationName() + " : " + route.routeType.toString());
        }
        notificationLabel.setVisible(false);
        affectedOriginLabel.setVisible(false);
        affectedDestinationLabel.setVisible(false);
        transportFirmLabel.setVisible(false);
        typeLabel.setVisible(false);
        statusLabel.setVisible(false);
    }

    /**
     * clears the screen
     *
     * @param event
     */
    private void clearContent(ActionEvent event) {
        Parent routeDiscontinueScreen = null;
        try {
            routeDiscontinueScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/RouteDiscontinueScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(routeDiscontinueScene);
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
            homescreen = FXMLLoader.load(controller.SendMailScreenController.class.getResource("/fxml/HomeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();
    }

    /**
     * This is a helper method used to display the route discontinue route.
     * @param route
     */
    private void discontinueNotification(Route route) {
        affectedOriginLabel.setText("Affected Origin: " + route.getStartLocation().getLocationName());
        affectedDestinationLabel.setText("Affected Destination: " + route.getEndLocation().getLocationName());
        transportFirmLabel.setText("Tranport Firm: " + route.getTransportFirm());
        typeLabel.setText("Type: " + route.routeType.toString());
        statusLabel.setText("Status: " + (route.isActive() ? "Active" : "Deactivated"));
        notificationLabel.setVisible(true);
        affectedOriginLabel.setVisible(true);
        affectedDestinationLabel.setVisible(true);
        transportFirmLabel.setVisible(true);
        typeLabel.setVisible(true);
        statusLabel.setVisible(true);
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        RouteDiscontinueScreenController.kpsMain = kpsMain;
    }
}
