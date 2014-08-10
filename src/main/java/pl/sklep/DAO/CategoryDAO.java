package pl.sklep.DAO;

import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by piotr on 04.08.14.
 */
public class CategoryDAO {
    private static final String SELECT_BY_NAME = "select * from public.categories where name = '%s'";
    private static final String SELECT_BY_ID = "select * from public.categories where id_category =%d";
    private static final String SELECT_ALL_CATEGORIES = "select * from public.categories";

    private static final String COL_ID_CATEGORY = "id_category";
    private static final String COL_NAME = "name";
    private DataBaseInterface mDataBaseInterface;

    public CategoryDAO(DataBaseInterface dataBaseInterface){
        this.mDataBaseInterface = dataBaseInterface;
    }

    public Category getCategory(String name) throws DBException {
        try {
            ResultSet resultSet = mDataBaseInterface.query(String.format(SELECT_BY_NAME, name));
            if(resultSet.next()){
                return fromDBResult(resultSet);
            }
            throw new DBRecordNotFound();
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    public Category getCategory(int id) throws DBException {
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

    public ArrayList<Category> getAllCategories() throws DBException{
        ArrayList<Category> allCategories = new ArrayList<Category>();
        try {
            ResultSet resultSet = mDataBaseInterface.query(SELECT_ALL_CATEGORIES);
            while(resultSet.next()){
                allCategories.add(fromDBResult(resultSet));
            }
            if (allCategories.isEmpty()){
                throw new DBRecordNotFound();
            }
            return allCategories;
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    private Category fromDBResult(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId_category(resultSet.getInt(COL_ID_CATEGORY));
        category.setName(resultSet.getString(COL_NAME));
        return category;
    }
}
