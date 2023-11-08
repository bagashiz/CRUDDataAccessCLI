package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVFile is a class for reading and writing CSV files.
 */
public class CSVFile {
    private static final String FILE_NAME = "src/main/resources/database.csv";
    private BufferedReader reader;
    private BufferedWriter writer;

    public CSVFile() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        this.writer = new BufferedWriter(new FileWriter(FILE_NAME));
        this.reader = new BufferedReader(new FileReader(FILE_NAME));
    }

    /**
     * Reads all lines from the CSV file.
     */
    public List<String> load() throws IOException {
        List<String> lines = new ArrayList<>();

        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }

        return lines;
    }

    /**
     * Writes lines of records to the CSV file.
     */
    public void save(List<String> lines) throws IOException {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }

        writer.flush();
    }

    /**
     * Closes the reader and writer.
     */
    public void close() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
