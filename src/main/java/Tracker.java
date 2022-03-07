import java.util.ArrayList;

public class Tracker implements Observer{
    ArrayList<ArrayList<Object>> information;
    ArrayList<Store> currentStores;
    private static final int name = 0; //ENUMERATIONS FOR READABILITY
    private static final int items_sold = 1;
    private static final int items_purchased = 2;
    private static final int items_damaged = 3;
    private static final int store_name = 4;

    private static Tracker myTracker;

    private Tracker() {
        information = new ArrayList<>();
        currentStores = new ArrayList<>();
    }

    public void addStaff(ArrayList<Staff> s) {
        for (int j = 0; j < s.size(); j++) {
            for (int i = 0; i < currentStores.size(); i++) {
                ArrayList<Object> staff_info = new ArrayList<>();
                staff_info.add(s.get(j).getName());
                staff_info.add(0); //items sold
                staff_info.add(0); //items purchased
                staff_info.add(0); //items damaged
                staff_info.add(currentStores.get(i).getStoreName()); //Store name
                information.add(staff_info);
            }
        }
        
        
    }

    public void printInfo() {
        System.out.println();
        System.out.println("Tracker: Day " + Store.getDay());
        System.out.println("Clerk\tItems Sold\tItems Purchased\t\tItems Damaged\tStore Worked");
        for (ArrayList<Object> staff_member: information) {
            System.out.println(staff_member.get(name) + "\t\t" + staff_member.get(items_sold) + "\t\t" + staff_member.get(items_purchased) + "\t\t" + staff_member.get(items_damaged) + "\t\t"+ staff_member.get(store_name));
        }
        System.out.println();
    }

    public void update(Clerk c) {
        if (c.getMessage().contains("sold")) {
            updateStat(c, items_sold);
        }
        if (c.getMessage().contains("bought")) {
            updateStat(c, items_purchased);
        }
        if (c.getMessage().contains("damaged")) {
            updateStat(c, items_damaged);
        }
    }

    public void updateStat(Clerk c, int stat) {
        for (int i = 0; i < information.size(); i++) {
            if (c.getName().equals(information.get(i).get(name)) && c.getStore().getStoreName().equals(information.get(i).get(store_name))) {
                ArrayList<Object> staff_member = information.get(i);
                staff_member.set(stat, (int)staff_member.get(stat) + Integer.parseInt(c.getMessage().split(" ")[0]));
                information.set(i, staff_member);
            }
        }
    }
    public static Tracker getTracker() {
        if (myTracker == null) {
            myTracker = new Tracker();
        }
        return myTracker;
    }

    public void addStore(Store s) {
        currentStores.add(s);
    }

    public ArrayList<Store> getStores() {
        return currentStores;
    }
}
