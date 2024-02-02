import yoda.YodaUI;
import yoda.utils.Parser;
import yoda.utils.Storage;
import yoda.task.TaskList;
import java.util.Scanner;
import java.io.IOException;


public class Yoda {
    private final YodaUI yodaUI;

    public Yoda(String filePath) {
        Storage storage = new Storage(filePath);
        TaskList tasks;
        try {
            // Load tasks from the storage
            tasks = storage.loadTasks();
        } catch (IOException e) {
            // If there's an error loading tasks, start with an empty task list
            System.out.println("Error loading tasks: " + e.getMessage());
            tasks = new TaskList(null);
        }
        // Initialize YodaUI with the loaded tasks
        this.yodaUI = new YodaUI("Yoda", tasks, storage);
    }

    public static void main(String[] args) {
        // Initialize Yoda with the file path for storing tasks
        Yoda yoda = new Yoda("data/yoda.txt");

        Scanner scanner = new Scanner(System.in);
        Parser commandParser = new Parser(yoda.yodaUI);

        // Display the greeting message
        yoda.yodaUI.printGreeting();

        while (yoda.yodaUI.isChatting()) {
            String input = scanner.nextLine();
            try {
                commandParser.parseAndExecute(input);
            } catch (Exception e) {
                yoda.yodaUI.printMessage("Error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
