package db;

/**
 * Базовый класс модуля доступа DAO к объектам БД
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public abstract class DaoBase {
    protected  String driver;  // драйвер JDBC
    protected  String url;  // строка подключения
    protected  Properties  properties = null;  // свойства подключения объекта Connection

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public DaoBase(String driver) {
        this.driver = driver;
        url = DbConfig.url;
    }

    protected void RegisterDriverManager() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    public abstract void setURL (String host, String database, int port);

    public abstract Connection getConnection ();

    public void Connect (String login, String password) {
        // Регистрация драйвера
        RegisterDriverManager();

        // Определение свойств подключения Connection
        properties = new Properties();
        properties.setProperty("password", password);
        properties.setProperty("user", login);
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "utf8");
    }

    public ResultSet selectSchema(final String schema) throws SQLException{
        return null;
    }

    public ResultSet execSQL (final String sql) {
        Statement statement;
        ResultSet RS = null;
        try {
            if (getConnection() != null) {
                statement = getConnection().createStatement();
                RS = statement.executeQuery(sql);
                //statement.close();
                //statement = null;
                //result = true;
            }
        } catch (SQLException e) {
            System.err.println ("SQLException : code = " + String.valueOf(e.getErrorCode()) +
                    " - " + e.getMessage());
            System.err.println ("\tSQL : " + sql);
        }
        return RS;
    }
}