import java.util.ArrayList;

public class Tracker implements Observer{
    ArrayList<ArrayList<Object>> information;

    public Tracker() {
        information = new ArrayList<>();
    }

    public void addStaff(Staff s) {
        ArrayList<Object> staff_info = new ArrayList<>();
        staff_info.add(s.getName());
        staff_info.add(0); //items sold
        staff_info.add(0); //items purchased
        staff_info.add(0); //items damaged
        information.add(staff_info);
    }

    public void printInfo(Store s) {
        System.out.println("Tracker: Day " + s.getDay());
        System.out.println("Clerk\tItems Sold\tItems Purchased\t\tItems Damaged");
        for (ArrayList<Object> staff_member: information) {
            System.out.println(staff_member.get(0) + "\t\t" + staff_member.get(1) + "\t\t" + staff_member.get(2) + "\t\t\t" + staff_member.get(3));
        }
    }

    public void update(Clerk c) {
        if (c.getMessage().contains("sold")) {
            for (int i = 0; i < information.size(); i++) {
                if (c.getName().equals(information.get(i).get(0))) {
                    ArrayList<Object> staff_member = information.get(i);
                    staff_member.set(1, (int)staff_member.get(1) + Integer.parseInt(c.getMessage().split(" ")[0]));
                    information.set(i, staff_member);
                }
            }
        }
        if (c.getMessage().contains("bought")) {
            for (int i = 0; i < information.size(); i++) {
                if (c.getName().equals(information.get(i).get(0))) {
                    ArrayList<Object> staff_member = information.get(i);
                    staff_member.set(2, (int)staff_member.get(2) + Integer.parseInt(c.getMessage().split(" ")[0]));
                    information.set(i, staff_member);
                }
            }
        }
        if (c.getMessage().contains("damaged")) {
            for (int i = 0; i < information.size(); i++) {
                if (c.getName().equals(information.get(i).get(0))) {
                    ArrayList<Object> staff_member = information.get(i);
                    staff_member.set(3, (int)staff_member.get(3) + Integer.parseInt(c.getMessage().split(" ")[0]));
                    information.set(i, staff_member);
                }
            }
        }
    }
}
