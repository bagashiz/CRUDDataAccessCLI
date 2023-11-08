import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task {
    private int id;
    private String description;
    private boolean isDone;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.isDone = false;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        String fmt = String.format("%d. %s", this.id, this.description);
        return fmt + (this.isDone ? " [X]" : " [ ]");
    }
}

public class Main {
    private static final String FILE_NAME = "src/main/resources/database.csv";
    private static final List<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadTasksFromFile();

        while (true) {
            System.out.printf(
                    "Options:\n1. Create task\n2. Read tasks\n3. Update task\n4. Delete task\n5. Mark as done\n6. Mark as undone\n7. Exit\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Creating a new task.");
                    createTask(scanner);
                    break;
                case 2:
                    System.out.println("Tasks:");
                    readTasks();
                    break;
                case 3:
                    System.out.println("Updating a task.");
                    updateTask(scanner);
                    break;
                case 4:
                    System.out.println("Deleting a task.");
                    deleteTask(scanner);
                    break;
                case 5:
                    System.out.println("Marking a task as done.");
                    markAsDone(scanner);
                    break;
                case 6:
                    System.out.println("Marking a task as undone.");
                    markAsUndone(scanner);
                    break;
                case 7:
                    saveTasksToFile();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createTask(Scanner scanner) {
        System.out.println("Enter description:");
        String description = scanner.nextLine();

        int id = tasks.size() + 1;

        Task task = new Task(id, description);
        tasks.add(task);
        System.out.println("Task created: " + task);
    }

    private static void readTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void updateTask(Scanner scanner) {
        System.out.println("Enter the id of the task to update:");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Task task : tasks) {
            if (task.getId() == id) {
                System.out.println("Enter new description:");
                String description = scanner.nextLine();
                task.setDescription(description);
                System.out.println("Task updated: " + task);
                return;
            }
        }

        System.out.println("Task not found.");
    }

    private static void markAsDone(Scanner scanner) {
        System.out.println("Enter the id of the task to mark as done:");
        int id = scanner.nextInt();

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.markAsDone();
                System.out.println("Task marked as done: " + task);
                return;
            }
        }

        System.out.println("Task not found.");
    }

    private static void markAsUndone(Scanner scanner) {
        System.out.println("Enter the id of the task to mark as undone:");
        int id = scanner.nextInt();

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.markAsUndone();
                System.out.println("Task marked as undone: " + task);
                return;
            }
        }

        System.out.println("Task not found.");
    }

    private static void deleteTask(Scanner scanner) {
        System.out.println("Enter the id of the task to delete:");
        int id = scanner.nextInt();

        for (Task task : tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                System.out.println("Task deleted: " + task);
                return;
            }
        }

        System.out.println("Task not found.");
    }

    private static void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String description = parts[1].trim();
                    tasks.add(new Task(id, description));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
    }

    private static void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(String.format("%d,%s,%s", task.getId(), task.getDescription(), task.isDone()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}