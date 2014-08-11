package pl.sklep.serwlety;

import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.ProductDAO;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by piotr on 08.08.14.
 */
public class ProdServlet extends HttpServlet {
    private static final String NAME_SELECT_CAT_PARAM = "enterCategory";
    private static final String PRODUCTS_PAGE = "products.jsp";
    private static final String PRODUCTS_ATTRIBUTE_NAME = "products";

    private DataBaseInterface mDataBaseInterface;
    private ProductDAO productDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //init DataBase model
        mDataBaseInterface = new DataBaseInterface();
        productDAO = new ProductDAO(mDataBaseInterface);

        try {
            mDataBaseInterface.connect();
        } catch (DBException e) {
            System.out.println("Problem z podlaczeniem do bazy");
            e.printStackTrace();
        }

        prepareProducts(req);
        req.getRequestDispatcher(PRODUCTS_PAGE).forward(req, resp);

        try {
            mDataBaseInterface.disconnect();
        } catch (DBException e) {
            System.out.println("Problem z rozlaczeniem bazy");
            e.printStackTrace();
        }
    }

    private void prepareProducts(HttpServletRequest req){
        try {
            req.setAttribute(PRODUCTS_ATTRIBUTE_NAME, productDAO.getProducts(req.getParameter(NAME_SELECT_CAT_PARAM)));
        }catch (DBException e) {
            System.out.println("Problem z zapytaniem do bazy");
            e.printStackTrace();
        }


    }
}
