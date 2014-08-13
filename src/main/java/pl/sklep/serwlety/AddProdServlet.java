package pl.sklep.serwlety;

import pl.sklep.DAO.CartDAO;
import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.model.Cart;
import pl.sklep.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by piotr on 12.08.14.
 */
public class AddProdServlet extends HttpServlet {
    private static final String ID_PARAMETER_NAME = "toCart";
    private static final String REDIRECT_PARAM_NAME = "redirect";

    private DataBaseInterface mDataBaseInterface;
    private CartDAO cartDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            addProductToCart(req);

            if (req.getParameter(REDIRECT_PARAM_NAME).equals("cart")) {
                resp.sendRedirect("cart");
            }
            else{
                String returningPage = "prod" + "?enterCategory="
                        + req.getParameter(REDIRECT_PARAM_NAME);
                resp.sendRedirect(returningPage);
            }

            try {
                mDataBaseInterface.disconnect();
            } catch (DBException e) {
                System.out.println("Problem z rozlaczeniem bazy.");
                e.printStackTrace();
            }
        }
        else {
            resp.sendRedirect("disc");
        }
    }

    private void addProductToCart(HttpServletRequest req){
        User loggedUser = (User) req.getSession().getAttribute("user");
        try {
            Cart openCart = cartDAO.getUsersOpenCart(loggedUser.getId_user());
            if (openCart == null){
                try {
                    int idOpenCart = cartDAO.createNewCart(loggedUser.getId_user());
                    int product_id = Integer.parseInt(req.getParameter(ID_PARAMETER_NAME));
                    cartDAO.addProductToCart(product_id, idOpenCart);
                } catch (DBException e1) {
                    System.out.println("Problem z baza danych");
                    e1.printStackTrace();
                }
            }
            else {
                int product_id = Integer.parseInt(req.getParameter(ID_PARAMETER_NAME));
                cartDAO.addProductToCart(product_id, openCart.getId_cart());
            }
        }
        catch (DBException e) {
            System.out.println("Blad przy pobieraniu danych z bazy");
            e.printStackTrace();
        }
    }
}
