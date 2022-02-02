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

        //Process
        //1. Initialize starting inventory of merchandise items
        //three pieces per lowest subclass item
        //self-defined purchasse price from $1 to $50
        //list price = 2 * purchase price
        //dayArrived = 0
        //condition from 1 to 5, self-defined as well???????????????????????????/
        //salePrice and daySold will be set when the item is sold


        //Store our_shop=new Store();
        //our_shop.initialize_inventory()  ----  initialize 3 pieces per lowest subclass item

        //2. Simulation starts
        //run daily-based loops - can we define what day of the week as the starting day????????
        //assign a clerk to work for that day (allow work for maximum 3 days in a row) - if Fri/Sat/Sun(break)/Mon, then is it still 3 days in a row?
        //print announcement for reporting who arrives at the store on which day
        //check delivered orders called by PlaceAnOrder action, and put them into the inventory item list for selling
        //

        //our_shop.run_business();
            //which contains:
            //ArriveAtStore
            //CheckRegister
            //GoToBank
            //DoInventory
            //PlaceAnOrder
            //OpenTheStore
            //CleanTheStore
            //LeaveTheStore

        //or

        //for(int i=0;i<30;i++){
        //  our_shop.run_a_day();
        //}

        //3. At the end of the 30th day, print out a summary of the state of the simulated store
        //4. Record all console output in Output.txt
    }
}

