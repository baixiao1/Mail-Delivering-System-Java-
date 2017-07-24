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
 * Created by Dipen on 25/04/2017.This is the controller used for the Price Update Screen. and will handle the interaction
 * between view and the model.
 */
public class PriceUpdateScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox<String> routeCombobox;
    @FXML
    private TextField weightTextfield;
    @FXML
    private TextField volumeTextfield;
    @FXML
    private ImageView avatar;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private Label originLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label weightPriceLabel;
    @FXML
    private Label volumePriceLabel;
    @FXML
    Label notificationLabel;

    public PriceUpdateScreenController() {
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
            Parent sendMailScreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/SendMailScreen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/RouteDiscontinueScreen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/TransportCostScreen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/NewRouteScreen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/BusinessFiguresScreen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            Parent reviewLogScreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/ReviewLogScreen.fxml"));
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
                errorLabel.setText("Please Fill in all the Information");
            } else if (!weightTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || !volumeTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                errorLabel.setText("Please fill in valid numbers in weight or volume");
            } else {
                String[] selectdText = ((String) routeCombobox.getValue()).split(" ");

                int routeID = Integer.parseInt(selectdText[0]);
                Route route = kpsMain.getRoute(routeID);
                double oldWeightPrice = route.getPricePerGram();
                double oldVolumePrice = route.getPricePerVolume();
                double weightCost = Double.parseDouble(weightTextfield.getText());
                double volumeCost = Double.parseDouble(volumeTextfield.getText());
                kpsMain.updateRouteCustomerPrice(routeID, weightCost, volumeCost);
                errorLabel.setText("Customer price was successfully updated");
                customerPriceUpdateNotification(route, oldWeightPrice, oldVolumePrice);
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
        avatar.setImage(new Image(PriceUpdateScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        for (Integer i : kpsMain.getAllRoutes().keySet()) {
            Route route = kpsMain.getAllRoutes().get(i);
            routeCombobox.getItems().add(route.id + " " + route.getStartLocation().getLocationName() + " ->" + route.getEndLocation().getLocationName() + " : " + route.routeType.toString());
        }
        notificationLabel.setVisible(false);
        originLabel.setVisible(false);
        destinationLabel.setVisible(false);
        priorityLabel.setVisible(false);
        weightPriceLabel.setVisible(false);
        volumePriceLabel.setVisible(false);


    }

    /**
     * clears the screen
     *
     * @param event
     */
    private void clearContent(ActionEvent event) {
        Parent priceUpdateScreen = null;
        try {
            priceUpdateScreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/PriceUpdateScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene priceUpdateScene = new Scene(priceUpdateScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(priceUpdateScene);
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
            homescreen = FXMLLoader.load(PriceUpdateScreenController.class.getResource("/fxml/HomeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();
    }

    /**
     * Helpper method used to display the notification for the price update
     *
     * @param route
     * @param oldWeightPrice
     * @param oldVolumePrice
     */
    private void customerPriceUpdateNotification(Route route, double oldWeightPrice, double oldVolumePrice) {
        originLabel.setText("Affected Origin: " + route.getStartLocation().getLocationName());
        destinationLabel.setText("Affected Destination: " + route.getEndLocation().getLocationName());
        priorityLabel.setText("Type: " + route.routeType.toString());
        weightPriceLabel.setText("Old Weight Price: $" + String.format("%.2f", oldWeightPrice) + " New Price: $" + String.format("%.2f", route.getPricePerGram()));
        volumePriceLabel.setText("Old Volume Price: $" + String.format("%.2f", oldVolumePrice) + " New Price: $" + String.format("%.2f", route.getPricePerVolume()));
        notificationLabel.setVisible(true);
        originLabel.setVisible(true);
        destinationLabel.setVisible(true);
        priorityLabel.setVisible(true);
        weightPriceLabel.setVisible(true);
        volumePriceLabel.setVisible(true);
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        PriceUpdateScreenController.kpsMain = kpsMain;
    }
}
