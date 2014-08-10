package pl.sklep.serwlety;

import pl.sklep.DAO.CategoryDAO;
import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by piotr on 29.07.14.
 */
public class KatServlet extends HttpServlet {

    private static final String CATEGORY_PAGE = "kategorie.jsp";
    private static final String CATEGORIES_ATTRIBUTE_NAME = "categories";

    private CategoryDAO categoryDAO;
    private DataBaseInterface baseInterface;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //init model

        baseInterface = new DataBaseInterface();
        categoryDAO = new CategoryDAO(baseInterface);

        try {
            baseInterface.connect();
        } catch (DBException e) {
            System.out.println("Blad z polaczeniem do bazy");
            e.printStackTrace();
        }

        prepareCategories(req);
        req.getRequestDispatcher(CATEGORY_PAGE).forward(req, resp);

        try {
            baseInterface.disconnect();
        } catch (DBException e) {
            System.out.println("Blad z rozlaczeniem bazy");
            e.printStackTrace();
        }

//        String kat = req.getParameter("kategoria");
//
//        if (req.getSession().getAttribute("login") != null) {
//            wybierzProduktyZJednejKat(kat, req, resp);
//            req.getRequestDispatcher("products.jsp").forward(req, resp);
//        }
//        else{
//            req.getRequestDispatcher("wylogowanie").forward(req,resp);
//        }
    }

    private void prepareCategories(HttpServletRequest req){
        try {
            req.setAttribute(CATEGORIES_ATTRIBUTE_NAME, categoryDAO.getAllCategories());
        }catch (DBRecordNotFound e){
            System.out.println("W bazie nie ma kategorii!");
            e.printStackTrace();
        }
        catch (DBException e) {
            System.out.println("Bład przy pobieraniu danych z bazy");
            e.printStackTrace();
        }
    }

//    private void wybierzProduktyZJednejKat(String kat, HttpServletRequest req, HttpServletResponse resp){
//        HttpSession sesja = req.getSession();
//
//        ArrayList<Produkt> produkty = (ArrayList<Produkt>) sesja.getAttribute("produkty");
//        ArrayList<Produkt> wybrane = new ArrayList<Produkt>();
//
//        for (Produkt pr : produkty){
//            if (pr.getKategoria().equals(kat)) wybrane.add(pr);
//        }
//        //sesja.setAttribute("wybrane", wybrane);
//        req.setAttribute("wybrane", wybrane);
//        req.setAttribute("login", sesja.getAttribute("login"));
//
//    }
}
