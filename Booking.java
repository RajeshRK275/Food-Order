import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Booking {
    static int num = 1;
    int tripId;
    int executiveNum;
    char hotelLoaction;
    char deliveryLoaction;
    int earnings;
    int allovance;
    int noOfOrders;
    LocalTime pickupTime;
    LocalTime deliveryTime;
    Executive executive;

    Scanner sc = new Scanner(System.in);

    public Booking(Executive executive) {
        this.executive = executive;
    }

    public Booking(int executiveNum, char hotelLoaction, char deliveryLoaction, int noOfOrders, LocalTime pickupTime,
            LocalTime deliveryTime, int earnings) {
        this.executiveNum = executiveNum;
        this.hotelLoaction = hotelLoaction;
        this.deliveryLoaction = deliveryLoaction;
        this.noOfOrders = noOfOrders;
        this.pickupTime = pickupTime;
        this.deliveryTime = deliveryTime;
        this.earnings = earnings;
    }

    public void orderFood(ArrayList<Executive> executives, ArrayList<Booking> bookings,
            HashMap<Integer, Booking> tripsMap) {

        System.out.println("Enter Customer Id : ");
        int customerId = sc.nextInt();
        System.out.println("Enter Restaurant Loaction : ");
        char restaurantLocation = sc.next().charAt(0);
        System.out.println("Enter Delivery Loaction : ");
        char deliveryLocation = sc.next().charAt(0);
        System.out.println("Enter Time in (HH:mm) format: ");
        String timeString = sc.next();

        try {
            pickupTime = LocalTime.parse(timeString);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please enter time in HH:mm format.");
            return;
        }

        // System.out.println("Pick-Up Location: " + restaurantLocation);
        // System.out.println("Drop Location: " + deliveryLocation);
        // System.out.println("Pick-Up Time: " + pickupTime);

        executive.displayEarnings();

        // check if any delivery guys is departing to that lacation in the next 15 mins
        // from any of the hotel
        // check if he has not more than 5 orders
        // if delivery can be done in existing trip
        
        int flag = 0;
        for (Map.Entry<Integer, Booking> entry : tripsMap.entrySet()) {
            Integer key = entry.getKey();
            Booking b = entry.getValue();
            if (b.deliveryLoaction == deliveryLocation && b.noOfOrders < 5) {
                Duration duration = Duration.between(pickupTime, b.pickupTime);
                long timeGap = duration.toMinutes();
                if (timeGap <= 15 && timeGap >= 0) {
                    // thats the guy
                    b.noOfOrders += 1;
                    b.earnings = (50 + (5 * (b.noOfOrders - 1)));
                    executives.get(b.executiveNum - 1).setTotalChargesEarned(5);
                    flag = 1;
                    System.out.println("Allotted Delivery Executive : DE" + b.executiveNum
                            + " (because same location within 15mins)");
                    return;
                }
            }
        }
        /// assign a guy with the lowest earnings
        /// so new trip is needed for this delivery
        if (flag == 0) {
            Executive lowestEarner = executive.getLowestEarnedExecutive(executives);
            lowestEarner.setAllovanceEarned(10);
            lowestEarner.setTotalChargesEarned(50);
            lowestEarner.setLocation(deliveryLocation);
            pickupTime = pickupTime.plusMinutes(15);
            LocalTime deliveryTime = pickupTime.plusMinutes(30);
            Booking booked = new Booking(lowestEarner.executiveNum, restaurantLocation, deliveryLocation, 1, pickupTime,
                    deliveryTime, 50);
            tripsMap.put(num++, booked);
            System.out.println("Allotted Delivery Executive : DE" + lowestEarner.executiveNum);
            return;
        }
    }

    public void printDeliveryHistory(Map<Integer, Booking> tripsMap) {
        System.out.println(
                "TRIP EXECUTIVE RESTAURANT DESTINATION ORDERS PICK-UP_TIME DELIVERY_TIME \tCHARGE");
        for (Map.Entry<Integer, Booking> entry : tripsMap.entrySet()) {
            Integer trip = entry.getKey();
            Booking book = entry.getValue();
            System.out.println(trip + "\t" + "DE" + book.executiveNum + "\t" + book.hotelLoaction + "\t\t"
                    + book.deliveryLoaction + "\t" + book.noOfOrders + "\t" + book.pickupTime + "\t\t"
                    + book.deliveryTime
                    + "\t\t" + book.earnings);
        }
    }
}
