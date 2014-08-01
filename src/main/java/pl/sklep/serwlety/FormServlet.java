package pl.sklep.serwlety;

import pl.sklep.kontrolery.DataBaseControl;
import pl.sklep.obiekty.Produkt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Override;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by piotr on 23.07.14.
 */
public class FormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession sesja = req.getSession();
        sesja.setMaxInactiveInterval(2000);


        if (sesja.getAttribute("login") == null) {
            //req.getRequestDispatcher("formularz.html").forward(req, resp);
            req.getRequestDispatcher("formularz.jsp").forward(req,resp);
        }
        else {
            req.setAttribute("login", sesja.getAttribute("login"));
            req.setAttribute("produkty", sesja.getAttribute("produkty"));
            req.setAttribute("kategorie", sesja.getAttribute("kategorie"));

            if(req.getAttribute("produkty")== null){
                PrintWriter pw = resp.getWriter();
                pw.print("blad, brak produktow");
            }
            else{
                req.getRequestDispatcher("kategorie.jsp").forward(req, resp);
                /*PrintWriter pw = resp.getWriter();
                ArrayList<Produkt> pr = (ArrayList<Produkt>)sesja.getAttribute("produkty");
                /*for (int i = 0; i < pr.size(); i++){
                    pw.println(pr.get(i).getNazwa() + " " + pr.get(i).getCenaString());
                }*/
            }

        }
    }
}
