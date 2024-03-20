import java.util.ArrayList;

public class Executive {
    static int number = 1;
    int executiveNum;
    private int totalChargesEarned = 0;
    private int totalAllovanceEarned = 0;
    private char currentLocation = 'A';

    public ArrayList<Executive> executives = new ArrayList<>();

    public Executive() {
    }

    public Executive(int executiveNum, int totalAllovanceEarned, int totalChargesEarned, char currentLocation) {
        this.executiveNum = executiveNum;
        this.totalChargesEarned = totalChargesEarned;
        this.totalAllovanceEarned = totalAllovanceEarned;
        this.currentLocation = currentLocation;
    }

    public void setupDelivery(int num) {
        int i = 1;
        while (i++ <= num) {
            Executive executive = new Executive(number++, 0, 0, 'A');
            executives.add(executive);
        }
    }

    int getTotalChargesEarned() {
        return this.totalChargesEarned;
    }

    int getTotalAllovanceEarned() {
        return this.totalAllovanceEarned;
    }

    char getCurrentLocation() {
        return this.currentLocation;
    }

    void setTotalChargesEarned(int fare) {
        this.totalChargesEarned += fare;
    }

    void setAllovanceEarned(int fare) {
        this.totalAllovanceEarned += fare;
    }

    void setLocation(char loc) {
        this.currentLocation = loc;
    }

    // printing earnings
    public void displayEarnings() {
        System.out.println("Executive ChargesEarned  AllovanceEarned\tTotal");
        for (Executive e : executives) {
            System.out.println("  DE" + e.executiveNum + "\t\t" + e.totalChargesEarned + "\t\t" + e.totalAllovanceEarned
                    + "\t\t" + (e.totalChargesEarned + e.totalAllovanceEarned));
        }
    }

    public Executive getLowestEarnedExecutive(ArrayList<Executive> executives) {
        int minValue = Integer.MAX_VALUE;
        Executive minEarner = null;
        for (Executive e : executives) {
            if (e.getTotalChargesEarned() < minValue) {
                minEarner = e;
                minValue = e.getTotalChargesEarned();
            }
        }
        return minEarner;
    }

}
