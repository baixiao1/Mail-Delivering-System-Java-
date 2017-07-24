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
import model.route.RouteType;
import model.staff.Staff;
import view.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017. This is the controller used for the Add New Route Screen. and will handle the interaction
 * between view and the model.
 */
public class NewRouteScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Label errorLabel;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private ComboBox<String> typeCombobox;
    @FXML
    private TextField weightPriceTextfield;
    @FXML
    private TextField volumePriceTextfield;
    @FXML
    private TextField durationTextfield;
    @FXML
    private TextField originTextfield;
    @FXML
    private TextField destinationTextfield;
    @FXML
    private TextField companyTextfield;
    @FXML
    private TextField weightCostTextfield;
    @FXML
    private TextField volumeCostTextfield;
    @FXML
    private ImageView avatar;


    public NewRouteScreenController() {
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
            Parent sendMailScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/SendMailScreen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/RouteDiscontinueScreen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/PriceUpdateScreen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/TransportCostScreen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/BusinessFiguresScreen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            Parent reviewLogScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/ReviewLogScreen.fxml"));
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

            String origin = originTextfield.getText().trim();
            String destination = destinationTextfield.getText().trim();
            String transportCompany = companyTextfield.getText().trim();
            String durationString = durationTextfield.getText().trim();
            String typeString = typeCombobox.getValue();
            String WeightPriceString = weightPriceTextfield.getText().trim();
            String VolumePriceString = volumePriceTextfield.getText().trim();
            String WeightCostString = weightCostTextfield.getText().trim();
            String VolumeCostString = volumeCostTextfield.getText().trim();

            if (origin.equals("") || destination.equals("") || transportCompany.equals("") || durationString.equals("")
                    || typeString == null || WeightPriceString.equals("") || VolumePriceString.equals("")
                    || WeightCostString.equals("") || VolumeCostString.equals("")) {
                errorLabel.setText("Please fill in all the fields");
                return;
            }

            if (!WeightPriceString.matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !VolumePriceString.matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !WeightCostString.matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !VolumeCostString.matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !durationString.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                errorLabel.setText("Please fill in valid numbers for price, cost or duration");
                return;
            }

            double WeightPrice = Double.parseDouble(WeightPriceString);
            double VolumePrice = Double.parseDouble(VolumePriceString);
            double WeightCost = Double.parseDouble(WeightCostString);
            double VolumeCost = Double.parseDouble(VolumeCostString);
            double duration = Double.parseDouble(durationString);
            RouteType type = RouteType.createFromString(typeString);

            kpsMain.addRoute(origin, destination, type, duration, transportCompany, WeightPrice, VolumePrice, WeightCost, VolumeCost);
            errorLabel.setText("New Route Successfully Created");

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
        avatar.setImage(new Image(NewRouteScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        typeCombobox.getItems().add(RouteType.Air.toString());
        typeCombobox.getItems().add(RouteType.Land.toString());
        typeCombobox.getItems().add(RouteType.Sea.toString());


    }

    /**
     * clears the screen
     *
     * @param event
     */
    private void clearContent(ActionEvent event) {
        Parent newRouteScreen = null;
        try {
            newRouteScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/NewRouteScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene newRouteScene = new Scene(newRouteScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(newRouteScene);
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
            homescreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/HomeScreen.fxml"));
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
        NewRouteScreenController.kpsMain = kpsMain;
    }
}
