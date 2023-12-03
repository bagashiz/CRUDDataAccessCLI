package service;

import java.util.List;
import java.util.Scanner;

import entity.Data;
import storage.Storage;

/**
 * Service is a class that handles the operations of the application
 * based on user input.
 */
public class Service {
    private Storage<Data> storage;
    private Scanner scanner;

    public Service(Storage<Data> storage, Scanner scanner) {
        this.storage = storage;
        this.scanner = scanner;
    }

    /**
     * Displays the options.
     */
    public void options() {
        System.out.printf(
                "Options:\n1. Create\n2. Read All\n3. Read\n4. Update\n5. Delete\n6. Options\n7. Exit\n");
    }

    /**
     * Displays all data
     */
    public void getAll() {
        try {
            List<Data> data = storage.read();

            if (data.isEmpty()) {
                System.out.println("No data to display.");
                return;
            }

            for (Data datum : data) {
                System.out.println(datum.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a single data by id.
     */
    public void getById() {
        Data data = null;

        System.out.println("Enter id:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            data = storage.readById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null) {
            System.out.println("Data not found.");
        } else {
            System.out.println(data.toString());
        }
    }

    /**
     * Creates a new data.
     */
    public void create() {
        System.out.println("Enter id:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter text:");
        String text = scanner.nextLine();
        System.out.println("Enter flag:");
        boolean flag = scanner.nextBoolean();
        scanner.nextLine();

        Data data = Data.builder()
                .id(id)
                .text(text)
                .flag(flag)
                .build();

        try {
            storage.create(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Data created.");
    }

    /**
     * Updates an existing data.
     */
    public void update() {
        Data data = null;

        System.out.println("Enter the id:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            data = storage.readById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null) {
            System.out.println("Data not found.");
            return;
        }

        System.out.println("Enter text:");
        String text = scanner.nextLine();
        System.out.println("Enter flag:");
        boolean flag = scanner.nextBoolean();
        scanner.nextLine();

        Data updatedData = Data.builder()
                .id(id)
                .text(text)
                .flag(flag)
                .build();

        try {
            storage.update(updatedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Data updated.");
    }

    /**
     * Deletes an existing data.
     */
    public void delete() {
        Data data = null;

        System.out.println("Enter the id:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            data = storage.readById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null) {
            System.out.println("Data not found.");
            return;
        }

        try {
            storage.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Data deleted.");
    }
}
