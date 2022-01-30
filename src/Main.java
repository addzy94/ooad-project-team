import java.util.Scanner;
public class Main {
    public static int hello(){
        System.out.println("For Project 2 Part 2:");
        System.out.println("Please enter your keyword.");
        System.out.println("Your input: ");
        Scanner scanner = new Scanner(System.in);
        //set a value to keep the input entered by user through scanner
        int input = scanner.nextInt();
        System.out.println("Received input is: "+input);
        return input;
    }

    public static void main(String[] args) {
        //print welcome word and read the user input
        int input=hello();
    }
}

