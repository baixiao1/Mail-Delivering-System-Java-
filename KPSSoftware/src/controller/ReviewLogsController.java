package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.KPSMain;
import model.event.*;
import model.staff.Staff;
import view.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 14/06/2017.This is the controller used for the Review Logs Screen. and will handle the interaction
 * between view and the model.
 */
public class ReviewLogsController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private TableView tabelView;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn eventNameColumn;
    @FXML
    private Label userLable;
    @FXML
    private ImageView avatar;

    public ReviewLogsController() {
        KPSMain.setLoginScreenController(this);
    }

    /**
     * this method is used to change between screens.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("sendMail")) {
            Parent sendMailScreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/SendMailScreen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/RouteDiscontinueScreen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/PriceUpdateScreen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/TransportCostScreen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/NewRouteScreen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/BusinessFiguresScreen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("logout")) {
            DialogBox.LogoutMsg("Logout", "Are you sure to logout?",event);
        }
    }

    /**
     * This method is used to handel local screen button actions.
     * @param event
     * @throws IOException
     */
    public void handleButtons(ActionEvent event) throws IOException {
        if (event.toString().contains("exit")) {
            returnHome(event);
        } else if (event.toString().contains("view")) {
            EventTable evetTable = (EventTable) tabelView.getSelectionModel().getSelectedItem();
            Integer id = evetTable.getId();
            Event eventType = kpsMain.getAllEvent().get(id);
            EventDialogController.setEvent(eventType);

            displayEventDialog();
        }
    }

    /**
     * Helper method that is used to call the Event Information screen.
     * @throws IOException
     */
    private void displayEventDialog() throws IOException {
        Parent loginScreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/DisplayEventsDialog.fxml"));
        Scene scene = new Scene(loginScreen);
        Stage window = new Stage();
        Image iconImage = new Image(ReviewLogsController.class.getResourceAsStream("/img/KPS.png"));
        window.getIcons().add(iconImage);
        // this makes it so you can't click on the window other then this one
        window.initModality(Modality.APPLICATION_MODAL);
        // sets the title
        window.setTitle("Event Information");
        // sets the weight and height
        window.setMinHeight(200);
        window.setMinWidth(300);


        window.setScene(scene);
        window.show();
    }

    /**
     * Everything that should occur before the screen is displayed should go in here.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Staff staff = kpsMain.getCurrentStaff();
        userLable.setText((staff.isManager() ? "Manager": "Clerk")+" "+ staff.getFirstName());
        avatar.setImage(new Image(ReviewLogsController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));

        Map<Integer, Event> eventMap = kpsMain.getAllEvent();
        String eventName = "";

        ObservableList<EventTable> items = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<EventTable, Integer>("id"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<EventTable, String>("eventName"));

        for (Integer id : eventMap.keySet()) {
            Event event = eventMap.get(id);
            if (event instanceof MailDeliveryEvent) {
                eventName = " Mail Delivery Event";
            } else if (event instanceof CustomerPriceUpdateEvent) {
                eventName = " Customer Price Update Event";
            } else if (event instanceof RouteAdditionEvent) {
                eventName = " Route Addition Event";
            } else if (event instanceof RouteDeactivationEvent) {
                eventName = " Route Deactivation Event";
            } else if (event instanceof TransportCostUpdateEvent) {
                eventName = " Transport Cost Update Event";
            }
            items.add(new EventTable(id, eventName));
        }
        tabelView.setItems(items);
    }

    /**
     * returns the user back to the home screen
     * @param event
     */
    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(ReviewLogsController.class.getResource("/fxml/HomeScreen.fxml"));
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
        ReviewLogsController.kpsMain = kpsMain;
    }

    /**
     * This is a helper class used by Table View Columns to get the values.
     */
    public class EventTable {
        private Integer id;
        private String eventName;

        public EventTable(Integer id, String eventName) {
            this.id = id;
            this.eventName = eventName;
        }

        public Integer getId() {
            return id;
        }

        public String getEventName() {
            return eventName;
        }
    }
}
