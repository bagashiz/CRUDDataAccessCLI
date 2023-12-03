package storage;

import java.util.List;

/**
 * Storage is an interface for CRUD operations on a data storage.
 */
public interface Storage<T> {
    /**
     * Reads all data from the storage.
     * 
     * @return List<T>
     * @throws Exception
     */
    public List<T> read() throws Exception;

    /**
     * Reads a single data from the storage by id.
     * 
     * @param id
     * @return T
     * @throws Exception
     */
    public T readById(int id) throws Exception;

    /**
     * Creates a new data in the storage.
     * 
     * @param data
     * @throws Exception
     */
    public void create(T data) throws Exception;

    /**
     * Updates an existing data in the storage.
     * 
     * @param data
     * @throws Exception
     */
    public void update(T data) throws Exception;

    /**
     * Deletes an existing data in the storage.
     * 
     * @param data
     * @throws Exception
     */
    public void delete(int id) throws Exception;
}
