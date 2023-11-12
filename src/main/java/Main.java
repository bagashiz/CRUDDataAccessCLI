import java.util.InputMismatchException;
import java.util.Scanner;

import repository.TaskRepository;
import repository.TaskRepositoryFactory;
import service.TaskService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("====================");
        System.out.println("Simple Todo List App");
        System.out.println("====================");
        System.out.println();

        // choose storage type
        int storageType = 0;
        try {
            System.out.printf("1. CSV\n2. SQLite\nChoose a storage type: ");
            storageType = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
        } catch (Exception e) {
            System.err.println("Invalid input.");
            System.exit(1);
        }

        // dependency injection
        TaskRepository repo = TaskRepositoryFactory.getTaskRepository(storageType);
        if (repo == null) {
            System.err.println("Invalid storage type.");
            System.exit(1);
        }

        TaskService service = new TaskService(repo, scanner);

        service.options();

        while (true) {
            int choice = 0;

            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }

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
                    service.options();
                    break;
                case 8:
                    System.out.println("Exiting the program.");
                    service.close();
                    System.exit(0);
                default:
                    System.err.println("Invalid input.");
                    System.out.println();
                    service.options();
            }
        }
    }
}