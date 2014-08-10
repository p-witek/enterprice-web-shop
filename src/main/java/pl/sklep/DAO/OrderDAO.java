package pl.sklep.DAO;

import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by piotr on 05.08.14.
 */
public class OrderDAO {
    private DataBaseInterface mDataBaseInterface;

    private static final String SELECT_BY_ID = "select * from public.orders where id_order = %d";
    private static final String COL_ID_ORDER = "id_order";
    private static final String COL_ADDRESS = "address";
    private static final String COL_DATE = "date";

    public OrderDAO(DataBaseInterface dataBaseInterface){
        this.mDataBaseInterface = dataBaseInterface;
    }

    public Order getOrder(int id) throws DBException{
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_BY_ID, id));
            if (resultSet.next()) {
                return fromDBResult(resultSet);
            }
            throw new DBRecordNotFound();
        }catch(SQLException e){
            throw new DBException();
        }
    }

    private Order fromDBResult(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt(COL_ID_ORDER));
        order.setAddress(resultSet.getString(COL_ADDRESS));
        order.setDate(resultSet.getString(COL_DATE));
        return order;
    }
}
