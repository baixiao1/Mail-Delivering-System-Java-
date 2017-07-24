package model.database;

import model.event.*;
import model.location.InternationalLocation;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class is used to populate the initial data, so the programme won't have to be demonstrated with blank data. this
 * class is used to load the data need for the program to run.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class DataPopulater {

    /**
     * Constructor
     */
    public DataPopulater() {
    }

    /**
     * Populate something for me so I can play with it will ya?
     */
    public void populateSomethingForMeWillYa() {
        populateStaffs();
        populateLocationsAndRoutesAndMails();
        populateEvents();
    }

    /**
     * Writes some fake data into staffs.xml
     */
    private void populateStaffs() {
        Staff staff_1 = new Staff(1, "John", "test123", false, "John",
                "Smith", "jsmith@email.com", "123 456 7890");
        Staff staff_2 = new Staff(2, "Bob", "test123", true, "Bob",
                "Jones", "bjones@email.com", "123 456 7890");
        Staff staff_3 = new Staff(3, "Steve", "test123", false, "Steve",
                "Jobs", "sjobs@email.com", "123 456 7890");
        Staff staff_4 = new Staff(4, "123", "123", true, "John",
                "Doe", "jd@email.com", "123 456 7890");

        XMLDriver.writeStaff(staff_1);
        XMLDriver.writeStaff(staff_2);
        XMLDriver.writeStaff(staff_3);
        XMLDriver.writeStaff(staff_4);
    }

    /**
     * Writes some fake data into locations.xml, routes.xml, and mails.xml
     */
    private void populateLocationsAndRoutesAndMails() {

        // ================= populate locations ============================

        NZLocation auckland = new NZLocation(1, NZCity.Auckland);
        NZLocation hamilton = new NZLocation(2, NZCity.Hamilton);
        NZLocation rotorua = new NZLocation(3, NZCity.Rotorua);
        NZLocation parmerstonNorth = new NZLocation(4, NZCity.Palmerston_North);
        NZLocation wellington = new NZLocation(5, NZCity.Wellington);
        NZLocation christchurch = new NZLocation(6, NZCity.Christchurch);
        NZLocation dunedin = new NZLocation(7, NZCity.Dunedin);

        InternationalLocation sydney = new InternationalLocation(8, "Sydney");
        InternationalLocation singapore = new InternationalLocation(9, "Singapore");
        InternationalLocation hongkong = new InternationalLocation(10, "HongKong");
        InternationalLocation guangzhou = new InternationalLocation(11, "GuangZhou");
        InternationalLocation moscow = new InternationalLocation(12, "Moscow");

        XMLDriver.writeLocation(auckland);
        XMLDriver.writeLocation(hamilton);
        XMLDriver.writeLocation(rotorua);
        XMLDriver.writeLocation(parmerstonNorth);
        XMLDriver.writeLocation(wellington);
        XMLDriver.writeLocation(christchurch);
        XMLDriver.writeLocation(dunedin);
        XMLDriver.writeLocation(sydney);
        XMLDriver.writeLocation(singapore);
        XMLDriver.writeLocation(hongkong);
        XMLDriver.writeLocation(guangzhou);
        XMLDriver.writeLocation(moscow);

        // ================= populate routes ============================

        Route route_1 = new Route(1, dunedin, christchurch, RouteType.Land, 4.0f, "RoyalPost", 1.2f, 0.7f, 1.0f, 0.5f);
        Route route_2 = new Route(2, dunedin, christchurch, RouteType.Land, 3.2f, "NZFast", 1.4f, 0.9f, 1.45f, 0.95f);
        Route route_3 = new Route(3, christchurch, wellington, RouteType.Air, 1.2f, "RoyalPost", 1.9f, 1.3f, 1.6f, 0.9f);
        Route route_4 = new Route(4, christchurch, wellington, RouteType.Air, 1.1f, "NZFast", 1.8f, 1.3f, 1.8f, 1.35f);
        Route route_5 = new Route(5, dunedin, auckland, RouteType.Air, 4.5f, "NZFast", 3.9f, 4.0f, 3.2f, 3.5f);
        Route route_6 = new Route(6, christchurch, auckland, RouteType.Land, 4.9f, "RoyalPost", 2.9f, 3.2f, 2.5f, 3.0f);
        Route route_7 = new Route(7, auckland, sydney, RouteType.Air, 3.0f, "FedEx", 6.6f, 6.2f, 5.5f, 5.0f);
        Route route_8 = new Route(8, auckland, sydney, RouteType.Sea, 7.5f, "PacificaMails", 4.0f, 3.5f, 3.5f, 3.0f);
        Route route_9 = new Route(9, auckland, singapore, RouteType.Sea, 10.5f, "PacificaMails", 4.8f, 4.6f, 4.5f, 4.2f);
        Route route_10 = new Route(10, sydney, hongkong, RouteType.Air, 4.8f, "FedEx", 8.8f, 7.6f, 6.5f, 6.5f);
        Route route_11 = new Route(11, singapore, guangzhou, RouteType.Sea, 4, "PacificaMails", 3.2f, 3.7f, 3.0f, 3.2f);
        Route route_12 = new Route(12, hongkong, guangzhou, RouteType.Sea, 0.5f, "FedEx", 1.2f, 0.7f, 1.0f, 0.5f);
        Route route_13 = new Route(13, guangzhou, moscow, RouteType.Land, 14.0f, "FedEx", 9.6f, 9.9f, 8.5f, 8.5f);
        Route route_14 = new Route(14, guangzhou, moscow, RouteType.Air, 5.5f, "FedEx", 13.5f, 14.5f, 12.0f, 12.5f);

        XMLDriver.writeRoute(route_1);
        XMLDriver.writeRoute(route_2);
        XMLDriver.writeRoute(route_3);
        XMLDriver.writeRoute(route_4);
        XMLDriver.writeRoute(route_5);
        XMLDriver.writeRoute(route_6);
        XMLDriver.writeRoute(route_7);
        XMLDriver.writeRoute(route_8);
        XMLDriver.writeRoute(route_9);
        XMLDriver.writeRoute(route_10);
        XMLDriver.writeRoute(route_11);
        XMLDriver.writeRoute(route_12);
        XMLDriver.writeRoute(route_13);
        XMLDriver.writeRoute(route_14);

        // ================= populate mails ============================

        Mail mail_1 = new Mail(1, dunedin, auckland, 2500, 3000, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Mail mail_2 = new Mail(2, dunedin, moscow, 6200, 3000, Priority.International_Standard, LocalDate.of(2016, 4, 17));
        Mail mail_3 = new Mail(3, dunedin, hongkong, 500, 100, Priority.International_Air, LocalDate.of(2017, 1, 1));
        Mail mail_4 = new Mail(4, auckland, sydney, 500, 300, Priority.International_Air, LocalDate.of(2017, 2, 2));
        Mail mail_5 = new Mail(5, christchurch, auckland, 2800, 2800, Priority.Domestic_Standard, LocalDate.of(2017, 3, 3));
        Mail mail_6 = new Mail(6, dunedin, christchurch, 100, 200, Priority.Domestic_Standard, LocalDate.of(2017, 6, 6));

        mail_1.setRoutes(Collections.singletonList(route_5));
        mail_2.setRoutes(Arrays.asList(route_5, route_9, route_11, route_13));
        mail_3.setRoutes(Arrays.asList(route_5, route_7, route_10));
        mail_4.setRoutes(Collections.singletonList(route_7));
        mail_5.setRoutes(Collections.singletonList(route_6));
        mail_6.setRoutes(Collections.singletonList(route_2));

        XMLDriver.writeMail(mail_1);
        XMLDriver.writeMail(mail_2);
        XMLDriver.writeMail(mail_3);
        XMLDriver.writeMail(mail_4);
        XMLDriver.writeMail(mail_5);
        XMLDriver.writeMail(mail_6);
    }

    /**
     * Writes some fake data into events.xml
     */
    private void populateEvents() {
        LocalDateTime time_1 = LocalDateTime.of(2016, 4, 03, 10, 14, 9);
        LocalDateTime time_2 = LocalDateTime.of(2016, 4, 17, 10, 14, 9);
        LocalDateTime time_3 = LocalDateTime.of(2017, 1, 1, 10, 14, 9);
        LocalDateTime time_4 = LocalDateTime.of(2017, 2, 2, 10, 14, 9);
        LocalDateTime time_5 = LocalDateTime.of(2017, 3, 3, 10, 14, 9);

        CustomerPriceUpdateEvent cpuEvent = new CustomerPriceUpdateEvent(1, 1, time_1, 1, 1.0f, 0.5f, 1.2f, 0.7f);
        TransportCostUpdateEvent tcuEvent = new TransportCostUpdateEvent(2, 2, time_2, 2, 1.2f, 0.8f, 1.1f, 0.6f);
        MailDeliveryEvent mdEvent = new MailDeliveryEvent(3, 1, time_3, 1);
        RouteAdditionEvent raEvent = new RouteAdditionEvent(4, 1, time_4, 14);
        RouteDeactivationEvent rdEvent = new RouteDeactivationEvent(5, 1, time_5, 3);

        XMLDriver.writeCustomerPriceUpdateEvent(cpuEvent);
        XMLDriver.writeTransportCostUpdateEvent(tcuEvent);
        XMLDriver.writeMailDeliveryEvent(mdEvent);
        XMLDriver.writeRouteAdditionEvent(raEvent);
        XMLDriver.writeRouteDeactivationEvent(rdEvent);
    }
}
