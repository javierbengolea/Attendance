package util;

import java.sql.*;

public class MySQLAdapter {

    static String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String MYSQL_URL = "jdbc:mysql://localhost:3307/rrhh";
    static String MYSQL_USER = "root";
    static String MYSQL_PASS = "Admin_2024";

    private Connection connection;
    private Statement statement;
    public int Debug = 0;
    public ResultSet results;
    public boolean found;

    public MySQLAdapter() {
        this(MYSQL_URL, MYSQL_DRIVER, MYSQL_USER, MYSQL_PASS);
    }

    public MySQLAdapter(String user, String password) {
        this(MYSQL_URL, MYSQL_DRIVER, user, password);
    }

    public MySQLAdapter(String url, String driverName, String user, String password) {
        try {
            Class.forName(driverName);
            if (Debug != 0) {
                System.out.println("Opening connection to the database");
            }
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (ClassNotFoundException ex) {
            System.err.println("Cannot find database driver classes.");
            System.err.println(ex);
        } catch (SQLException ex) {
            System.err.println("Cannot connect to the database.");
            System.err.println(ex);
        }
    }

    public ResultSet executeQuery(String query) {
        if (connection == null || statement == null) {
            System.err.println("Database or statement is null.");
            return null;
        }
        try {
            if (Debug != 0) {
                System.out.println("Executing query: " + query);
            }
            results = statement.executeQuery(query);
            if (results.next()) {
                found = true;
            }
            results.beforeFirst();
        } catch (SQLException ex) {
            System.err.println(ex);
            return null;
        }
        return results;
    }

    public int executeUpdate(String sql) throws SQLException {
        if (connection == null || statement == null) {
            System.err.println("No database to execute update.");
            return 0;
        }
        try {
            if (Debug != 0) {
                System.out.println("Executing update: " + sql);
            }
        } catch (Exception ex) {
            // ex.printStackTrace();
        }

        int count = statement.executeUpdate(sql);
        return count;
    }

    public void close() throws SQLException {
        if (Debug != 0) {
            System.out.println("Closing database connection.");
        }
        if (results != null) {
            results.close();
        }
        statement.close();
        connection.close();
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public static void main(String[] args) {
        MySQLAdapter adapter = new MySQLAdapter();
        try {
            adapter.executeQuery("SELECT * from marcaciones_dic_2023;");

            while (adapter.results.next()) {
                for (int i = 0; i < adapter.results.getMetaData().getColumnCount(); i++) {
                    System.out.println(adapter.results.getMetaData().getColumnName(i + 1)
                            + " : " + adapter.results.getString(i + 1));
                }
                System.out.println("");
            }

            ResultSetMetaData meta = adapter.results.getMetaData();
            int cols = meta.getColumnCount();

            for (int i = 0; i < cols; i++) {
                System.out.println(meta.getColumnTypeName(i + 1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
