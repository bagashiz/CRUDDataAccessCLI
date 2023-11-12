package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import entity.Task;
import storage.SQLite;

/**
 * SQLiteTaskRepository is a class that implements the TaskRepository interface
 * using a SQLite database as the data source.
 */
public class SQLiteTaskRepository implements TaskRepository {
    private SQLite db;

    public SQLiteTaskRepository() {
        try {
            this.db = new SQLite();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // create table if it doesn't exist
        try {
            db.getStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY, description TEXT, is_done INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Returns a copy of all tasks in a new LinkedList.
     */
    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new LinkedList<>();

        try {
            ResultSet res = db.getStatement().executeQuery("SELECT * FROM tasks");

            while (res.next()) {
                int id = res.getInt("id");
                String description = res.getString("description");
                boolean isDone = res.getBoolean("is_done");

                Task task = new Task(id, description, isDone);
                tasks.add(task);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return tasks;
    }

    /**
     * Returns a task with the given ID.
     */
    @Override
    public Task getTaskById(int id) {
        Task task = null;

        try {

            ResultSet res = db.getStatement().executeQuery("SELECT * FROM tasks WHERE id = " + id);

            if (res.next()) {
                String description = res.getString("description");
                boolean isDone = res.getBoolean("is_done");

                task = new Task(id, description, isDone);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return task;
    }

    /**
     * Creates a new task with a sequential ID.
     */
    @Override
    public Task createTask(String description) {
        Task task = null;

        try {
            db.getStatement()
                    .executeUpdate("INSERT INTO tasks (description) VALUES ('" + description + "')");
            ResultSet res = db.getStatement().executeQuery("SELECT last_insert_rowid()");

            if (res.next()) {
                int id = res.getInt(1);
                task = new Task(id, description);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return task;
    }

    /**
     * Updates the task with the same ID as the given task.
     */
    @Override
    public Task updateTask(int id, String description, boolean isDone) {
        Task task = null;

        try {
            db.getStatement().executeUpdate("UPDATE tasks SET description = '" + description + "', is_done = "
                    + (isDone ? 1 : 0) + " WHERE id = " + id);
            task = new Task(id, description, isDone);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return task;
    }

    /**
     * Deletes the task with the given ID.
     */
    @Override
    public void deleteTask(int id) {
        try {
            db.getStatement().executeUpdate("DELETE FROM tasks WHERE id = " + id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Closes the database connection.
     */
    @Override
    public void close() {
        try {
            db.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
