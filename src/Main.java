import java.util.Scanner;


public class Main {
    public static int hello(){
        System.out.println("For Project 2 Part 2:");
        System.out.println("Please enter your keyword.");
        System.out.println("Your input: ");
        Scanner scanner = new Scanner(System.in);
        //set a value to keep the input entered by user through scanner
        int input = -1;
        //keep asking for input until input is int
        while(!scanner.hasNextInt()){
            System.out.println("\""+scanner.nextLine()+"\""+" is not valid Integer, please try again:");
        }
        input = scanner.nextInt();
        System.out.println(input+" is a valid Integer");
        return input;
    }

    public static void main(String[] args) {
        //print welcome word and read the user input
        //int input=hello();

        //test cases
        PaperScore test= new PaperScore("Stars", 12.5, 2, 5, true, "Fun.","Some Nights");
        System.out.println("the name of the song is: " + test.getName());
        System.out.println("the condition is: " + test.getCondition());
        System.out.println("the band name is: " + test.getBand());

        RunStore a=new RunStore();
        a.run();

        //Process
        //1. Initialize starting inventory of merchandise items
        //three pieces per lowest subclass item
        //self-defined purchase price from $1 to $50
        //list price = 2 * purchase price
        //dayArrived = 0
        //condition from 1 to 5, OR ENUM, self-defined as well, randomly called by any number
        //salePrice and daySold will be set when the item is sold


        //Store our_shop=new Store();
        //our_shop.initialize_inventory()  ----  initialize 3 pieces per lowest subclass item

        //2. Simulation starts
        //run daily-based loops - can we define what day of the week as the starting day. Yep!
        //assign a clerk to work for that day (allow work for maximum 3 days in a row)
        // if Fri/Sat/Sun(break)/Mon, then is it still 3 days in a row? - We can define the rule here, and currently we are assuming that Mon does not count as the wrk day continued from Saturday.

        //our_shop.run_business(30);  30 means to run the method 30 times, since we are defining it as a daily-based method
        //which contains:
            //ArriveAtStore:
                // clerk announce their arrival at the store
                // check delivered orders called by PlaceAnOrder action, and put them into the inventory item list for selling
            //CheckRegister:
                // clerk will count and announce the amount of money found in the register
                // if there is insufficient money in the register, the clerk must perform the GoToBank action next before going on to DoInventory
            //GoToBank:
                // performed if there is less than $75 in the register
                // goto bank, withdraw $1000, put money in the store register with an announcement  - can the bank offer infinite amount of money? Yep.
                // the amount of money withdrawn from the bank in this manner should be tracked for total amount of money been taken form the bank
            //DoInventory:
                // the clerk will add up the value of all the items in the store based on purchasePrice, announce that total value
                // if any of the ite subclasses has a count of 0 (e.g. 0 CD player item in the inventory), perform PlaceAnOrder action for each missing item
                // announce this action
            //PlaceAnOrder:
                // works for each subclass item that has 0 inventory
                // orders 3 for each missing item subclass, each with random purchasePrice and other randomly determined characteristics
                // purchasePrice paid for each item should be done by removing the funds from Cash Register
                // these ordered items should arrive at store in teh next 1 to 3 days (handled by ArriveAtStore method)
                // all activity should be announced
            //OpenTheStore:
                // clerk will respond to arriving customers.
                // customers will come in to either buy or sell a single item.
                // wil be 4 to 10 buying customers and 1 to 4 selling customers each day
                // all actions taken by customers should be announced
                // customer that didn't buy or sell due to possible reasons still counts as 1 buying/selling customer? Yep!
            //CleanTheStore:
                // Velma is a careful cleaner and only damages a random item in inventory 5% of the time
                // Shaggy is less careful and damages a random item in inventory 20% of the time
                // When damaged, lower that damaged item condition by 1.
                // If item condition was already poor (1) before damaging, remove it from the list of inventory
                // anything that occurs in this phase should be announced
            //LeaveTheStore:
                // clerk lock up the store and go home.
                // announce this action.

        //3. At the end of the 30th day, print out a summary of the state of the simulated store
        //4. Record all console output in Output.txt
    }

//  Ultimate delivery - just to make the main class as neat as possible, so that the user don't need to see so many detailed codes here
//    public static void main(String[] args) {
//        Store our_shop = new Store();
//        //our_shop.initialize_inventory();  can be implemented into the constructor directly
//        our_shop.run_daily_based_business(30);
//        our_shop.report();
//    }
}

