import java.io.IOException;
import java.util.Scanner;

import repository.CSVTaskRepository;
import repository.TaskRepository;
import service.TaskService;
import storage.CSVFile;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // initialize connection to CSV file
        CSVFile csvFile = null;
        try {
            csvFile = new CSVFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // dependency injection
        TaskRepository repo = new CSVTaskRepository(csvFile);
        TaskService service = new TaskService(repo, scanner);

        while (true) {
            System.out.printf(
                    "Options:\n1. Create task\n2. Read tasks\n3. Update task\n4. Mark as done\n5. Mark as undone\n6. Delete task\n7. Exit\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Creating a new task.");
                    service.createTask();
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Tasks:");
                    service.getAllTasks();
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Updating a task.");
                    service.updateTask();
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Marking a task as done.");
                    service.markAsDone();
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Marking a task as undone.");
                    service.markAsUndone();
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Deleting a task.");
                    service.deleteTask();
                    System.out.println();
                    break;
                case 7:
                    System.out.println("Exiting the program.");
                    service.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}