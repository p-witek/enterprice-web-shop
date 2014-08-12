package pl.sklep.serwlety;

import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.UserDAO;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.DAO.exceptions.JDBCDriverException;
import pl.sklep.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by piotr on 23.07.14.
 */
public class LoginServlet extends HttpServlet {
    private DataBaseInterface baseInterface;
    private UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        // Init model
        baseInterface = new DataBaseInterface();
        userDAO = new UserDAO(baseInterface);

        dataBaseConnect();

        User loggedUser = checkUser(req.getParameter("login"), req.getParameter("password"));
        if (loggedUser != null){
            req.getSession().setAttribute("user", loggedUser);
            resp.sendRedirect("kat");
        }
        else{
            resp.sendRedirect("form");
        }

        try {
            baseInterface.disconnect();
        } catch (DBException e) {
            System.out.println("Problem z rozlaczeniem bazy danych");
        }
    }

    private User checkUser(String username, String password) {
        try {
            User user = userDAO.getUser(username);
            if (user.getPassword().equals(password)){
                return user;
            }

        } catch (DBRecordNotFound e) {
            System.out.println("User not found!");
            e.printStackTrace();
        } catch (DBException e) {
            System.out.println("Blad przy pobieraniu danych z bazy");
            e.printStackTrace();
        }
        return null;
    }

    private void dataBaseConnect(){
        try {
            baseInterface.connect();
        } catch (JDBCDriverException e) {
            System.out.println("Problem z wczytaniem sterownika");
        } catch (DBException e) {
            System.out.println("Blad z podlaczeniem do bazy danych");
        }
    }
}
