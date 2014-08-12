package pl.sklep.DAO;

import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBFailUserAuthorization;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object - tutaj bedzie cala interakcja z baza danych
 * Created by piotr on 04.08.14.
 */
public class UserDAO {

    private static final String SELECT_BY_NAME = "select * from public.\"users\" where login='%s'";
    private static final String SELECT_BY_ID = "select * from public.\"users\" where id_user=%d";

    private static final String COL_ID_USER = "id_user";
    private static final String COL_LOGIN = "login";
    private static final String COL_PASSWORD = "password";

    private DataBaseInterface mDataBaseInterface;

    public UserDAO(DataBaseInterface dataBaseInterface) {
        mDataBaseInterface = dataBaseInterface;
    }

    public User getUser(long userId) throws DBException {
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_BY_ID, userId));
            if (resultSet.next()) {
                return fromDBResult(resultSet);
            }
            throw new DBRecordNotFound();
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public User getUser(String userName) throws DBException {
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_BY_NAME, userName));
            if (resultSet.next()) {
                return fromDBResult(resultSet);
            }
            throw new DBRecordNotFound();
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    private User fromDBResult(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId_user(resultSet.getInt(COL_ID_USER));
        user.setLogin(resultSet.getString(COL_LOGIN));
        user.setPassword(resultSet.getString(COL_PASSWORD));
        return user;
    }
}
