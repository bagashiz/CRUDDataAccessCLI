package storage;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import entity.Data;

/**
 * JSON is a class that implements Storage interface.
 */
public class JSONDataStorage implements Storage<Data> {
    private File file;
    private ObjectMapper mapper;
    private List<Data> data;

    public JSONDataStorage(String filePath) throws Exception {
        file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        mapper = new ObjectMapper();
        data = this.read();
    }

    @Override
    public List<Data> read() throws Exception {
        TypeReference<List<Data>> typeReference = new TypeReference<List<Data>>() {
        };

        try {
            this.data = mapper.readValue(file, typeReference);
        } catch (MismatchedInputException e) {
            mapper.writeValue(file, data);
        }

        return this.data;
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
        mapper.writeValue(file, this.data);
    }

    @Override
    public void update(Data data) throws Exception {
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == data.getId()) {
                this.data.set(i, data);
                mapper.writeValue(file, this.data);
                return;
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == id) {
                this.data.remove(i);
                mapper.writeValue(file, this.data);
                return;
            }
        }
    }
}