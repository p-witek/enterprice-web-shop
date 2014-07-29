package pl.sklep.serwlety;

import pl.sklep.kontrolery.DataBaseControl;
import pl.sklep.obiekty.Produkt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by piotr on 25.07.14.
 */
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("text/plain");
        //resp.getWriter().print(req.getParameter("doKoszyka"));
        DataBaseControl bazaDanych = new DataBaseControl();

        int id = Integer.parseInt(req.getParameter("doKoszyka"));
        String nazwa = req.getParameter("nazwa");
        int cena = Integer.parseInt(req.getParameter("cena"));
        String kategoria = req.getParameter("kategoria");
        HttpSession sesja = req.getSession();

        if (sesja.getAttribute("login") != null ){
            ArrayList<Produkt> koszyk = (ArrayList<Produkt>) sesja.getAttribute("koszyk");

            if (koszyk == null){
                koszyk = new ArrayList<Produkt>();
                sesja.setAttribute("koszyk", koszyk);
            }
            Produkt prod = new Produkt(id, nazwa, kategoria, cena);
            koszyk.add(prod);

            req.getRequestDispatcher("kat").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("wylogowanie").forward(req,resp);
        }
    }
}
