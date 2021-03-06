package pl.sklep.serwlety;

import pl.sklep.DAO.CategoryDAO;
import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by piotr on 29.07.14.
 */
public class KatServlet extends HttpServlet {

    private static final String CATEGORY_PAGE = "kategorie.jsp";
    private static final String CATEGORIES_ATTRIBUTE_NAME = "categories";

    private CategoryDAO categoryDAO;
    private DataBaseInterface baseInterface;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //init model
        baseInterface = new DataBaseInterface();
        categoryDAO = new CategoryDAO(baseInterface);

        if (req.getSession().getAttribute("user") != null) {
            try {
                baseInterface.connect();
            } catch (DBException e) {
                System.out.println("Blad z polaczeniem do bazy");
                e.printStackTrace();
            }

            prepareCategories(req);
            req.getRequestDispatcher(CATEGORY_PAGE).forward(req, resp);

            try {
                baseInterface.disconnect();
            } catch (DBException e) {
                System.out.println("Blad z rozlaczeniem bazy");
                e.printStackTrace();
            }
        }
        else{
            resp.sendRedirect("disc");
        }
    }

    private void prepareCategories(HttpServletRequest req){
        try {
            req.setAttribute(CATEGORIES_ATTRIBUTE_NAME, categoryDAO.getAllCategories());
        }catch (DBRecordNotFound e){
            System.out.println("W bazie nie ma kategorii!");
            e.printStackTrace();
        }
        catch (DBException e) {
            System.out.println("Bład przy pobieraniu danych z bazy");
            e.printStackTrace();
        }
    }
}
