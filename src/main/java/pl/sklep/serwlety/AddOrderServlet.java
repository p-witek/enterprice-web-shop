package pl.sklep.serwlety;

import pl.sklep.DAO.CartDAO;
import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.OrderDAO;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.model.Cart;
import pl.sklep.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by piotr on 12.08.14.
 */
public class AddOrderServlet extends HttpServlet {
    private static final String ADDRESS_PARAM_NAME = "address";

    DataBaseInterface mDataBaseInterface;
    OrderDAO orderDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //init database model
        mDataBaseInterface = new DataBaseInterface();
        orderDAO = new OrderDAO(mDataBaseInterface);

        if (req.getSession().getAttribute("user") != null) {
            try {
                mDataBaseInterface.connect();
            } catch (DBException e) {
                System.out.println("Problem z podlaczeniem do bazy");
                e.printStackTrace();
                return;
            }

            try {
                User loggedUser = (User) req.getSession().getAttribute("user");
                CartDAO cartDAO = new CartDAO(mDataBaseInterface);
                Cart cart = cartDAO.getUsersOpenCart(loggedUser.getId_user());
                orderDAO.createNewOrder(cart.getId_cart(), getCurrentDate(), req.getParameter(ADDRESS_PARAM_NAME));
            } catch (DBException e) {
                e.printStackTrace();
            }
            req.getRequestDispatcher("kat").forward(req, resp);

            try {
                mDataBaseInterface.disconnect();
            } catch (DBException e) {
                System.out.println("Problem z rozlaczeniem bazy");
                e.printStackTrace();
            }
        }
        else {
            resp.sendRedirect("disc");
        }
    }

    private String getCurrentDate(){
        Calendar kalendarz = Calendar.getInstance();
        return kalendarz.get(Calendar.YEAR) + "-" + (kalendarz.get(Calendar.MONTH) + 1)
                + "-" + kalendarz.get(Calendar.DAY_OF_MONTH);
    }
}
