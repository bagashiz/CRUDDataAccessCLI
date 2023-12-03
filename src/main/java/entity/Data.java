package entity;

/**
 * Data class represents a data in a file.
 */
public class Data {
    protected int id;
    protected String text;
    protected boolean flag;

    /**
     * @return DataBuilder return a DataBuilder object to build Data object
     */
    public static DataBuilder builder() {
        return new DataBuilder();
    }

    /**
     * @return int return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return String return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return boolean return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", text='" + getText() + "'" +
                ", flag='" + isFlag() + "'" +
                "}";
    }
}