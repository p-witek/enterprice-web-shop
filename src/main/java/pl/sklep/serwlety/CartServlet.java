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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by piotr on 25.07.14.
 */
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        //resp.getWriter().print(req.getParameter("doKoszyka"));
        DataBaseControl bazaDanych = new DataBaseControl();
        polaczZBaza(bazaDanych, resp.getWriter());


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
                stworzNowyKoszyk( bazaDanych, req, resp);
                sesja.setAttribute("id_koszyka", znajdzIdKoszyka(bazaDanych, req));

//                PrintWriter pw = resp.getWriter();
//                pw.print("siema eniu");
            }


            int id_koszyka = (Integer) sesja.getAttribute("id_koszyka");
            String query = "INSERT INTO public.koszyk_produkt (id_koszyka, id_produktu)" +
                    "VALUES ('" + id_koszyka + "', '" + id + "');";

            Produkt prod = new Produkt(id, nazwa, kategoria, cena);
            koszyk.add(prod);
            try {
                bazaDanych.dodajRekord(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            req.getRequestDispatcher("kat").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("wylogowanie").forward(req,resp);
        }
    }

    private void stworzNowyKoszyk(DataBaseControl dbc, HttpServletRequest req, HttpServletResponse resp) {
        int id_usera = znajdzIdUsera(dbc, req, resp);

        String queryDodajKoszyk = "INSERT INTO public.\"Koszyk\" (id_usera) VALUES (" + id_usera + ")";

        try {
            dbc.dodajRekord(queryDodajKoszyk);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int znajdzIdKoszyka(DataBaseControl dbc, HttpServletRequest req){
        String login = (String) req.getSession().getAttribute("login");
        String id_koszyka_query = "SELECT \n" +
                "  \"Koszyk\".id_koszyka\n" +
                "FROM \n" +
                "  public.\"Koszyk\", \n" +
                "  public.\"user\"\n" +
                "WHERE \n" +
                "  \"Koszyk\".id_usera = \"user\".id_usera\n" +
                "  AND \"user\".login = '" + login +"';";

        ResultSet wynik = null;

        try {
            wynik = dbc.zapytanie(id_koszyka_query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (wynik.next()) {
                return wynik.getInt("id_koszyka");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private int znajdzIdUsera(DataBaseControl dbc, HttpServletRequest req, HttpServletResponse resp){
        String login = (String) req.getSession().getAttribute("login");
        String queryIdUsera = "SELECT \n" +
                "  \"user\".id_usera\n" +
                "FROM \n" +
                "  public.\"user\"\n" +
                "WHERE\n" +
                "  \"user\".login = '" + login + "';";

        ResultSet wynik = null;

        try {
            wynik = dbc.zapytanie(queryIdUsera);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (wynik.next()) return wynik.getInt("id_usera");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void polaczZBaza(DataBaseControl dbc, PrintWriter pw){
        dbc.zaladujSterownik("org.postgresql.Driver");
        dbc.ustawAdresSerwera("jdbc:postgresql://localhost/baza_sklep");
        dbc.zalogujUzytkownika("postgres", "Nabuchodonozor2");
        try {
            dbc.polacz();
        } catch (ClassNotFoundException e) {
            pw.println("Blad z zaladowaniem sterownika");
        } catch (SQLException e) {
            pw.println("Nie polaczono z baza");
        }

    }
}
