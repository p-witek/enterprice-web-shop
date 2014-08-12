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
import java.util.ArrayList;
import java.util.Calendar;

/**
* Created by piotr on 30.07.14.
*/
public class BuyServlet extends HttpServlet {
    private static final String CARTS_ATTRIBUTE_NAME = "carts";

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
            }

            prepareOrdersToShow(req);
            req.getRequestDispatcher("orders.jsp").forward(req, resp);

            try {
                mDataBaseInterface.disconnect();
            } catch (DBException e) {
                System.out.println("Problem z rozlaczeniem bazy");
                e.printStackTrace();
            }
        }
        else{
            resp.sendRedirect("disc");
        }
    }

    private void prepareOrdersToShow(HttpServletRequest req){
        CartDAO cartDAO = new CartDAO(mDataBaseInterface);
        User user = (User) req.getSession().getAttribute("user");
        try {
            ArrayList<Cart> carts = cartDAO.getCartsWithOrder(user.getId_user());
            req.setAttribute(CARTS_ATTRIBUTE_NAME, carts);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
