package pl.sklep.DAO;

import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.model.Cart;
import pl.sklep.model.Order;
import pl.sklep.model.Product;
import pl.sklep.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private static final String SELECT_CART_BY_ORDERS_ID = "SELECT carts.id_cart FROM public.carts" +
            " WHERE id_order IS NOT NULL AND id_user = %d;";
    private static final String SELECT_CART_BY_ID = "select * from public.carts " +
            "where carts.id_cart = %d;";

    private static final String COL_ID_CART = "id_cart";
    private static final String COL_ID_USER = "id_user";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_ID_ORDER = "id_order";

    private DataBaseInterface mDataBaseInterface;


    public CartDAO(DataBaseInterface dataBaseInterface){
        mDataBaseInterface = dataBaseInterface;
    }

    public Cart getUsersOpenCart(int id_user) throws DBException{
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_EMPTY_CART, id_user));
            if (resultSet.next()) {
                return fromDBResultOpenCart(resultSet);
            }
            return null;
        }catch (SQLException e){
            throw new DBException();
        }
    }

    public ArrayList<Cart> getCartsWithOrder(int id_user) throws DBException {
        ArrayList<Cart> carts = new ArrayList<Cart>();
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_CART_BY_ORDERS_ID,id_user));
            while (resultSet.next()){
                carts.add(getCart(resultSet.getInt(COL_ID_CART)));
            }
            return carts;
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public Cart getCart(int id_cart) throws DBException {
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_CART_BY_ID, id_cart));
            if (resultSet.next()){
                return fromDBResult(resultSet);
            }
            throw new DBRecordNotFound();
        } catch (SQLException e) {
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


    private Cart fromDBResultOpenCart(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId_cart(resultSet.getInt(COL_ID_CART));
        cart.setProducts(getCartProducts(resultSet.getInt(COL_ID_CART)));
        cart.setUser(getCartOwner(resultSet.getInt(COL_ID_USER)));
        cart.setOrder(null);
        return cart;
    }

    private Cart fromDBResult(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId_cart(resultSet.getInt(COL_ID_CART));
        cart.setProducts(getCartProducts(resultSet.getInt(COL_ID_CART)));
        cart.setUser(getCartOwner(resultSet.getInt(COL_ID_USER)));
        cart.setOrder(getOrder(resultSet.getInt(COL_ID_ORDER)));
        return cart;
    }

    private Order getOrder(int id_order) throws SQLException {
        OrderDAO orderDAO = new OrderDAO(mDataBaseInterface);
        try {
            return orderDAO.getOrder(id_order);
        } catch (DBException e) {
            throw new SQLException();
        }
    }

    private ArrayList<Product> getCartProducts(int cart_id) throws SQLException {
        try {
            ProductDAO productDAO = new ProductDAO(mDataBaseInterface);
            return productDAO.getProducts(cart_id);
        }catch (DBException e) {
            throw new SQLException();
        }
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
