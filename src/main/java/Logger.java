import java.io.FileWriter; //https://www.w3schools.com/java/java_files_create.asp
import java.io.IOException;

public class Logger implements Observer{

    private FileWriter currentFile;

    public Logger() {
        //do nothing
    }

    public void instantiate(int day) {
        try {
            currentFile = new FileWriter("logger_files/Logger-" + day + ".txt");
        }
        catch (IOException e) {
            System.out.println("ERROR: Unable to create logger file for day " + day);
        }
        
    }

    public void close() {
        try {
            currentFile.close();
        }
        catch (IOException e) {
            System.out.println("ERROR: Unable to close file");
        }
        
    }

    public void update(Clerk c) {
        try {
            currentFile.write("\n" + c.getMessage() + "\n");
        }
        catch (IOException e) {
            System.out.println("ERROR: Unable to write to file.");
        }
    }
}