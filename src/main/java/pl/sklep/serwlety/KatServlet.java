package pl.sklep.serwlety;

import pl.sklep.obiekty.Produkt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by piotr on 29.07.14.
 */
public class KatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String kat = req.getParameter("kategoria");

        if (req.getSession().getAttribute("login") != null) {
            wybierzProduktyZJednejKat(kat, req, resp);
            req.getRequestDispatcher("glowna.jsp").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("wylogowanie").forward(req,resp);
        }
    }

    private void wybierzProduktyZJednejKat(String kat, HttpServletRequest req, HttpServletResponse resp){
        HttpSession sesja = req.getSession();

        ArrayList<Produkt> produkty = (ArrayList<Produkt>) sesja.getAttribute("produkty");
        ArrayList<Produkt> wybrane = new ArrayList<Produkt>();

        for (Produkt pr : produkty){
            if (pr.getKategoria().equals(kat)) wybrane.add(pr);
        }
        //sesja.setAttribute("wybrane", wybrane);
        req.setAttribute("wybrane", wybrane);
        req.setAttribute("login", sesja.getAttribute("login"));

    }
}
