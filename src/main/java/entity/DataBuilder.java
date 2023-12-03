package entity;

/**
 * DataBuilder class for building Data objects.
 */
public class DataBuilder {
    private int id;
    private String text;
    private boolean flag;

    /**
     * @param id the id to set
     * @return DataBuilder
     */
    public DataBuilder id(int id) {
        this.id = id;
        return this;
    }

    /**
     * @param text the text to set
     * @return DataBuilder
     */
    public DataBuilder text(String text) {
        this.text = text;
        return this;
    }

    /**
     * @param flag the flag to set
     * @return DataBuilder
     */
    public DataBuilder flag(boolean flag) {
        this.flag = flag;
        return this;
    }

    /**
     * @return Data return a Data object built from the DataBuilder
     */
    public Data build() {
        Data data = new Data();
        data.id = this.id;
        data.text = this.text;
        data.flag = this.flag;
        return data;
    }
}