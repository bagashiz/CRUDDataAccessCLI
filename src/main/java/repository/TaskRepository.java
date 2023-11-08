package repository;

import java.util.List;

import entity.Task;

/**
 * TaskRepository is an interface that defines the task-related operations that
 * can be performed for a data source.
 */
public interface TaskRepository {
    public List<Task> getAllTasks();

    public Task getTaskById(int id);

    public Task createTask(String description);

    public Task updateTask(int id, String description, boolean isDone);

    public void deleteTask(int id);

    public void close();
}
