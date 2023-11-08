package repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Task;
import storage.CSVFile;

/**
 * CSVTaskRepository is a class that implements the TaskRepository interface
 * using a CSV file as the data source.
 */
public class CSVTaskRepository implements TaskRepository {
    private List<Task> tasks;
    private final CSVFile csvFile;

    public CSVTaskRepository(CSVFile csvFile) {
        this.csvFile = csvFile;
        this.tasks = new ArrayList<>();
        List<String> taskStrings = null;

        try {
            taskStrings = csvFile.load();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        for (String taskString : taskStrings) {
            Task task = convertToTask(taskString);
            if (task != null) {
                tasks.add(task);
            }
        }
    }

    /**
     * Returns all tasks.
     */
    @Override
    public List<Task> getAllTasks() {
        return this.tasks;
    }

    /**
     * Returns a task with the given ID.
     */
    @Override
    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }

        return null;
    }

    /**
     * Creates a new task.
     */
    @Override
    public Task createTask(String description) {
        int id = tasks.size() + 1;
        Task task = new Task(id, description);
        tasks.add(task);

        return task;
    }

    /**
     * Updates the task with the same ID as the given task.
     */
    @Override
    public Task updateTask(int id, String description, boolean isDone) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(description);
                task.setIsDone(isDone);
                return task;
            }
        }

        return null;
    }

    /**
     * Deletes the task with the given ID.
     */
    @Override
    public void deleteTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return;
            }
        }
    }

    /**
     * Saves the tasks to the CSV file and closes the file.
     */
    @Override
    public void close() {
        List<String> taskStrings = new ArrayList<>();
        for (Task task : tasks) {
            taskStrings.add(convertToString(task));
        }

        try {
            csvFile.save(taskStrings);
            csvFile.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Converts a task string to a Task object.
     * 
     * @example "1,Task description,true" -> Task object
     */
    private Task convertToTask(String taskString) {
        String[] parts = taskString.split(",");
        if (parts.length == 3) {
            int id = Integer.parseInt(parts[0]);
            String description = parts[1];
            boolean isDone = Boolean.parseBoolean(parts[2]);
            return new Task(id, description, isDone);
        }
        return null;
    }

    /**
     * Converts a Task object to a task string.
     * 
     * @example Task object -> "1,Task description,true"
     */
    private String convertToString(Task task) {
        return task.getId() + "," + task.getDescription() + "," + task.isDone();
    }
}
