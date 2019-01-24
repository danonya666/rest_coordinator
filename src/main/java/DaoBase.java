package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class DaoBase<E,K> {

    private Connection connection;

    DaoBase() {
        try {
            connection = ConnectionPool.getConnection();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public abstract List<E> getAll();
    public abstract E getEntity(K id);
    public abstract boolean delete(K entity);
    public abstract boolean create(E entity);
    public abstract int count() throws SQLException;
    public abstract boolean exists(String id) throws SQLException;

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) { e.printStackTrace(); }

        return statement;
    }

    void closePreparedStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}