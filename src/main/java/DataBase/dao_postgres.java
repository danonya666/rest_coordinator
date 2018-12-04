package DataBase;

/**
 * Модуль доступа к серверу СУБД PostgreSQL
 */

import java.sql.*;

import Domain.Restaraunt;
import org.postgresql.PGConnection;
import org.postgresql.core.SqlCommand;

public class dao_postgres extends dao_base
{
    private  PGConnection  connection = null;
    private  final  String  SCHEMA_CREATE = "CREATE SCHEMA \"%s\"";
    private  final  String  DROP_SCHEMA   = "DROP SCHEMA \"%s\""  ;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public dao_postgres() {
        super ("org.postgresql.Driver");
    };
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void setURL (String host, String database, int port) {
        if (database.length() > 0)
            this.url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        else
            this.url = "jdbc:postgresql://" + host + ":" + port;
    };
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public Connection getConnection () {
        return (java.sql.Connection) connection;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void Connect (String login, String password) {
        super.Connect(login, password);
        try {
            connection = (PGConnection) DriverManager.getConnection(url, properties);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            connection = null;
            System.out.println("Connection unsuccessful");
        }
    };
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public ResultSet createSchema(final String schema) throws SQLException {
        return execSQL (String.format(SCHEMA_CREATE, schema));
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public ResultSet dropSchema(final String schema) throws SQLException {
        return execSQL (String.format(DROP_SCHEMA, schema));
    }

    @Override
    public ResultSet selectSchema(final String schema) throws SQLException{
        return this.execSQL(schema);
    }

    public String selectToString(ResultSet RS) throws SQLException {
        String result = "";
        int columns = RS.getMetaData().getColumnCount();
        while(RS.next()){
            for (int i = 1; i <= columns; i++){
                result += (RS.getString(i) + "\t");
            }
            result += "\n";
        }
        return result;
    }

    public String select(final String query) throws SQLException {
        return selectToString(selectSchema(query));
    }

    public void insert(Restaraunt restaraunt) throws SQLException{
        String query = "INSERT INTO restaurants(seatsandhours, opentime, name, lon, lat, closetime, averagecost, managerid)\n" +
                "VALUES(";
        query += "\'" + restaraunt.seastsAndHoursMapToJson() + "\'";
        query += ", ";
        query += restaraunt.getOpenTime_().getHours_() + ", ";
        query += "\'" + restaraunt.getName_() + "\'" + ", " ;
        query += restaraunt.getCoordinates_().getLongtitude_() + ", ";
        query += restaraunt.getCoordinates_().getLattitude_() + ", ";
        //query += restaraunt.getId_() + ", ";
        query += restaraunt.getCloseTime_().getHours_() + ", ";
        query += restaraunt.getAverageCost_().getBigValue_() + ", ";
        //query += restaraunt.getRestaurantManager().getId() + ", ";
        query += "1";
        query += ")";
        this.execSQL(query);
        System.out.println(query);
    }

    public void delete(String name) throws SQLException{
        name = name.substring(0, name.length() - 2);
        String query = "DELETE FROM restaurants WHERE name = " + "\'" + name + "\'";
        try {
            execSQL(query);
        }
        catch (java.lang.StringIndexOutOfBoundsException e){

        }
    }

    public int count() throws SQLException {
        String query = "SELECT count(*) AS exact_count FROM restaurants;";
        ResultSet rs = execSQL(query);
        String result = selectToString(rs);
        result = result.substring(0, result.length() -2);
        int res = Integer.parseInt(result);
        return res;
    }
}