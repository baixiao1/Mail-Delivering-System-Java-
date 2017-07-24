package model.database;

import model.event.*;
import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represent a XML writer/reader.
 *
 * @author Hector
 * @version 2017/6/5
 */
public class XMLDriver {

    /**
     * The system-independent path of where this programme is executed.
     */
    private static final String RUNTIME_PATH = System.getProperty("user.dir").replace("\\", "/");

    /**
     * The folder name of where all xml resource files are stored
     */
    private static final String RESOURCE_XML_FOLDER_PATH = "/xml";

    /**
     * The file name to write "events.xml"
     */
    private static final String EVENT_XML_FILE_NAME = "/events.xml";

    /**
     * The file name to write "locations.xml"
     */
    private static final String LOCATION_XML_FILE_NAME = "/locations.xml";

    /**
     * The file name to write "mails.xml"
     */
    private static final String MAIL_XML_FILE_NAME = "/mails.xml";

    /**
     * The file name to write "routes.xml"
     */
    private static final String ROUTE_XML_FILE_NAME = "/routes.xml";

    /**
     * The file name to write "staffs.xml"
     */
    private static final String STAFF_XML_FILE_NAME = "/staffs.xml";

    /**
     * The file path to write "locations.xml"
     */
    private static final String LOCATION_XML_WRITE_PATH = RUNTIME_PATH + LOCATION_XML_FILE_NAME;

    /**
     * The file path to write "staffs.xml"
     */
    private static final String STAFF_XML_WRITE_PATH = RUNTIME_PATH + STAFF_XML_FILE_NAME;

    /**
     * The file path to write "mails.xml"
     */
    private static final String MAIL_XML_WRITE_PATH = RUNTIME_PATH + MAIL_XML_FILE_NAME;

    /**
     * The file path to routes "routes.xml"
     */
    private static final String ROUTE_XML_WRITE_PATH = RUNTIME_PATH + ROUTE_XML_FILE_NAME;

    /**
     * The file path to routes "events.xml"
     */
    private static final String EVENT_XML_WRITE_PATH = RUNTIME_PATH + EVENT_XML_FILE_NAME;

    /**
     * A uniform format for writing all XML files
     */
    private static OutputFormat format = OutputFormat.createPrettyPrint();

    /**
     * The XML parser
     */
    private static SAXReader reader = new SAXReader();

    static {
        format.setEncoding("UTF-8");
    }

    /**
     * Private Constructor. This class should only be used as a provider of static methods.
     */
    private XMLDriver() {}

    // ===============================================================
    //                            EVENTS
    // ===============================================================

    /**
     * Write the given CustomerPriceUpdateEvent object into "events.xml"
     *
     * @param event
     * @return true if successful, or false if failed.
     */
    public static boolean writeCustomerPriceUpdateEvent(CustomerPriceUpdateEvent event) {
        // read from XML file to get the existing events
        Document document = readDocumentFrom(EVENT_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Events/*[@id='" + event.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of CustomerPriceUpdateEvent
        String elementName = "CustomerPriceUpdateEvent";
        String id = String.valueOf(event.id);
        String staffId = String.valueOf(event.getStaffId());
        String timeStamp = String.valueOf(event.getTimeStamp().toString());
        String routeId = String.valueOf(event.getRouteId());
        String oldPricePerGram = String.valueOf(event.getOldPricePerGram());
        String oldPricePerVolume = String.valueOf(event.getOldPricePerVolume());
        String newPricePerGram = String.valueOf(event.getNewPricePerGram());
        String newPricePerVolume = String.valueOf(event.getNewPricePerVolume());

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new event node
        List<Element> events = document.getRootElement().elements();

        Element newEvent = DocumentHelper.createElement(elementName);
        newEvent.addAttribute("id", id);

        attachChildNode(newEvent, "staffId", staffId);
        attachChildNode(newEvent, "timeStamp", timeStamp);
        attachChildNode(newEvent, "routeId", routeId);
        attachChildNode(newEvent, "oldPricePerGram", oldPricePerGram);
        attachChildNode(newEvent, "oldPricePerVolume", oldPricePerVolume);
        attachChildNode(newEvent, "newPricePerGram", newPricePerGram);
        attachChildNode(newEvent, "newPricePerVolume", newPricePerVolume);

        events.add(newEvent);

        // write the DOM tree back into XML
        return writeDocumentTo(document, EVENT_XML_WRITE_PATH);
    }

    /**
     * Write the given TransportCostUpdateEvent object into "events.xml"
     *
     * @param event
     * @return true if successful, or false if failed.
     */
    public static boolean writeTransportCostUpdateEvent(TransportCostUpdateEvent event) {
        // read from XML file to get the existing events
        Document document = readDocumentFrom(EVENT_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Events/*[@id='" + event.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of TransportCostUpdateEvent
        String elementName = "TransportCostUpdateEvent";
        String id = String.valueOf(event.id);
        String staffId = String.valueOf(event.getStaffId());
        String timeStamp = String.valueOf(event.getTimeStamp().toString());
        String routeId = String.valueOf(event.getRouteId());
        String oldCostPerGram = String.valueOf(event.getOldCostPerGram());
        String oldCostPerVolume = String.valueOf(event.getOldCostPerVolume());
        String newCostPerGram = String.valueOf(event.getNewCostPerGram());
        String newCostPerVolume = String.valueOf(event.getNewCostPerVolume());

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new event node
        List<Element> events = document.getRootElement().elements();

        Element newEvent = DocumentHelper.createElement(elementName);
        newEvent.addAttribute("id", id);

        attachChildNode(newEvent, "staffId", staffId);
        attachChildNode(newEvent, "timeStamp", timeStamp);
        attachChildNode(newEvent, "routeId", routeId);
        attachChildNode(newEvent, "oldCostPerGram", oldCostPerGram);
        attachChildNode(newEvent, "oldCostPerVolume", oldCostPerVolume);
        attachChildNode(newEvent, "newCostPerGram", newCostPerGram);
        attachChildNode(newEvent, "newCostPerVolume", newCostPerVolume);

        events.add(newEvent);

        // write the DOM tree back into XML
        return writeDocumentTo(document, EVENT_XML_WRITE_PATH);
    }

    /**
     * Write the given MailDeliveryEvent object into "events.xml"
     *
     * @param event
     * @return true if successful, or false if failed.
     */
    public static boolean writeMailDeliveryEvent(MailDeliveryEvent event) {
        // read from XML file to get the existing events
        Document document = readDocumentFrom(EVENT_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Events/*[@id='" + event.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of MailDeliveryEvent
        String elementName = "MailDeliveryEvent";
        String id = String.valueOf(event.id);
        String staffId = String.valueOf(event.getStaffId());
        String timeStamp = String.valueOf(event.getTimeStamp().toString());
        String mailId = String.valueOf(event.getMailId());

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new event node
        List<Element> events = document.getRootElement().elements();

        Element newEvent = DocumentHelper.createElement(elementName);
        newEvent.addAttribute("id", id);

        attachChildNode(newEvent, "staffId", staffId);
        attachChildNode(newEvent, "timeStamp", timeStamp);
        attachChildNode(newEvent, "mailId", mailId);

        events.add(newEvent);

        // write the DOM tree back into XML
        return writeDocumentTo(document, EVENT_XML_WRITE_PATH);
    }

    /**
     * Write the given RouteAdditionEvent object into "events.xml"
     *
     * @param event
     * @return true if successful, or false if failed.
     */
    public static boolean writeRouteAdditionEvent(RouteAdditionEvent event) {
        // read from XML file to get the existing events
        Document document = readDocumentFrom(EVENT_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Events/*[@id='" + event.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of RouteAdditionEvent
        String elementName = "RouteAdditionEvent";
        String id = String.valueOf(event.id);
        String staffId = String.valueOf(event.getStaffId());
        String timeStamp = String.valueOf(event.getTimeStamp().toString());
        String routeId = String.valueOf(event.getRouteId());

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new event node
        List<Element> events = document.getRootElement().elements();

        Element newEvent = DocumentHelper.createElement(elementName);
        newEvent.addAttribute("id", id);

        attachChildNode(newEvent, "staffId", staffId);
        attachChildNode(newEvent, "timeStamp", timeStamp);
        attachChildNode(newEvent, "routeId", routeId);

        events.add(newEvent);

        // write the DOM tree back into XML
        return writeDocumentTo(document, EVENT_XML_WRITE_PATH);
    }

    /**
     * Write the given RouteDeactivationEvent object into "events.xml"
     *
     * @param event
     * @return true if successful, or false if failed.
     */
    public static boolean writeRouteDeactivationEvent(RouteDeactivationEvent event) {
        // read from XML file to get the existing events
        Document document = readDocumentFrom(EVENT_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Events/*[@id='" + event.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of RouteAdditionEvent
        String elementName = "RouteDeactivationEvent";
        String id = String.valueOf(event.id);
        String staffId = String.valueOf(event.getStaffId());
        String timeStamp = String.valueOf(event.getTimeStamp().toString());
        String routeId = String.valueOf(event.getRouteId());

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new event node
        List<Element> events = document.getRootElement().elements();

        Element newEvent = DocumentHelper.createElement(elementName);
        newEvent.addAttribute("id", id);

        attachChildNode(newEvent, "staffId", staffId);
        attachChildNode(newEvent, "timeStamp", timeStamp);
        attachChildNode(newEvent, "routeId", routeId);

        events.add(newEvent);

        // write the DOM tree back into XML
        return writeDocumentTo(document, EVENT_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded events
     *
     * @return a Map of events where the key is event id, and the value is the Event object
     */
    public static Map<Integer, Event> readEvents() {
        Map<Integer, Event> events = new HashMap<>();

        // read from XML file to get the existing locations
        Document document = readDocumentFrom(EVENT_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // get all event nodes from XML and construct them into all kinds of event objects
        List<Node> eventNodes = document.selectNodes("/Events/*");
        eventNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            int staffId = Integer.parseInt(node.valueOf("./staffId"));
            LocalDateTime timeStamp = LocalDateTime.parse(node.valueOf("./timeStamp"));

            String eventClassName = node.getName();

            switch (eventClassName) {
                case "CustomerPriceUpdateEvent":
                    int routeId_cpu = Integer.parseInt(node.valueOf("./routeId"));
                    double oldPricePerGram = Float.parseFloat(node.valueOf("./oldPricePerGram"));
                    double oldPricePerVolume = Float.parseFloat(node.valueOf("./oldPricePerVolume"));
                    double newPricePerGram = Float.parseFloat(node.valueOf("./newPricePerGram"));
                    double newPricePerVolume = Float.parseFloat(node.valueOf("./newPricePerVolume"));

                    events.put(id, new CustomerPriceUpdateEvent(id, staffId, timeStamp, routeId_cpu, oldPricePerGram,
                            oldPricePerVolume, newPricePerGram, newPricePerVolume));
                    break;

                case "TransportCostUpdateEvent":
                    int routeId_tcu = Integer.parseInt(node.valueOf("./routeId"));
                    double oldCostPerGram = Float.parseFloat(node.valueOf("./oldCostPerGram"));
                    double oldCostPerVolume = Float.parseFloat(node.valueOf("./oldCostPerVolume"));
                    double newCostPerGram = Float.parseFloat(node.valueOf("./newCostPerGram"));
                    double newCostPerVolume = Float.parseFloat(node.valueOf("./newCostPerVolume"));

                    events.put(id, new CustomerPriceUpdateEvent(id, staffId, timeStamp, routeId_tcu, oldCostPerGram,
                            oldCostPerVolume, newCostPerGram, newCostPerVolume));
                    break;

                case "MailDeliveryEvent":
                    int mailId = Integer.parseInt(node.valueOf("./mailId"));
                    events.put(id, new MailDeliveryEvent(id, staffId, timeStamp, mailId));
                    break;

                case "RouteAdditionEvent":
                    int routeId_ra = Integer.parseInt(node.valueOf("./routeId"));
                    events.put(id, new RouteAdditionEvent(id, staffId, timeStamp, routeId_ra));
                    break;

                case "RouteDeactivationEvent":
                    int routeId_rd = Integer.parseInt(node.valueOf("./routeId"));
                    events.put(id, new RouteDeactivationEvent(id, staffId, timeStamp, routeId_rd));
                    break;

                default:
            }
        });

        return events;
    }

    /**
     * Given a event ID, read the XML file, and find the Event with the given id.
     *
     * @param eventId
     * @return the Event object with the specified ID, or null if cannot find it.
     */
    public static Event readEventById(int eventId) {
        Map<Integer, Event> events = readEvents();

        if (events == null) {
            return null;
        }

        return events.get(eventId);
    }

    /**
     * @return the max ID of events recorded in XML file.
     */
    public static int getMaxEventId() {
        Document document = readDocumentFrom(EVENT_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    // ===============================================================
    //                LOCATIONS
    // ===============================================================

    /**
     * Write the given Location object into "locations.xml"
     *
     * @param location
     * @return true if successful, or false if failed.
     */
    public static boolean writeLocation(Location location) {
        // read from XML file to get the existing locations
        Document document = readDocumentFrom(LOCATION_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Locations/*[@id='" + location.id + "']");
        if (existingNode != null) {
            return false;
        }

        // metadata of location
        String id = String.valueOf(location.id);
        String elementName = location.getClass().getSimpleName();
        String cityName = location.getLocationName();

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new location node
        List<Element> locations = document.getRootElement().elements();

        Element newLocation = DocumentHelper.createElement(elementName);
        newLocation.addAttribute("id", id);
        attachChildNode(newLocation, "cityName", cityName);

        locations.add(newLocation);

        // write the DOM tree back into XML
        return writeDocumentTo(document, LOCATION_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded locations
     *
     * @return a Map of locations where the key is location id, and the value is the Location object
     */
    public static Map<Integer, Location> readLocations() {
        Map<Integer, Location> locations = new HashMap<>();

        // read from XML file to get the existing locations
        Document document = readDocumentFrom(LOCATION_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // get all NZLocation nodes from XML and construct them into NZLocations
        List<Node> nzNodes = document.selectNodes("/Locations/NZLocation");
        nzNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            String cityName = node.valueOf("./cityName");
            NZCity nzCity = NZCity.createFromString(cityName);
            NZLocation nzLocation = new NZLocation(id, nzCity);

            locations.put(id, nzLocation);
        });

        // get all InternationalLocation nodes from XML and construct them into InternationalLocation
        List<Node> internationalNodes = document.selectNodes("/Locations/InternationalLocation");
        internationalNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            String cityName = node.valueOf("./cityName");
            InternationalLocation internationalLocation = new InternationalLocation(id, cityName);

            locations.put(id, internationalLocation);
        });

        return locations;
    }

    /**
     * Given a location ID, read the XML file, and find the Location with the given id.
     *
     * @param locationId
     * @return the Location object with the specified ID, or null if cannot find it.
     */
    public static Location readLocationById(int locationId) {
        Map<Integer, Location> locations = readLocations();

        if (locations == null) {
            return null;
        }

        return locations.get(locationId);
    }

    /**
     * @return the max ID of locations recorded in XML file.
     */
    public static int getMaxLocationId() {
        Document document = readDocumentFrom(LOCATION_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    // ===============================================================
    //                MAILS
    // ===============================================================

    /**
     * Write the given Mail object into "mails.xml"
     *
     * @param mail
     * @return true if successful, or false if failed.
     */
    public static boolean writeMail(Mail mail) {
        // read from XML file to get the existing locations
        Document document = readDocumentFrom(MAIL_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Mails/*[@id='" + mail.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of Mail
        String elementName = "Mail";
        String id = String.valueOf(mail.id);
        String originId = String.valueOf(mail.getOrigin().id);
        String destinationId = String.valueOf(mail.getDestination().id);
        String weight = String.valueOf(mail.getWeight());
        String volume = String.valueOf(mail.getVolume());
        String priority = mail.getPriority().toString();
        String deliveryDate = mail.getDeliveryDate().toString();
        String routes = mail.getRoutes().stream().map(route -> String.valueOf(route.id)).collect(Collectors.joining(","));

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new staff node
        List<Element> mails = document.getRootElement().elements();

        Element newMail = DocumentHelper.createElement(elementName);
        newMail.addAttribute("id", id);

        attachChildNode(newMail, "originId", originId);
        attachChildNode(newMail, "destinationId", destinationId);
        attachChildNode(newMail, "weight", weight);
        attachChildNode(newMail, "volume", volume);
        attachChildNode(newMail, "priority", priority);
        attachChildNode(newMail, "deliveryDate", deliveryDate);
        attachChildNode(newMail, "routes", routes);

        mails.add(newMail);

        // write the DOM tree back into XML
        return writeDocumentTo(document, MAIL_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded mails
     *
     * @return a Map of mails where the key is mail id, and the value is the Mail object
     */
    public static Map<Integer, Mail> readMails() {
        Map<Integer, Route> routesMap = readRoutes();
        Map<Integer, Mail> mails = new HashMap<>();

        // read from XML file to get the existing mails
        Document document = readDocumentFrom(MAIL_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // read all locations
        Map<Integer, Location> locations = readLocations();

        // get all staff nodes from XML and construct them into Staff objects
        List<Node> mailNodes = document.selectNodes("/Mails/Mail");
        mailNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            NZLocation origin = (NZLocation) locations.get(Integer.parseInt(node.valueOf("./originId")));
            Location destination = locations.get(Integer.parseInt(node.valueOf("./destinationId")));
            double weight = Float.parseFloat(node.valueOf("./weight"));
            double volume = Float.parseFloat(node.valueOf("./volume"));
            Priority priority = Priority.createPriorityFrom(node.valueOf("./priority"));
            LocalDate deliveryDate = LocalDate.parse(node.valueOf("./deliveryDate"));

            Mail mail = new Mail(id, origin, destination, weight, volume, priority, deliveryDate);

            List<Route> routes = Arrays.stream(node.valueOf("./routes").split(","))
                    .map(idString -> routesMap.get(Integer.parseInt(idString)))
                    .collect(Collectors.toList());

            mail.setRoutes(routes);

            mails.put(id, mail);
        });

        return mails;
    }

    /**
     * Given a mail ID, read the XML file, and find the Mail with the given id.
     *
     * @param mailId
     * @return the Mail object with the specified ID, or null if cannot find it.
     */
    public static Mail readMailById(int mailId) {
        Map<Integer, Mail> mails = readMails();

        if (mails == null) {
            return null;
        }

        return mails.get(mailId);
    }

    /**
     * @return the max ID of mails recorded in XML file.
     */
    public static int getMaxMailId() {
        Document document = readDocumentFrom(MAIL_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    // ===============================================================
    //                ROUTES
    // ===============================================================

    /**
     * Write the given Route object into "routes.xml"
     *
     * @param route
     * @return true if successful, or false if failed.
     */
    public static boolean writeRoute(Route route) {
        // read from XML file to get the existing locations
        Document document = readDocumentFrom(ROUTE_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Routes/*[@id='" + route.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of route
        String elementName = "Route";
        String id = String.valueOf(route.id);
        String routeType = route.routeType.toString();
        String startId = String.valueOf(route.getStartLocation().id);
        String endId = String.valueOf(route.getEndLocation().id);
        String duration = String.valueOf(route.getDuration());
        String transportFirm = route.getTransportFirm();
        String pricePerGram = String.valueOf(route.getPricePerGram());
        String pricePerVolume = String.valueOf(route.getCostPerVolume());
        String costPerGram = String.valueOf(route.getCostPerGram());
        String costPerVolume = String.valueOf(route.getCostPerVolume());
        String isActive = String.valueOf(route.isActive());

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new route node
        List<Element> routes = document.getRootElement().elements();

        Element newRoute = DocumentHelper.createElement(elementName);
        newRoute.addAttribute("id", id);

        attachChildNode(newRoute, "routeType", routeType);
        attachChildNode(newRoute, "startId", startId);
        attachChildNode(newRoute, "endId", endId);
        attachChildNode(newRoute, "duration", duration);
        attachChildNode(newRoute, "transportFirm", transportFirm);
        attachChildNode(newRoute, "pricePerGram", pricePerGram);
        attachChildNode(newRoute, "pricePerVolume", pricePerVolume);
        attachChildNode(newRoute, "costPerGram", costPerGram);
        attachChildNode(newRoute, "costPerVolume", costPerVolume);
        attachChildNode(newRoute, "isActive", isActive);

        routes.add(newRoute);

        // write the DOM tree back into XML
        return writeDocumentTo(document, ROUTE_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded routes
     *
     * @return a Map of routes where the key is route id, and the value is the Route object
     */
    public static Map<Integer, Route> readRoutes() {
        Map<Integer, Route> routes = new HashMap<>();

        // read from XML file to get the existing staffs
        Document document = readDocumentFrom(ROUTE_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // read all locations
        Map<Integer, Location> locations = readLocations();

        // get all route nodes from XML and construct them into Route objects
        List<Node> routeNodes = document.selectNodes("/Routes/Route");
        routeNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            RouteType routeType = RouteType.createFromString(node.valueOf("./routeType"));
            Location start = locations.get(Integer.parseInt(node.valueOf("./startId")));
            Location end = locations.get(Integer.parseInt(node.valueOf("./endId")));
            double duration = Float.parseFloat(node.valueOf("./duration"));
            String transportFirm = node.valueOf("./transportFirm");
            double pricePerGram = Float.parseFloat(node.valueOf("./pricePerGram"));
            double pricePerVolume = Float.parseFloat(node.valueOf("./pricePerVolume"));
            double costPerGram = Float.parseFloat(node.valueOf("./costPerGram"));
            double costPerVolume = Float.parseFloat(node.valueOf("./costPerVolume"));
            boolean isActive = Boolean.parseBoolean(node.valueOf("./isActive"));

            Route route = new Route(id, start, end, routeType, duration, transportFirm, pricePerGram, pricePerVolume, costPerGram, costPerVolume);
            route.setIsActive(isActive);

            routes.put(id, route);
        });

        return routes;
    }

    /**
     * Given a route ID, read the XML file, and find the Route with the given id.
     *
     * @param routeId
     * @return the Route object with the specified ID, or null if cannot find it.
     */
    public static Route readRouteById(int routeId) {
        Map<Integer, Route> routes = readRoutes();

        if (routes == null) {
            return null;
        }

        return routes.get(routeId);
    }

    /**
     * @return the max ID of routes recorded in XML file.
     */
    public static int getMaxRouteId() {
        Document document = readDocumentFrom(ROUTE_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    /**
     * Update the route
     *
     * @param routeId the id of the route that needs to be updated
     * @param route   the route object with new properties.
     */
    public static boolean updateRoute(int routeId, Route route) {
        // read from XML file to get the existing locations
        Document document = readDocumentFrom(ROUTE_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        Node existingNode = document.selectSingleNode("/Routes/*[@id='" + routeId + "']");

        // if we can't find this id in XML file, then return false.
        if (existingNode == null) {
            return false;
        }

        // remove the existing route with the given id
        existingNode.detach();

        //  metadata of route
        String elementName = "Route";
        String routeType = route.routeType.toString();
        String startId = String.valueOf(route.getStartLocation().id);
        String endId = String.valueOf(route.getEndLocation().id);
        String duration = String.valueOf(route.getDuration());
        String transportFirm = route.getTransportFirm();
        String pricePerGram = String.valueOf(route.getPricePerGram());
        String pricePerVolume = String.valueOf(route.getPricePerVolume());
        String costPerGram = String.valueOf(route.getCostPerGram());
        String costPerVolume = String.valueOf(route.getCostPerVolume());
        String isActive = String.valueOf(route.isActive());

        // create the new route node
        Element newRoute = DocumentHelper.createElement(elementName);
        newRoute.addAttribute("id", String.valueOf(routeId));

        attachChildNode(newRoute, "routeType", routeType);
        attachChildNode(newRoute, "startId", startId);
        attachChildNode(newRoute, "endId", endId);
        attachChildNode(newRoute, "duration", duration);
        attachChildNode(newRoute, "transportFirm", transportFirm);
        attachChildNode(newRoute, "pricePerGram", pricePerGram);
        attachChildNode(newRoute, "pricePerVolume", pricePerVolume);
        attachChildNode(newRoute, "costPerGram", costPerGram);
        attachChildNode(newRoute, "costPerVolume", costPerVolume);
        attachChildNode(newRoute, "isActive", isActive);

        // add to root
        List<Element> routes = document.getRootElement().elements();
        routes.add(newRoute);

        // write the DOM tree back into XML
        return writeDocumentTo(document, ROUTE_XML_WRITE_PATH);
    }

    // ===============================================================
    //                STAFFS
    // ===============================================================

    /**
     * Write the given Staff object into "staffs.xml"
     *
     * @param staff
     * @return true if successful, or false if failed.
     */
    public static boolean writeStaff(Staff staff) {
        // read from XML file to get the existing staff
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file has this id already, do not add it.
        Node existingNode = document.selectSingleNode("/Staffs/*[@id='" + staff.id + "']");
        if (existingNode != null) {
            return false;
        }

        //  metadata of staff
        String elementName = "Staff";
        String id = String.valueOf(staff.id);
        String userName = staff.getUserName();
        String password = staff.getPassword();
        String firstName = staff.getFirstName();
        String lastName = staff.getLastName();
        String email = staff.getEmail();
        String phoneNumber = staff.getPhoneNumber();
        String isManager = String.valueOf(staff.isManager());

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new staff node
        List<Element> staffs = document.getRootElement().elements();

        Element newStaff = DocumentHelper.createElement(elementName);
        newStaff.addAttribute("id", id);

        attachChildNode(newStaff, "userName", userName);
        attachChildNode(newStaff, "password", password);
        attachChildNode(newStaff, "firstName", firstName);
        attachChildNode(newStaff, "lastName", lastName);
        attachChildNode(newStaff, "email", email);
        attachChildNode(newStaff, "phoneNumber", phoneNumber);
        attachChildNode(newStaff, "isManager", isManager);

        staffs.add(newStaff);

        // write the DOM tree back into XML
        return writeDocumentTo(document, STAFF_XML_WRITE_PATH);
    }

    /**
     * Update the staff with the given id, and write back to "staffs.xml"
     *
     * @param id
     * @param staff
     * @return true if successful, or false if failed.
     */
    public static boolean updateStaff(int id, Staff staff) {
        // read from XML file to get the existing staff
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // if XML file does not has this id, return false.
        Node existingNode = document.selectSingleNode("/Staffs/*[@id='" + id + "']");
        if (existingNode == null) {
            return false;
        }

        // remove the existing node
        existingNode.detach();

        //  metadata of staff
        String elementName = "Staff";
        String userName = staff.getUserName();
        String password = staff.getPassword();
        String firstName = staff.getFirstName();
        String lastName = staff.getLastName();
        String email = staff.getEmail();
        String phoneNumber = staff.getPhoneNumber();
        String isManager = String.valueOf(staff.isManager());

        // create the new staff node
        List<Element> staffs = document.getRootElement().elements();

        Element newStaff = DocumentHelper.createElement(elementName);
        newStaff.addAttribute("id", String.valueOf(id));

        attachChildNode(newStaff, "userName", userName);
        attachChildNode(newStaff, "password", password);
        attachChildNode(newStaff, "firstName", firstName);
        attachChildNode(newStaff, "lastName", lastName);
        attachChildNode(newStaff, "email", email);
        attachChildNode(newStaff, "phoneNumber", phoneNumber);
        attachChildNode(newStaff, "isManager", isManager);

        staffs.add(newStaff);

        // write the DOM tree back into XML
        return writeDocumentTo(document, STAFF_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded staffs
     *
     * @return a Map of staffs where the key is staff id, and the value is the Staff object
     */
    public static Map<Integer, Staff> readStaffs() {
        Map<Integer, Staff> staffs = new HashMap<>();

        // read from XML file to get the existing staffs
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // get all staff nodes from XML and construct them into Staff objects
        List<Node> staffNodes = document.selectNodes("/Staffs/Staff");
        staffNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            String userName = node.valueOf("./userName");
            String password = node.valueOf("./password");
            String firstName = node.valueOf("./firstName");
            String lastName = node.valueOf("./lastName");
            String email = node.valueOf("./email");
            String phoneNumber = node.valueOf("./phoneNumber");
            Boolean isManager = Boolean.valueOf(node.valueOf("./isManager"));

            Staff staff = new Staff(id, userName, password, isManager, firstName, lastName, email, phoneNumber);

            staffs.put(id, staff);
        });

        return staffs;
    }

    /**
     * Given a staff ID, read the XML file, and find the Staff with the given id.
     *
     * @param staffId
     * @return the Staff object with the specified ID, or null if cannot find it.
     */
    public static Staff readStaffById(int staffId) {
        Map<Integer, Staff> staffs = readStaffs();

        if (staffs == null) {
            return null;
        }

        return staffs.get(staffId);
    }

    /**
     * @return the max ID of staffs recorded in XML file.
     */
    public static int getMaxStaffId() {
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    /**
     * Given a staff ID, delete the Staff with the given id from XML file.
     *
     * @param staffId
     * @return true if successful, or false if failed.
     */
    public static boolean deleteStaffById(int staffId) {
        // read from XML file to get the existing locations
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        Node existingNode = document.selectSingleNode("/Staffs/*[@id='" + staffId + "']");

        // if we can't find this id in XML file, then return true. This is the idempotentness of Delete.
        if (existingNode == null) {
            return true;
        }

        // remove the existing route with the given id
        existingNode.detach();

        // write the DOM tree back into XML
        return writeDocumentTo(document, STAFF_XML_WRITE_PATH);
    }

    // ===============================================================
    //                PRIVATE METHODS
    // ===============================================================

    /**
     * Read the XML file with the given file name
     *
     * @param fileName
     * @return the parsed Document object
     */
    private static Document readDocumentFrom(String fileName) {
        String filePath;

        try {
            // if the given file exist in programme path, read it; otherwise read from resource.
            if (Files.exists(Paths.get(RUNTIME_PATH + fileName))) {
                filePath = RUNTIME_PATH + fileName;
                return reader.read(new File(filePath));
            } else {
                filePath = RESOURCE_XML_FOLDER_PATH + fileName;
                return reader.read(DataPopulater.class.getResource(filePath));
            }
        } catch (DocumentException e) {
            logError(e);
            return null;
        }
    }

    /**
     * Write the given Document object into file, using the given file path.
     *
     * @param document
     * @param path
     * @return true if successful, or false if failed.
     */
    private static boolean writeDocumentTo(Document document, String path) {
        try {
            XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(path)), format);
            writer.write(document);
            writer.close();

            return true;
        } catch (IOException e) {
            logError(e);
            return false;
        }
    }

    /**
     * Attach a child node whose name is <i>childNodeName</i> and inner text is <i>childNodeText</i>, to the
     * <i>parentNode</i>.
     *
     * @param parentNode
     * @param childNodeName
     * @param childNodeText
     */
    private static void attachChildNode(Element parentNode, String childNodeName, String childNodeText) {
        Element childNode = DocumentHelper.createElement(childNodeName);

        if (childNodeText != null) {
            childNode.setText(childNodeText);
        }

        parentNode.add(childNode);
    }

    /**
     * A helper method to log error into console.
     *
     * @param e
     */
    private static void logError(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }
}
