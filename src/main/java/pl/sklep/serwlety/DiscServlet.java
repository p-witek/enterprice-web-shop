package pl.sklep.serwlety;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by piotr on 24.07.14.
 */
public class DiscServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        req.getSession().invalidate();
        PrintWriter pw = resp.getWriter();
        //pw.println("Wylogowano :<. Zaraz nastąpi przekierowanie.");
        //try {
            //Thread.sleep(2000);
        //} catch (InterruptedException e) {
          //  pw.println("problem z watkiem");
        //}
        //req.getRequestDispatcher("formularz.html").forward(req, resp);
        resp.sendRedirect("formularz.jsp");
    }
}
