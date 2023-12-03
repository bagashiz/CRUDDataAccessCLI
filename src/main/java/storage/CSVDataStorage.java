package storage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import entity.Data;
import entity.DataBuilder;

/**
 * CSV is a class that implements Storage interface.
 */
public class CSVDataStorage implements Storage<Data> {
    private File file;
    private CSVReader reader;
    private CSVWriter writer;
    private List<Data> data;

    public CSVDataStorage(String filePath) throws Exception {
        file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        reader = new CSVReader(new FileReader(filePath));
        writer = new CSVWriter(new FileWriter(filePath));

        data = this.read();
    }

    @Override
    public List<Data> read() throws Exception {
        List<Data> dataList = new ArrayList<>();
        List<String[]> rows = reader.readAll();

        for (String[] row : rows) {
            Data data = new DataBuilder()
                    .id(Integer.parseInt(row[0]))
                    .text(row[1])
                    .flag(Boolean.parseBoolean(row[2]))
                    .build();

            dataList.add(data);
        }

        return dataList;
    }

    @Override
    public Data readById(int id) throws Exception {
        for (Data data : this.data) {
            if (data.getId() == id) {
                return data;
            }
        }

        return null;
    }

    @Override
    public void create(Data data) throws Exception {
        this.data.add(data);
        writeToFile();
    }

    @Override
    public void update(Data data) throws Exception {
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == data.getId()) {
                this.data.set(i, data);
            }
        }

        writeToFile();
    }

    @Override
    public void delete(int id) throws Exception {
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == id) {
                this.data.remove(i);
            }
        }

        writeToFile();
    }

    private void writeToFile() throws Exception {
        List<String[]> records = new ArrayList<>();

        for (Data data : this.data) {
            String[] record = {
                    String.valueOf(data.getId()),
                    data.getText(),
                    String.valueOf(data.isFlag())
            };

            records.add(record);
        }

        writer.writeAll(records);
    }
}