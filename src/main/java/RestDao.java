package db;

/**
 * Модуль доступа к серверу СУБД PostgreSQL
 */

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import common.Coordinates;
import common.Cost;
import domain.Restaurant;
import org.postgresql.PGConnection;
import common.Time;

public class RestDao extends DaoBase<Restaurant, String>
{
    private  PGConnection  connection = null;

    @Override
    public List getAll() {
        List<Restaurant> restaurants = new LinkedList<>();
        PreparedStatement statement = getPreparedStatement("SELECT * FROM restaurants");
        try {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                restaurants.add(constructRestaurant(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(statement);
        }

        return restaurants;
    }

    @Override
    public Restaurant getEntity(String id) {
        Restaurant restaurant = null;

        PreparedStatement statement = getPreparedStatement(String.format(
                "SELECT * FROM " +
                        "restaurants WHERE name = '%s' ", id));

        try {
            ResultSet results = statement.executeQuery();
            if(results.next()){
                restaurant = constructRestaurant(results);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(statement);
        }

        return restaurant;
    }

    @Override
    public boolean delete(String entity) {
        boolean result = false;

        PreparedStatement planetStatement = getPreparedStatement(String.format("DELETE FROM restaurants WHERE name = '%s'", entity));
        try {
            result = planetStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(planetStatement);
        }

        return result;
    }

    @Override
    public boolean create(Restaurant entity) {
        boolean result = false;
        PreparedStatement planetStatement = getPreparedStatement(String.format(
                "INSERT INTO restaurants (seatsandhours, opentime, name, lon, lat, closetime, averagecost, managerid) " +
                        "VALUES ('%s', %d, '%s', %d, %d, %d, %d, %d)", entity.seatsAndHoursMapToJson(), entity.getOpenTime_().getHours_(), entity.getName_(),
                (int)entity.getCoordinates_().getLattitude_(),
                (int)entity.getCoordinates_().getLattitude_(),
                entity.getCloseTime_().getHours_(),
                entity.getAverageCost_().getBigValue_(),
                // entity.getRestaurantManager().getId(),
                1));
        try {
            result = planetStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(planetStatement);
        }

        return result;
    }


    public boolean exists(String name) throws SQLException {
        Restaurant restaurant = getEntity(name);
        if (restaurant != null)
            return true;
        else
            return false;
    }


    public int count() throws SQLException {
        PreparedStatement statement = getPreparedStatement(String.format(
                "SELECT count(*) AS exact_count FROM restaurants;"));
        int result = 0;

        try {
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                result = results.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(statement);
        }

        return result;
    }

    private Restaurant constructRestaurant(ResultSet results) throws SQLException {
        Restaurant restaurant = Restaurant.newBuilder().setOpenTime_(new Time(results.getInt(2)))
                .setName_(results.getString(3))
                .setCoordinates_(new Coordinates(results.getInt(4), results.getInt(5)))
                .setCloseTime_(new Time(results.getInt(6)))
                .setAverageCost_(new Cost(results.getInt(7)))
                .setId_(results.getInt(9))
                .build();
        return restaurant;
    }
}