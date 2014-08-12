package pl.sklep.DAO;

import pl.sklep.Config;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.JDBCDriverException;

import java.sql.*;

/**
 * To jest klasa, ktora umozliwia interakcje z baza danych.
 *
 * Created by piotr on 24.07.14.
 */
public class DataBaseInterface {

    private Connection polaczenie;

    /** Do odpalienia przy inicjalizacji serwletu */
    public void connect() throws DBException{
        try {
            Class.forName(Config.JDBC_DRIVER);
            polaczenie = DriverManager.getConnection(
                    Config.JDBC_CONNECTION_STRING,
                    Config.JDBC_DB_USER,
                    Config.JDBC_DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new JDBCDriverException();
        } catch (SQLException e){
            throw new DBException();
        }
    }

    /** Do odpalenia przy wylaczaniu serwletu */
    public void disconnect() throws DBException {
        try {
            polaczenie.close();
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    /** Do odpalenia w obiektach DAO */
    ResultSet query(String query) throws SQLException {
        Statement st = polaczenie.createStatement();
        return st.executeQuery(query);
    }

    /** Do odpalenia w obiektach DAO */
    void update(String query) throws SQLException {
        Statement st = polaczenie.createStatement();
        st.executeUpdate(query);
    }
}
