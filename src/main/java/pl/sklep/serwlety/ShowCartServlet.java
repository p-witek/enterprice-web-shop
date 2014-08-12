package pl.sklep.serwlety;

import pl.sklep.DAO.CartDAO;
import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.ProductDAO;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.model.Cart;
import pl.sklep.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* Created by piotr on 25.07.14.
*/
public class ShowCartServlet extends HttpServlet {
    private static final String CART_PARAMETER_NAME = "cart";

    private DataBaseInterface mDataBaseInterface;
    private CartDAO cartDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        //init DataBase model
        mDataBaseInterface = new DataBaseInterface();
        cartDAO = new CartDAO(mDataBaseInterface);

        if (req.getSession().getAttribute("user") != null) {
            try {
                mDataBaseInterface.connect();
            } catch (DBException e) {
                System.out.println("Problem z podlaczeniem do bazy.");
                e.printStackTrace();
            }

            prepareCartProducts(req);
            req.getRequestDispatcher("cart.jsp").forward(req, resp);

            try {
                mDataBaseInterface.disconnect();
            } catch (DBException e) {
                System.out.println("Problem z rozlaczeniem bazy.");
                e.printStackTrace();
            }
        }else{
            resp.sendRedirect("disc");
        }
    }

    private void prepareCartProducts(HttpServletRequest req){
        User loggedUser = (User) req.getSession().getAttribute("user");
        try {
            req.setAttribute(CART_PARAMETER_NAME, cartDAO.getUsersOpenCart(loggedUser.getId_user()));
        }
        catch (DBException e) {
            System.out.println("Blad z baza danych");
            e.printStackTrace();
        }
    }
}
