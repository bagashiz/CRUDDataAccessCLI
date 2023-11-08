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

        this.writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
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
        // avoids duplicate records by reopening the file in write mode
        writer.close();
        writer = new BufferedWriter(new FileWriter(FILE_NAME, false));

        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }

        writer.flush();
    }

    /**
     * Closes the reader and writer.
     */
    public void close() throws IOException {
        reader.close();
        writer.close();
    }
}
