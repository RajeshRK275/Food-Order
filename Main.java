import java.util.*;

class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Executive executive = new Executive();
        Booking book = new Booking(executive);

        ArrayList<Booking> bookings = new ArrayList<>();
        HashMap<Integer, Booking> tripsMap = new HashMap<>();

        System.out.println("Enter the number of Delivery Partners : ");
        int num = sc.nextInt();
        executive.setupDelivery(num);

        while (true) {
            System.out.println("Enter \n 1)Place Order \n 2) Delivery History \n 3) Display Earnings ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    book.orderFood(executive.executives, bookings, tripsMap);
                    // place order
                    break;
                case 2:
                    // delivery history
                    book.printDeliveryHistory(tripsMap);
                    break;
                case 3:
                    // earnings
                    executive.displayEarnings();
                    break;
                default:
                    break;
            }
            if (ch > 3 || ch < 1)
                break;
        }

        sc.close();

    }
}
