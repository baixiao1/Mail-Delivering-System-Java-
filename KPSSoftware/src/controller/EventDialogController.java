package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.KPSMain;
import model.event.*;
import model.mail.Mail;
import model.route.Route;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 15/06/2017. This is the controller used for the Event Information Screen. and will handle the interaction
 * between view and the model.
 */
public class EventDialogController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label labelOne;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;
    @FXML
    private Label labelFour;
    @FXML
    private Label labelFive;
    @FXML
    private Label labelSix;
    @FXML
    private Label labelSeven;
    @FXML
    private Label labelEight;
    @FXML
    private Label labelNine;
    @FXML
    private Label labelTen;
    @FXML
    private Label labelEleven;
    @FXML
    private Label labelTwelve;
    @FXML
    private Label labelThirteen;
    @FXML
    private Label eventTypeLabel;
    private static Event eventType;

    public EventDialogController() {
        KPSMain.setLoginScreenController(this);
    }

    /**
     * This method is used to handel local screen button actions.
     *
     * @param event
     * @throws IOException
     */
    public void handleButtons(ActionEvent event) throws IOException {
        if (event.toString().contains("closeButton")) {
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.close();
        }
    }

    /**
     * Everything that should occur before the screen is displayed should go in here.
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(eventType);
        if (eventType instanceof MailDeliveryEvent) {
            eventTypeLabel.setText("Mail Delivery Event");
            Mail mailEvent = kpsMain.getMail(((MailDeliveryEvent) eventType).getMailId());
            String one = "Origin: " + mailEvent.getOrigin().getLocationName();
            String two = "Destination: " + mailEvent.getDestination().getLocationName();
            String three = "Weight: " + String.format("%.2f", mailEvent.getWeight());
            String four = "Volume: " + String.format("%.2f", mailEvent.getVolume());
            String five = "Priority: " + mailEvent.getPriority().name();
            String six = "Date: " + mailEvent.getDeliveryDate().toString();
            String thirteen = "Route: ";
            for (Route route : mailEvent.getRoutes()) {
                thirteen = thirteen + route.getRouteDescription();
            }
            setEventInformation(one, two, three, four, five, six, "", "", "", "", "", "", thirteen);
        } else if (eventType instanceof CustomerPriceUpdateEvent) {
            eventTypeLabel.setText("Customer Price Update Event");
            CustomerPriceUpdateEvent customerPriceUpdateEvent = (CustomerPriceUpdateEvent) eventType;
            Route route = kpsMain.getRoute(customerPriceUpdateEvent.getRouteId());
            String one = "Affected Origin: " + route.getStartLocation().getLocationName();
            String two = "Affected Destination: " + route.getEndLocation().getLocationName();
            String three = "Old Price Per Gram: " + String.format("%.2f", customerPriceUpdateEvent.getOldPricePerGram());
            String four = "Old Price Per Volume: " + String.format("%.2f", customerPriceUpdateEvent.getOldPricePerVolume());
            String five = "New Price Per Gram: " + String.format("%.2f", customerPriceUpdateEvent.getNewPricePerGram());
            String six = "New Price Per Volume: " + String.format("%.2f", customerPriceUpdateEvent.getNewPricePerVolume());
            String seven = "Route Type: " + route.routeType.name();
            String eight = "Transport Firm: " + route.getTransportFirm();
            String nine = "Duration: " + String.format("%.2f", route.getDuration());
            String ten = "Status: " + (route.isActive() ? "Active" : "Deactivated");
            String eleven = "Date: " + customerPriceUpdateEvent.getTimeStamp().toString();
            setEventInformation(one, two, three, four, five, six, seven, eight, nine, ten, eleven, "", "");
        } else if (eventType instanceof RouteAdditionEvent) {
            eventTypeLabel.setText("New Route Creation Event");
            RouteAdditionEvent routeAdditionEvent = (RouteAdditionEvent) eventType;
            Route route = kpsMain.getRoute(routeAdditionEvent.getRouteId());
            String one = "New Origin: " + route.getStartLocation().getLocationName();
            String two = "New Destination: " + route.getEndLocation().getLocationName();
            String three = "Price Per Gram: " + String.format("%.2f", route.getPricePerGram());
            String four = "Price Per Volume: " + String.format("%.2f", route.getPricePerVolume());
            String five = "Cost Per Gram: " + String.format("%.2f", route.getCostPerGram());
            String six = "Cost Price Per Volume: " + String.format("%.2f", route.getCostPerVolume());
            String seven = "Route Type: " + route.routeType.name();
            String eight = "Transport Firm: " + route.getTransportFirm();
            String nine = "Duration: " + String.format("%.2f", route.getDuration());
            String ten = "Status: " + (route.isActive() ? "Active" : "Deactivated");
            String eleven = "Date: " + routeAdditionEvent.getTimeStamp().toString();
            setEventInformation(one, two, three, four, five, six, seven, eight, nine, ten, eleven, "", "");
        } else if (eventType instanceof RouteDeactivationEvent) {
            eventTypeLabel.setText("Route Deactivated Event");
            RouteDeactivationEvent routeDeactivationEvent = (RouteDeactivationEvent) eventType;
            Route route = kpsMain.getRoute(routeDeactivationEvent.getRouteId());
            String one = "Deactivated Origin: " + route.getStartLocation().getLocationName();
            String two = "Deactivated Destination: " + route.getEndLocation().getLocationName();
            String three = "Price Per Gram: " + String.format("%.2f", route.getPricePerGram());
            String four = "Price Per Volume: " + String.format("%.2f", route.getPricePerVolume());
            String five = "Cost Per Gram: " + String.format("%.2f", route.getCostPerGram());
            String six = "Cost Price Per Volume: " + String.format("%.2f", route.getCostPerVolume());
            String seven = "Route Type: " + route.routeType.name();
            String eight = "Transport Firm: " + route.getTransportFirm();
            String nine = "Duration: " + String.format("%.2f", route.getDuration());
            String ten = "Status: " + (route.isActive() ? "Active" : "Deactivated");
            String eleven = "Date: " + routeDeactivationEvent.getTimeStamp().toString();
            setEventInformation(one, two, three, four, five, six, seven, eight, nine, ten, eleven, "", "");

        } else if (eventType instanceof TransportCostUpdateEvent) {
            eventTypeLabel.setText("Transport Cost Update Event");
            TransportCostUpdateEvent transportCostUpdateEvent = (TransportCostUpdateEvent) eventType;
            Route route = kpsMain.getRoute(transportCostUpdateEvent.getRouteId());
            String one = "Deactivated Origin: " + route.getStartLocation().getLocationName();
            String two = "Deactivated Destination: " + route.getEndLocation().getLocationName();
            String three = "Old Price Per Gram: " + String.format("%.2f", transportCostUpdateEvent.getOldCostPerGram());
            String four = "Old Price Per Volume: " + String.format("%.2f", transportCostUpdateEvent.getOldCostPerVolume());
            String five = "New Price Per Gram: " + String.format("%.2f", transportCostUpdateEvent.getNewCostPerGram());
            String six = "New Price Per Volume: " + String.format("%.2f", transportCostUpdateEvent.getNewCostPerVolume());
            String seven = "Route Type: " + route.routeType.name();
            String eight = "Transport Firm: " + route.getTransportFirm();
            String nine = "Duration: " + String.format("%.2f", route.getDuration());
            String ten = "Status: " + (route.isActive() ? "Active" : "Deactivated");
            String eleven = "Date: " + transportCostUpdateEvent.getTimeStamp().toString();
            setEventInformation(one, two, three, four, five, six, seven, eight, nine, ten, eleven, "", "");
        }
    }

    /**
     * Helpper method used to set all the labels on the screen. allows for code reusability.
     *
     * @param one
     * @param two
     * @param three
     * @param four
     * @param five
     * @param six
     * @param seven
     * @param eight
     * @param nine
     * @param ten
     * @param eleven
     * @param twelve
     * @param thirteen
     */
    private void setEventInformation(String one, String two, String three, String four, String five, String six, String seven,
                                     String eight, String nine, String ten, String eleven, String twelve, String thirteen) {

        labelOne.setText(one);
        labelTwo.setText(two);
        labelThree.setText(three);
        labelFour.setText(four);
        labelFive.setText(five);
        labelSix.setText(six);
        labelSeven.setText(seven);
        labelEight.setText(eight);
        labelNine.setText(nine);
        labelTen.setText(ten);
        labelEleven.setText(eleven);
        labelTwelve.setText(twelve);
        labelThirteen.setText(thirteen);


    }

    /**
     * used by the Review Logs screen to set the type of event, for which more information is required.
     *
     * @param eventTypes
     */
    public static void setEvent(Event eventTypes) {
        eventType = eventTypes;
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        EventDialogController.kpsMain = kpsMain;
    }
}
