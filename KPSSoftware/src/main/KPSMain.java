package main;

import controller.*;
import model.KPSModel;
import model.database.DataPopulater;
import model.event.Event;
import model.location.Location;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;
import view.GUI;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * This class is the top-level controller of the app. The main method of the programme is located in this class.
 *
 * @author Dipen & Hector
 * @version 18/04/2017
 */
public class KPSMain {

    // ================== Model objects ============================

    /**
     * The top-level model object. This sole model provides all APIs from model world.
     */
    private KPSModel kpsModel;

    // ================== controller objects =======================

    private static LoginScreenController loginScreenController;
    private static HomeScreenController homeScreenController;
    private static UserSettingScreenController userSettingScreenController;
    private static ChangePasswordScreenController changePasswordScreenController;
    private static ManageUserScreenController manageUserScreenControllerl;
    private static AddNewUserScreenController addNewUserScreenController;
    private static SendMailScreenController sendMailScreenController;
    private static RouteDiscontinueScreenController routeDiscontinueScreenController;
    private static PriceUpdateScreenController priceUpdateScreenController;
    private static TransportCostScreenController transportCostScreenController;
    private static NewRouteScreenController newRouteScreenController;
    private static BusinessFiguresScreenController businessFiguresScreenController;
    private static ReviewLogsController reviewLogsController;
    private static EventDialogController eventDialogController;

    /**
     * Constructor
     */
    public KPSMain() {
        kpsModel = new KPSModel();

        //the code below will set the reference of this object in the all the controller.
        LoginScreenController.setKPSMain(this);
        HomeScreenController.setKPSMain(this);
        UserSettingScreenController.setKPSMain(this);
        ChangePasswordScreenController.setKPSMain(this);
        ManageUserScreenController.setKPSMain(this);
        AddNewUserScreenController.setKPSMain(this);
        SendMailScreenController.setKPSMain(this);
        RouteDiscontinueScreenController.setKPSMain(this);
        PriceUpdateScreenController.setKPSMain(this);
        TransportCostScreenController.setKPSMain(this);
        NewRouteScreenController.setKPSMain(this);
        BusinessFiguresScreenController.setKPSMain(this);
        ReviewLogsController.setKPSMain(this);
        EventDialogController.setKPSMain(this);
    }

    /**
     * this method is used to get the current user logged into the system
     *
     * @return Staff
     */
    public Staff getCurrentStaff() {
        return kpsModel.getCurrentStaff();
    }

    /**
     * this method is used to get all the users registered in teh system
     *
     * @return Map<Integer, Staff>
     */
    public Map<Integer, Staff> getAllUsers() {
        return kpsModel.getAllStaffs();
    }

    /**
     * this method is used to all a new user to the system
     *
     * @param userName
     * @param password
     * @param isManager
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @return
     */
    public boolean addNewUser(String userName, String password, boolean isManager, String firstName,
                              String lastName, String email, String phoneNumber) {
        return kpsModel.createNewStaff(userName, password, isManager, firstName, lastName, email, phoneNumber);
    }

    /**
     * This method is used to deleted the user from the system.
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public boolean deleteUser(String firstName, String lastName) {
        for (Staff s : kpsModel.getAllStaffs().values()) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                return kpsModel.deleteStaff(s.id);
            }
        }

        return false;
    }

    /**
     * this method is used to update the staff information, will return true is successful else false
     *
     * @param firstName
     * @param lastName
     * @param newFirstName
     * @param newLastName
     * @param newEmail
     * @param newPhone
     * @param changeRole
     * @return
     */
    public boolean updateStaffInformation(String firstName, String lastName, String newFirstName, String newLastName, String newEmail, String newPhone, boolean changeRole) {
        Staff tempStaff = null;

        for (Staff s : kpsModel.getAllStaffs().values()) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                tempStaff = s;
            }
        }

        if (tempStaff == null) {
            return false;
        }

        if (changeRole) {
            //if the user wants to change roles to manager
            kpsModel.deleteStaff(tempStaff.id);

            String fName = "".equals(newFirstName) ? tempStaff.getFirstName() : newFirstName;
            String LName = "".equals(newLastName) ? tempStaff.getLastName() : newLastName;
            String nMail = "".equals(newEmail) ? tempStaff.getEmail() : newEmail;
            String nPhone = "".equals(newPhone) ? tempStaff.getPhoneNumber() : newPhone;

            kpsModel.createNewStaff(tempStaff.getUserName(), tempStaff.getPassword(), true, fName, LName, nMail, nPhone);

            return true;

        } else {
            // if the user just want ot update an users information
            if (!"".equals(newFirstName)) {
                tempStaff.setFirstName(newFirstName);
            }
            if (!"".equals(newLastName)) {
                tempStaff.setLastName(newLastName);
            }
            if (!"".equals(newEmail)) {
                tempStaff.setEmail(newEmail);
            }
            if (!"".equals(newPhone)) {
                tempStaff.setPhoneNumber(newPhone);
            }
            return true;
        }
    }

    /**
     * this method is used to change the current staffs password and will return true if successful else false.
     *
     * @param newPassword
     * @return
     */
    public boolean changeCurrentStaffPassword(String newPassword) {
        Staff currentStaff = getCurrentStaff();

        return kpsModel.updateStaff(currentStaff.id, currentStaff.getUserName(), newPassword, currentStaff.isManager(),
                currentStaff.getFirstName(), currentStaff.getLastName(), currentStaff.getEmail(), currentStaff.getPhoneNumber());
    }

    /**
     * @param firstName
     * @param lastName
     * @return the user in the system that matches the given first name and last name. If there is no match, null is
     * returned.
     */
    public Staff getMatchedUser(String firstName, String lastName) {
        for (Staff s : kpsModel.getAllStaffs().values()) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                return s;
            }
        }

        return null;
    }

    /**
     * this method is used to log a user in to the system, it checks there username and password is successful then it
     * will return true else false
     *
     * @param username
     * @param password
     * @return
     */
    public boolean authenticateLogin(String username, String password) {
        return kpsModel.login(username, password);
    }

    /**
     * This method is by the controllers to set there references in this class.
     *
     * @param controllers
     */
    public static void setLoginScreenController(Object controllers) {
        if (controllers instanceof LoginScreenController) {
            loginScreenController = (LoginScreenController) controllers;
        } else if (controllers instanceof HomeScreenController) {
            homeScreenController = (HomeScreenController) controllers;
        } else if (controllers instanceof UserSettingScreenController) {
            userSettingScreenController = (UserSettingScreenController) controllers;
        } else if (controllers instanceof ChangePasswordScreenController) {
            changePasswordScreenController = (ChangePasswordScreenController) controllers;
        } else if (controllers instanceof ManageUserScreenController) {
            manageUserScreenControllerl = (ManageUserScreenController) controllers;
        } else if (controllers instanceof AddNewUserScreenController) {
            addNewUserScreenController = (AddNewUserScreenController) controllers;
        } else if (controllers instanceof SendMailScreenController) {
            sendMailScreenController = (SendMailScreenController) controllers;
        } else if (controllers instanceof RouteDiscontinueScreenController) {
            routeDiscontinueScreenController = (RouteDiscontinueScreenController) controllers;
        } else if (controllers instanceof PriceUpdateScreenController) {
            priceUpdateScreenController = (PriceUpdateScreenController) controllers;
        } else if (controllers instanceof TransportCostScreenController) {
            transportCostScreenController = (TransportCostScreenController) controllers;
        } else if (controllers instanceof NewRouteScreenController) {
            newRouteScreenController = (NewRouteScreenController) controllers;
        } else if (controllers instanceof BusinessFiguresScreenController) {
            businessFiguresScreenController = (BusinessFiguresScreenController) controllers;
        } else if (controllers instanceof ReviewLogsController) {
            reviewLogsController = (ReviewLogsController) controllers;
        } else if (controllers instanceof EventDialogController) {
            eventDialogController = (EventDialogController) controllers;
        }
    }

    /**
     * this method is used to get all the available destination.
     *
     * @return
     */
    public Set<Location> getAvailableDestinations() {
        return kpsModel.getAvailableDestinations();
    }

    /**
     * this method is used to get all the available origi.
     *
     * @return
     */
    public Set<NZLocation> getAvailableOrigins() {
        return kpsModel.getAvailableOrigins();
    }

    /**
     * this method is used to process the mail NOT send. it will check if mail can be sent with the information
     * provided. if the the mail can be sent it will return a Mail object else null.
     *
     * @param origin
     * @param destination
     * @param weight
     * @param volume
     * @param priority
     * @return
     */
    public Mail processMail(String origin, String destination, double weight, double volume, Priority priority) {
        return kpsModel.processMail(origin, destination, weight, volume, priority);
    }

    /**
     * this method is used to deliver the mail having passed the mail to send. if successful it will return true else
     * false.
     *
     * @param mail
     * @return
     */
    public boolean deliverMail(Mail mail) {
        return kpsModel.deliverMail(mail);
    }

    /**
     * this method is used to get the temporary revenue for a mail.
     *
     * @param tempMail
     * @return
     */
    public double getTempMailRevenue(Mail tempMail) {
        return kpsModel.getTempMailRevenue(tempMail);
    }

    /**
     * this method used to get the temporary expenditure for a Mail.
     *
     * @param tempMail
     * @return
     */
    public double getTempMailExpenditure(Mail tempMail) {
        return kpsModel.getTempMailExpenditure(tempMail);
    }

    /**
     * this method will get all the routes in the system.
     *
     * @return
     */
    public Map<Integer, Route> getAllRoutes() {
        return kpsModel.getAllRoutes();
    }

    /**
     * this method will get the route give a route id
     *
     * @param id
     * @return
     */
    public Route getRoute(int id) {
        return kpsModel.getRouteById(id);
    }

    /**
     * this method is used to deactivate a route given a route id and will return true if successful else false.
     *
     * @param routId
     * @return
     */
    public boolean deactivateRoute(int routId) {
        return kpsModel.deactivateRoute(routId);
    }

    /**
     * this method is used to update the customer price
     *
     * @param idToUpdate
     * @param newPricePerGram
     * @param newPricePerVolume
     */
    public void updateRouteCustomerPrice(int idToUpdate, double newPricePerGram, double newPricePerVolume) {
        kpsModel.updateCustomerPrice(idToUpdate, newPricePerGram, newPricePerVolume);
    }

    /**
     * this method is used to update the transport cost.
     *
     * @param idToUpdate
     * @param newCostPerGram
     * @param newCostPerVolume
     */
    public void updateRouteTransportCost(int idToUpdate, double newCostPerGram, double newCostPerVolume) {
        kpsModel.updateTransportCost(idToUpdate, newCostPerGram, newCostPerVolume);
    }

    /**
     * this method is used to add a new route to the system.
     *
     * @param startString
     * @param endString
     * @param routeType
     * @param duration
     * @param transportFirm
     * @param pricePerGram
     * @param pricePerVolume
     * @param costPerGram
     * @param costPerVolume
     */
    public void addRoute(String startString, String endString, RouteType routeType, double duration, String transportFirm,
                         double pricePerGram, double pricePerVolume, double costPerGram, double costPerVolume) {
        kpsModel.addRoute(startString, endString, routeType, duration, transportFirm, pricePerGram, pricePerVolume, costPerGram, costPerVolume);
    }

    /**
     * this method is used to get critical mail.
     *
     * @return
     */
    public Map<Integer, Mail> getCriticalMails() {
        return kpsModel.getCriticalMails();
    }

    /**
     * this method is used to get the total revenue
     *
     * @param mails
     * @return
     */
    public double getTotalRevenue(Map<Integer, Mail> mails) {
        return KPSModel.calculateTotalRevenue(mails);
    }

    /**
     * this method is used to get the total expenditure
     *
     * @param mails
     * @return
     */
    public double getTotalExpenditure(Map<Integer, Mail> mails) {
        return KPSModel.calculateTotalExpenditure(mails);
    }

    /**
     * this method is used to get the events based on a start and end date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<Integer, Event> getEvensByStartEndTime(LocalDate startDate, LocalDate endDate) {
        return kpsModel.getEventsByStartAndEndTime(startDate, endDate);
    }

    /**
     * this method is used to get the mails based on the start and end date.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<Integer, Mail> getMailsByStartEndTime(LocalDate startDate, LocalDate endDate) {
        return kpsModel.getMailsByStartAndEndTime(startDate, endDate);
    }

    /**
     * this method is used to get the average delivery time give a valid route.
     *
     * @param origin
     * @param destination
     * @param priority
     * @return
     */
    public double getAverageDeliveryTime(String origin, String destination, Priority priority) {
        return kpsModel.calculateAverageDeliveryTime(origin, destination, priority);
    }

    /**
     * this method will get all the events
     *
     * @return
     */
    public Map<Integer, Event> getAllEvent() {
        return kpsModel.getAllEvens();
    }

    /**
     * this method will get all the mail given a mail id.
     *
     * @param id
     * @return
     */
    public Mail getMail(int id) {
        return kpsModel.getMailById(id);
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        new KPSMain();

        // prepare some data so we can have something to play with
        new DataPopulater().populateSomethingForMeWillYa();

        javafx.application.Application.launch(GUI.class);
    }
}
