package pl.sklep.serwlety;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Override;

/**
 * Created by piotr on 23.07.14.
 */
public class FormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession sesja = req.getSession();
        sesja.setMaxInactiveInterval(20);


        if (sesja.getAttribute("login") == null) {
            //req.getRequestDispatcher("formularz.html").forward(req, resp);
            resp.sendRedirect("formularz.html");
        }
        else {
            //req.getRequestDispatcher("powitanie.html").forward(req, resp);
            resp.sendRedirect("powitanie.html");
        }
    }
}
