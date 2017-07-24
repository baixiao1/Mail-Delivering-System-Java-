import model.KPSModel;
import model.event.Event;
import model.location.Location;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for APIs of KPSModel.
 * <p>
 * Note that these tests aren't perfect. Some of the test cases rely on the existing data in the system, which means
 * they are only testing that the system runs correctly with the given dataset. This is really bad and practically
 * useless.
 *
 * @author Hector
 * @version 2017/6/17
 */
public class KPSModelTest {

    // ============================================================
    //                  Tests for Events
    // ============================================================

    /**
     * Test that we can retrieve correct events within the given time range
     */
    @Test
    public void event_RetrievingEventsByTimeRange() {
        LocalDate wantedStartDate = LocalDate.of(2016, 1, 1);
        LocalDate wantedEndDate = LocalDate.of(2016, 12, 31);
        Map<Integer, Event> events = mockKPSModel().getEventsByStartAndEndTime(wantedStartDate, wantedEndDate);

        events.values().forEach(event -> {
            LocalDate eventTime = event.getTimeStamp().toLocalDate();

            assertFalse(eventTime.isBefore(wantedStartDate), "event time is before wanted start date! event id: " + event.id);
            assertFalse(eventTime.isAfter(wantedEndDate), "event time is after wanted end date! event id: " + event.id);
        });
    }

    // ============================================================
    //                  Tests for Locations
    // ============================================================

    /**
     * Test that we can retrieve correct result of available origins & destinations
     */
    @Test
    public void location_RetrievingAvailableOriginsAndDestinations() {
        KPSModel kpsModel = mockKPSModel();

        Set<NZLocation> origins = kpsModel.getAvailableOrigins();
        Set<Location> destinations = kpsModel.getAvailableDestinations();
        Map<Integer, Route> routes = kpsModel.getAllRoutes();

        routes.values().forEach(route -> {
            Location start = route.getStartLocation();
            Location end = route.getEndLocation();

            if (start instanceof NZLocation) {
                assertTrue(origins.contains(start), "The start of the route isn't in available origins! route id: " + route.id);
            }

            assertTrue(destinations.contains(end), "The end of the route isn't in available destinations! route id: " + route.id);
        });
    }

    // ============================================================
    //                  Tests for Mails
    // ============================================================

    /**
     * Test that we can process a mail
     */
    @Test
    public void mail_ProcessingMail_success() {
        // we have routes connecting Christchurch and Auckland
        String originString = "Christchurch";
        String destinationString = "Auckland";
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.Domestic_Standard;

        Mail mail = mockKPSModel().processMail(originString, destinationString, weight, volume, priority);
        assertTrue(mail != null, "Houston, we have a problem, we don't know how to get to Auckland from Christchurch");

        List<Route> routes = mail.getRoutes();
        assertTrue(routes != null && !routes.isEmpty(), "The routes associated with the mail is null/empty!");
    }

    /**
     * Test that we shouldn't be able to process a mail, when the given parameters are not valid
     */
    @Test
    public void mail_ProcessingMail_failure() {
        // we have no routes connecting Auckland and Hongkong
        String originString = "Auckland";
        String destinationString = "Moscow";
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.International_Air;

        Mail mail = mockKPSModel().processMail(originString, destinationString, weight, volume, priority);
        assertEquals(null, mail, "We shouldn't be able to process this mail, because the given parameters are not valid!");
    }

    /**
     * Test that we can deliver a mail, and log the event.
     */
    @Test
    public void mail_DeliveringMail() {
        KPSModel kpsModel = mockKPSModel();
        Mail mail = mockMail(kpsModel);
        int oldEventSize = kpsModel.getAllEvens().size();
        assertTrue(kpsModel.deliverMail(mail), "Delivering mail failed!");

        int newEventSize = kpsModel.getAllEvens().size();
        assertEquals(oldEventSize + 1, newEventSize, "Number of events didn't increment!");
    }

    /**
     * Test that we can retrieve correct mails given an origin and a destination.
     */
    @Test
    public void mail_RetrievingMailsByOriginAndDestination() {
        String originString = "dunedin";
        String destinationString = "moscow";
        Map<Integer, Mail> mails = mockKPSModel().getMailsByOriginAndDestination(originString, destinationString);

        mails.values().forEach(mail -> {
            assertTrue(mail.getOrigin().getLocationName().equalsIgnoreCase(originString),
                    "The origin of mail doesn't match the wanted origin, mail id: " + mail.id);
            assertTrue(mail.getDestination().getLocationName().equalsIgnoreCase(destinationString),
                    "The destination of mail doesn't match the wanted destination, mail id: " + mail.id);
        });
    }

    /**
     * Test that we can retrieve correct mails given an start date and an end date.
     */
    @Test
    public void mail_RetrievingMailsWithinGivenTimeRange() {
        LocalDate wantedStartDate = LocalDate.of(2017, 1, 1);
        LocalDate wantedEndDate = LocalDate.of(2017, 12, 31);
        Map<Integer, Mail> mails = mockKPSModel().getMailsByStartAndEndTime(wantedStartDate, wantedEndDate);

        mails.values().forEach(mail -> {
            LocalDate deliveryDate = mail.getDeliveryDate();

            assertFalse(deliveryDate.isBefore(wantedStartDate), "mail delivery date is before wanted start date! mail id: " + mail.id);
            assertFalse(deliveryDate.isAfter(wantedEndDate), "mail delivery date is after wanted end date! mail id: " + mail.id);
        });
    }

    /**
     * Test that we can calculate the revenue, the expenditure, and the profit.
     */
    @Test
    public void mail_CalculatingRevenueExpenditureAndProfit() {
        Map<Integer, Mail> mails = new HashMap<>();
        NZLocation dunedin = new NZLocation(7, NZCity.Dunedin);
        NZLocation auckland = new NZLocation(1, NZCity.Auckland);
        Mail mail_1 = new Mail(1, dunedin, auckland, 100, 100, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Mail mail_2 = new Mail(2, dunedin, auckland, 100, 100, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Route route = new Route(1, dunedin, auckland, RouteType.Land, 4.0f, "RoyalPost", 2, 2, 1, 1);

        mail_1.setRoutes(Collections.singletonList(route));
        mail_2.setRoutes(Collections.singletonList(route));
        mails.put(mail_1.id, mail_1);
        mails.put(mail_2.id, mail_2);

        double expectedRevenue = 800d;
        double expectedExpenditure = 400d;
        double expectedProfit = expectedRevenue - expectedExpenditure;

        assertEquals(expectedRevenue, KPSModel.calculateTotalRevenue(mails), "Revenue doesn't match");
        assertEquals(expectedExpenditure, KPSModel.calculateTotalExpenditure(mails), "Expenditure doesn't match");
        assertEquals(expectedProfit, KPSModel.calculateTotalProfit(mails), "Profit doesn't match");
    }

    /**
     * Test that we can calculate the average delivery time, given an origin, a destination, and a priority.
     */
    @Test
    public void mail_CalculatingAverageDeliveryTime_success() {
        // Too sad we have to do this :( because we haven't got time to unit-test the internal models. Have to clear the data destructively.
        KPSModel kpsModel = mockKPSModel();
        Map<Integer, Mail> mailCache = kpsModel.getAllMails();
        mailCache.clear();

        NZLocation dunedin = new NZLocation(7, NZCity.Dunedin);
        NZLocation auckland = new NZLocation(1, NZCity.Auckland);
        Mail mail_1 = new Mail(1, dunedin, auckland, 100, 100, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Mail mail_2 = new Mail(2, dunedin, auckland, 100, 100, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Route route = new Route(1, dunedin, auckland, RouteType.Land, 4.0d, "RoyalPost", 2, 2, 1, 1);

        mail_1.setRoutes(Collections.singletonList(route));
        mail_2.setRoutes(Collections.singletonList(route));
        mailCache.put(mail_1.id, mail_1);
        mailCache.put(mail_2.id, mail_2);

        double expectedAverageDeliveryTime = 4d;

        assertEquals(expectedAverageDeliveryTime, kpsModel.calculateAverageDeliveryTime("Dunedin", "Auckland", Priority.Domestic_Standard),
                "Average delivery time doesn't match");
    }

    /**
     * Test that the system will return -1 when there is no existing mail for calculating average delivery time.
     */
    @Test
    public void mail_CalculatingAverageDeliveryTime_failure() {
        // Too sad we have to do this :( because we haven't got time to unit-test the internal models. Have to clear the data destructively.
        KPSModel kpsModel = mockKPSModel();
        Map<Integer, Mail> mailCache = kpsModel.getAllMails();
        mailCache.clear();

        NZLocation dunedin = new NZLocation(7, NZCity.Dunedin);
        NZLocation auckland = new NZLocation(1, NZCity.Auckland);
        Mail mail_1 = new Mail(1, dunedin, auckland, 100, 100, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Route route = new Route(1, dunedin, auckland, RouteType.Land, 4d, "RoyalPost", 2, 2, 1, 1);

        mail_1.setRoutes(Collections.singletonList(route));
        mailCache.put(mail_1.id, mail_1);

        double expectedAverageDeliveryTime = -1;

        assertEquals(expectedAverageDeliveryTime, kpsModel.calculateAverageDeliveryTime("christchurch", "Auckland", Priority.Domestic_Standard),
                "Average delivery time doesn't match");
    }

    // ============================================================
    //                  Tests for Routes
    // ============================================================

    /**
     * Test that we can update the customer price and transport cost
     */
    @Test
    public void route_UpdatingRoutePriceAndCost() {
        KPSModel kpsModel = mockKPSModel();
        Route route = kpsModel.getRouteById(1);
        double newPricePerGram = 200;
        double newPricePerVolume = 200;
        double newCostPerGram = 200;
        double newCostPerVolume = 200;
        int oldNumberEvents = kpsModel.getAllEvens().size();

        // update prices

        assertTrue(kpsModel.updateCustomerPrice(1, newPricePerGram, newPricePerVolume), "The update failed!");
        assertEquals(newPricePerGram, route.getPricePerGram(), "Price per gram didn't get updated!");
        assertEquals(newPricePerVolume, route.getPricePerVolume(), "Price per volume didn't get updated!");

        int newNumberEvents = kpsModel.getAllEvens().size();
        assertEquals(oldNumberEvents + 1, newNumberEvents, "Number of events didn't increment!");

        // update costs

        assertTrue(kpsModel.updateTransportCost(1, newCostPerGram, newCostPerVolume), "The update failed!");
        assertEquals(newCostPerGram, route.getCostPerGram(), "Cost per gram didn't get updated!");
        assertEquals(newCostPerVolume, route.getCostPerVolume(), "Cost per volume didn't get updated!");

        newNumberEvents = kpsModel.getAllEvens().size();
        assertEquals(oldNumberEvents + 2, newNumberEvents, "Number of events didn't increment!");
    }

    /**
     * Test that we can add new route if the parameters are valid
     */
    @Test
    public void route_AddingRoute_success() {
        KPSModel kpsModel = mockKPSModel();
        String start = "auckland";
        String end = "Paris";
        RouteType routeType = RouteType.Air;
        double duration = 50;
        String transportFirm = "Le Champ";
        int oldNumberEvents = kpsModel.getAllEvens().size();

        assertTrue(kpsModel.addRoute(start, end, routeType, duration, transportFirm, 999, 999,
                999, 999), "Adding new route failed!");

        int newNumberEvents = kpsModel.getAllEvens().size();
        assertEquals(oldNumberEvents + 1, newNumberEvents, "Number of events didn't increment!");
    }

    /**
     * Test that we cannot add new route if the system already has this route (even the id is different).
     */
    @Test
    public void route_AddingRoute_failure() {
        KPSModel kpsModel = mockKPSModel();
        Route route = kpsModel.getRouteById(1);
        String start = route.getStartLocation().getLocationName();
        String end = route.getEndLocation().getLocationName();
        RouteType routeType = route.routeType;
        double duration = route.getDuration();
        String transportFirm = route.getTransportFirm();

        // let's add a route with same start, end, duration, type, and transport company, even the id, cost, and price is different.
        assertFalse(kpsModel.addRoute(start, end, routeType, duration, transportFirm, 999, 999,
                999, 999), "Shouldn't be able to add a same route!");
    }

    /**
     * Test that we can discontinue a route
     */
    @Test
    public void route_DeactivateRoute() {
        KPSModel kpsModel = mockKPSModel();
        Route route = kpsModel.getRouteById(1);
        boolean isActve = route.isActive();
        int oldNumberEvents = kpsModel.getAllEvens().size();

        assertTrue(kpsModel.deactivateRoute(1), "Deactivating route failed!");
        assertEquals(false, kpsModel.getRouteById(1).isActive(), "The route is still active after deactivating!");

        int newNumberEvents = kpsModel.getAllEvens().size();
        assertEquals(oldNumberEvents + 1, newNumberEvents, "Number of events didn't increment!");
    }

    /**
     * Test that we can retrieve critical mails
     */
    @Test
    public void route_RetrievingCriticalMails() {
        // Too sad we have to do this :( because we haven't got time to unit-test the internal models. Have to clear the data destructively.
        KPSModel kpsModel = mockKPSModel();
        Map<Integer, Mail> mailCache = kpsModel.getAllMails();

        NZLocation dunedin = new NZLocation(7, NZCity.Dunedin);
        NZLocation auckland = new NZLocation(1, NZCity.Auckland);
        Mail mail_1 = new Mail(101, dunedin, auckland, 100, 100, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Mail mail_2 = new Mail(102, dunedin, auckland, 100, 100, Priority.Domestic_Standard, LocalDate.of(2016, 4, 3));
        Route route = new Route(1, dunedin, auckland, RouteType.Land, 4.0d, "RoyalPost", 2, 2, 5, 5);

        mail_1.setRoutes(Collections.singletonList(route));
        mail_2.setRoutes(Collections.singletonList(route));
        mailCache.put(mail_1.id, mail_1);
        mailCache.put(mail_2.id, mail_2);

        Map<Integer, Mail> criticalMails = kpsModel.getCriticalMails();

        criticalMails.values().forEach(mail -> {
            assertTrue(mail.getRevenue() - mail.getExpenditure() < 0, "This mail DOES NOT lose money!");
        });
    }

    // ============================================================
    //                 Private helper methods
    // ============================================================

    private KPSModel mockKPSModel() {
        KPSModel kpsModel = new KPSModel();
        kpsModel.login("123", "123");

        return kpsModel;
    }

    private Mail mockMail(KPSModel kpsModel) {
        String originString = "Christchurch";
        String destinationString = "Auckland";
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.Domestic_Standard;

        return kpsModel.processMail(originString, destinationString, weight, volume, priority);
    }
}
