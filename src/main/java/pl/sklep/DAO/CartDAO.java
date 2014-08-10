package pl.sklep.DAO;

import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.model.Cart;
import pl.sklep.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by piotr on 05.08.14.
 */
public class CartDAO {
    private static final String SELECT_EMPTY_CART = "select * from public.carts " +
            "where carts.id_order is null and carts.id_user = %d;";
    private static final String INSERT_FIRST_PRODUCT = "INSERT INTO public.cart_product" +
            " (id_cart, id_product, amount) VALUES ('%d', '%d', 1);";
    private static final String SELECT_PRODUCTS_AMOUNT = "SELECT amount FROM public.cart_product" +
            " WHERE id_cart = %d  AND id_product = %d;";
    private static final String UPDATE_PRODUCTS_AMOUNT = "UPDATE public.cart_product SET amount = %d" +
            " WHERE id_cart = %d  AND id_product = %d ;";
    private static final String INSERT_NEW_CART = "INSERT INTO public.carts (id_user) VALUES (%d)" +
            "returning id_cart";

    private static final String COL_ID_CART = "id_cart";
    private static final String COL_ID_USER = "id_user";
    private static final String COL_AMOUNT = "amount";

    private DataBaseInterface mDataBaseInterface;

    public CartDAO(DataBaseInterface dataBaseInterface){
        mDataBaseInterface = dataBaseInterface;
    }

    public Cart getUsersOpenCart(int id_user) throws DBException{
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_EMPTY_CART, id_user));
            if (resultSet.next()) {
                return fromDBResult(resultSet);
            }
            throw new DBRecordNotFound();
        }catch (SQLException e){
            throw new DBException();
        }
    }

    public int createNewCart(int id_user) throws DBException{
        try {
            ResultSet resultSet =  mDataBaseInterface.query(String.format(INSERT_NEW_CART, id_user));
            if (resultSet.next()){
                return resultSet.getInt(COL_ID_CART);
            }
            throw new DBException();
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public void addProductToCart(int product_id, int cart_id) throws DBException{
        try {
            ResultSet resultSet = mDataBaseInterface.query(
                    String.format(SELECT_PRODUCTS_AMOUNT, cart_id, product_id));
            if (resultSet.next()) {
                mDataBaseInterface.update(String.format(
                        UPDATE_PRODUCTS_AMOUNT, resultSet.getInt(COL_AMOUNT) + 1,
                        cart_id, product_id));
            } else {
                mDataBaseInterface.update(
                        String.format(INSERT_FIRST_PRODUCT, cart_id, product_id));
            }
        }catch(SQLException e){
            throw new DBException();
        }
    }

    private Cart fromDBResult(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId_cart(resultSet.getInt(COL_ID_CART));
        cart.setUser(getCartOwner(resultSet.getInt(COL_ID_USER)));
        cart.setOrder(null);
        return cart;
    }

    private User getCartOwner(int id_user) throws SQLException {
        try {
            UserDAO userDAO = new UserDAO(mDataBaseInterface);
            return userDAO.getUser(id_user);
        }catch(DBException e){
            throw new SQLException();
        }

    }
}
