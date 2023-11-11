package repository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import entity.Task;
import storage.CSVFile;

/**
 * CSVTaskRepository is a class that implements the TaskRepository interface
 * using a CSV file as the data source. Tasks are stored in a LinkedList to
 * ensure that IDs remain sequential when tasks are deleted.
 */
public class CSVTaskRepository implements TaskRepository {
    private LinkedList<Task> tasks;
    private final CSVFile csvFile;

    public CSVTaskRepository(CSVFile csvFile) {
        this.csvFile = csvFile;
        this.tasks = new LinkedList<>();
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
     * Returns a copy of all tasks in a new LinkedList.
     */
    @Override
    public List<Task> getAllTasks() {
        return new LinkedList<>(tasks);
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
     * Creates a new task with a sequential ID.
     */
    @Override
    public Task createTask(String description) {
        int id = tasks.isEmpty() ? 1 : tasks.getLast().getId() + 1;
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
        for (Task task : tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                return;
            }
        }
    }

    /**
     * Saves the tasks to the CSV file and closes the file.
     */
    @Override
    public void close() {
        List<String> taskStrings = new LinkedList<>();
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
     * Converts a csv string to a task.
     */
    private Task convertToTask(String taskString) {
        String[] parts = taskString.split(",");

        int id = Integer.parseInt(parts[0]);
        String description = parts[1];
        boolean isDone = Boolean.parseBoolean(parts[parts.length - 1]);

        // if there are commas in the description
        if (parts.length > 3) {
            // concatenate the description
            for (int i = 2; i < parts.length - 1; i++) {
                description += "," + parts[i];
            }
        }

        // remove the quotes from the description
        if (description.startsWith("\"") && description.endsWith("\"")) {
            description = description.substring(1, description.length() - 1);
        }

        return new Task(id, description, isDone);
    }

    /**
     * Converts a task to a csv string.
     */
    private String convertToString(Task task) {
        return task.getId() + "," + "\"" + task.getDescription() + "\"," + task.isDone();
    }
}
