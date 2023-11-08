package service;

import java.util.List;
import java.util.Scanner;

import entity.Task;
import repository.TaskRepository;

/**
 * TaskService is a class that handles the task-related operations
 * based on user input.
 */
public class TaskService {
    private TaskRepository repo;
    private Scanner scanner;

    public TaskService(TaskRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
    }

    /**
     * Displays all tasks.
     */
    public void getAllTasks() {
        List<Task> tasks = repo.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    /**
     * Creates a new task.
     */
    public void createTask() {
        System.out.println("Enter description:");
        String description = scanner.nextLine();

        Task task = repo.createTask(description);
        System.out.println("Task created: " + task);
    }

    /**
     * Updates a task.
     */
    public void updateTask() {
        System.out.println("Enter the id of the task to update:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Task task = repo.getTaskById(id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        System.out.println("Enter new description:");
        String description = scanner.nextLine();

        Task updatedTask = repo.updateTask(task.getId(), description, task.isDone());

        System.out.println("Task updated: " + updatedTask);
    }

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        System.out.println("Enter the id of the task to mark as done:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Task task = repo.getTaskById(id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        if (task.isDone()) {
            System.out.println("Task is already done.");
            return;
        }

        Task updatedTask = repo.updateTask(task.getId(), task.getDescription(), true);

        System.out.println("Task marked as done: " + updatedTask);
    }

    /**
     * Marks a task as undone.
     */
    public void markAsUndone() {
        System.out.println("Enter the id of the task to mark as undone:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Task task = repo.getTaskById(id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        if (!task.isDone()) {
            System.out.println("Task is already undone.");
            return;
        }

        Task updatedTask = repo.updateTask(task.getId(), task.getDescription(), false);

        System.out.println("Task marked as undone: " + updatedTask);
    }

    /**
     * Deletes a task.
     */
    public void deleteTask() {
        System.out.println("Enter the id of the task to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();

        repo.deleteTask(id);

        System.out.println("Task deleted.");
    }

    /**
     * Closes the connection to the data source.
     */
    public void close() {
        repo.close();
    }
}
