package DataBase;

/**
 * Базовый класс модуля доступа DAO к объектам БД
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public abstract class dao_base {
    protected  String      driver     = null;  // драйвер JDBC
    protected  String      url        = null;  // строка подключения
    protected  Properties  properties = null;  // свойства подключения объекта Connection
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public dao_base(String driver) {
        this.driver = driver;
        url = dbConfig.url;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура регистрации драйвера JDBC
     */
    protected void RegisterDriverManager() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {e.printStackTrace();}
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура определения строки подключения URL к серверу БД
     * @param host - имя компьютера
     * @param database - наименование БД (может быть пустой строкой)
     * @param port - порт сервера
     */
    public abstract void setURL (String host, String database, int port);
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Функция получения объекта подключения
     * @return Connection - объект подключения
     */
    public abstract Connection getConnection ();
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура регистрации драйвера подключения к серверу СУБД JDBC и определения свойств
     * @param login - логин подключения
     * @param password - пароль подключения
     */
    public void Connect (String login, String password) {
        // Регистрация драйвера
        RegisterDriverManager();

        // Определение свойств подключения Connection
        properties = new Properties();
        properties.setProperty("password"         , password);
        properties.setProperty("user"             , login   );
        properties.setProperty("useUnicode"       , "true"  );
        properties.setProperty("characterEncoding", "utf8"  );
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура отключения от сервера БД
     * @param connection объект подключения
     */
    public void Disconnect (Connection connection) {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {}
    };
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Функция создания схемы
     * @param schema наименование схемы
     * @return результат транзакции
     */
    public ResultSet createSchema(final String schema) throws SQLException {
        return null;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Функция удаления схемы
     * @param schema наименование схемы
     * @return результат транзакции
     */
    public ResultSet dropSchema(final String schema) throws SQLException {
        return null;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Функция выполнения SQL-запроса
     * @param sql текст запроса
     * @return результат выполнения запроса
     */

    public ResultSet selectSchema(final String schema) throws SQLException{
        return null;
    }

    public ResultSet execSQL (final String sql) throws SQLException {
        boolean result = false;
        Statement statement = null;
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