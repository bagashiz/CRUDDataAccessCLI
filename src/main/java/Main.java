import java.util.InputMismatchException;
import java.util.Scanner;

import entity.Data;
import service.Service;
import storage.CSVDataStorage;
import storage.JSONDataStorage;
import storage.Storage;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=====================");
        System.out.println("Simple Data Acess App");
        System.out.println("=====================");
        System.out.println();

        Storage<Data> storage = null;

        // choose storage type
        int storageType = 0;
        try {
            System.out.printf("1. CSV\n2. JSON\nChoose a storage type: ");
            storageType = scanner.nextInt();
            System.out.println();

            switch (storageType) {
                case 1:
                    storage = new CSVDataStorage("src/main/resources/data.csv");
                    break;
                case 2:
                    storage = new JSONDataStorage("src/main/resources/data.json");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // dependency injection
        Service service = new Service(storage, scanner);

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
                    System.out.println("Creating a new data.");
                    service.create();
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Data:");
                    service.getAll();
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Getting a single data.");
                    service.getById();
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Updating an existing data.");
                    service.update();
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Deleting an existing data.");
                    service.delete();
                    System.out.println();
                    break;
                case 6:
                    service.options();
                    break;
                case 7:
                    System.out.println("Exiting the app.");
                    System.exit(0);
                    break;
                default:
                    System.err.println("Invalid input.");
                    System.out.println();
                    service.options();
            }
        }
    }
}