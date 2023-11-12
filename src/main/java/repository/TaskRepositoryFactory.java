package repository;

/**
 * TaskRepositoryFactory is a class that returns a TaskRepository object based
 * on the storage type.
 */
public class TaskRepositoryFactory {
    public static TaskRepository getTaskRepository(int storageType) {
        switch (storageType) {
            case 1:
                return new CSVTaskRepository();
            case 2:
                return new SQLiteTaskRepository();
            default:
                return null;
        }
    }
}
