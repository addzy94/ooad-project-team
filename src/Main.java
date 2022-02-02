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
        Music test=new Music("Stars", 12.5, 2, 5, "Fun.", "Some Nights");
        System.out.println("the name of the song is: " + test.getName());
        System.out.println("the condition is: " + test.getCondition());
        System.out.println("the band name is: " + test.getBand());
    }
}

