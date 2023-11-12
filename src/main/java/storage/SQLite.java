package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLite is a class for connecting to an SQLite database.
 */
public class SQLite {
    private Statement stmt;

    public SQLite() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.sqlite");
        this.stmt = conn.createStatement();
        stmt.setQueryTimeout(30);
    }

    public Statement getStatement() {
        return stmt;
    }

    public void close() throws SQLException {
        stmt.close();
    }
}
