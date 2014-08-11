package pl.sklep.DAO;

import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.model.Category;
import pl.sklep.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by piotr on 04.08.14.
 */
public class ProductDAO {

    private static final String SELECT_BY_ID = "select * from public.products where id_product = %d";
    private static final String SELECT_PRODUCTS_BY_CATEGORY = "select products.* from public.products, public.categories\n" +
            "where products.id_category = categories.id_category AND categories.name = '%s'";
    private static final String SELECT_PRODUCTS_FROM_CART = "SELECT products.name, products.id_product, \n" +
            "  products.price, products.id_category, cart_product.amount FROM public.cart_product, \n" +
            "  public.products, public.categories WHERE cart_product.id_product = products.id_product AND\n" +
            "  products.id_category = categories.id_category AND cart_product.id_cart = %d;";

    private static final String COL_ID_PRODUCT = "id_product";
    private static final String COL_NAME_PRODUCT = "name";
    private static final String COL_ID_CATEGORY = "id_category";
    private static final String COL_PRICE_PRODUCT = "price";
    private static final String COL_AMOUNT_PRODUCT = "amount";


    private DataBaseInterface mDataBaseInterface;

    public ProductDAO(DataBaseInterface dataBaseInterface){
        mDataBaseInterface = dataBaseInterface;
    }

    public Product getProduct(int id) throws DBException{
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_BY_ID, id));
            if (resultSet.next()){
                return fromDBResult(resultSet);
            }
            throw new DBRecordNotFound();
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public ArrayList<Product> getProducts(int cartId) throws DBException{
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_PRODUCTS_FROM_CART, cartId));
            while (resultSet.next()){
                products.add(fromDBResultWithAmount(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public ArrayList<Product> getProducts(String categoryName) throws DBException{
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_PRODUCTS_BY_CATEGORY,categoryName));
            while (resultSet.next()){
                products.add(fromDBResult(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    private Product fromDBResult(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt(COL_ID_PRODUCT));
        product.setName(resultSet.getString(COL_NAME_PRODUCT));
        product.setPrice(resultSet.getInt(COL_PRICE_PRODUCT));
        product.setCategory(getProductCategory(resultSet.getInt(COL_ID_CATEGORY)));
        return product;
    }

    private Product fromDBResultWithAmount(ResultSet resultSet) throws SQLException {
        Product product = fromDBResult(resultSet);
        product.setAmount(resultSet.getInt(COL_AMOUNT_PRODUCT));
        return product;
    }

    private Category getProductCategory(int id) throws SQLException {
        try {
            CategoryDAO categoryDAO = new CategoryDAO(mDataBaseInterface);
            return categoryDAO.getCategory(id);
        }catch(DBException e){
            throw new SQLException();
        }
    }

}
