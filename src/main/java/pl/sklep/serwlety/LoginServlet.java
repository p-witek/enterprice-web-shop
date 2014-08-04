package pl.sklep.serwlety;

import pl.sklep.kontrolery.DataBaseControl;
import pl.sklep.obiekty.Koszyk;
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
import java.util.*;

/**
 * Created by piotr on 23.07.14.
 */
public class LoginServlet extends HttpServlet {
    HttpServletRequest req;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        //HttpSession sesja = req.getSession();
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        DataBaseControl bazaDanych = new DataBaseControl();

        bazaDanych.zaladujSterownik("org.postgresql.Driver");
        bazaDanych.ustawAdresSerwera("jdbc:postgresql://localhost/baza_sklep");
        bazaDanych.zalogujUzytkownika("postgres", "Nabuchodonozor2");
        polaczZBaza(bazaDanych, pw);
        //pw.println("polaczono z baza");
        boolean czyZalogowano = sprawdzUzytkownika(bazaDanych, pw, req);

        if (czyZalogowano){
            //req.getRequestDispatcher("form");
            //req.getSession().invalidate();

            przygotujKategorie(bazaDanych, req, resp);

            int idKoszyka = znajdzIdKoszyka(bazaDanych, req, resp);
            if (idKoszyka != -1) {
                przygotujKoszyk(bazaDanych, req, resp, idKoszyka);
            }

            resp.sendRedirect("form");
        }
        else{
            pw.println("Podano nieprawidlowe dane. Zaraz nastąpi przekierowanie...");
            try {
                Thread.sleep(2000);
                resp.sendRedirect("form");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        rozlaczZbaza(bazaDanych, pw);
    }



    private int znajdzIdKoszyka(DataBaseControl dbc, HttpServletRequest req,
                                HttpServletResponse resp) {
        String login = (String) req.getSession().getAttribute("login");

        String query = "SELECT \n" +
                "  id_koszyka \n" +
                "FROM \n" +
                "  public.\"user\", \n" +
                "  public.\"Koszyk\"\n" +
                "WHERE \n" +
                "  \"user\".id_usera = \"Koszyk\".id_usera AND\n" +
                "  \"user\".login = '" + login + "' AND\n" +
                "  \"Koszyk\".id_zamowienia is NULL\n";

        ResultSet wynik = null;

        try {
            wynik = dbc.zapytanie(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int idKoszyka = -1;
        //boolean czyIstniejeKosz = false;
        try {
            //czyIstniejeKosz = wynik.next();
            if (wynik.next()){
                idKoszyka = wynik.getInt("id_koszyka");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idKoszyka;
    }

    private void przygotujKoszyk(DataBaseControl dbc, HttpServletRequest req,
                                    HttpServletResponse resp, int idKoszyka) {
        Koszyk koszyk = new Koszyk();
        koszyk.setId(idKoszyka);
        String login = req.getParameter("login");
        String query = "SELECT \n" +
                "  produkty.nazwa as nazwa_produktu, \n" +
                "  produkty.cena, \n" +
                "  kategorie.nazwa as nazwa_kat,\n" +
                "  produkty.id_produktu,\n" +
                "  koszyk_produkt.ilosc\n" +
                "FROM \n" +
                "  public.produkty, \n" +
                "  public.koszyk_produkt, \n" +
                "  public.\"Koszyk\", \n" +
                "  public.\"user\", \n" +
                "  public.kategorie\n" +
                "WHERE \n" +
                "  \"user\".login = '" + login + "' AND" +
                "  koszyk_produkt.id_koszyka = \"Koszyk\".id_koszyka AND\n" +
                "  koszyk_produkt.id_produktu = produkty.id_produktu AND\n" +
                "  \"user\".id_usera = \"Koszyk\".id_usera AND\n" +
                "  kategorie.id_kategorii = produkty.id_kategorii AND" +
                "  koszyk_produkt.id_koszyka = " + koszyk.getId() + ";";

        ResultSet wynik = null;

        try {
            wynik = dbc.zapytanie(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while(wynik.next()){
                koszyk.add(new Produkt(wynik.getInt("id_produktu"), wynik.getString("nazwa_produktu"),
                        wynik.getString("nazwa_kat"), wynik.getInt("cena"),wynik.getInt("ilosc")));
            }
        } catch (SQLException e) {

            try {
                PrintWriter pw = resp.getWriter();
                pw.print("blad z dodaniem do koszyka");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        req.getSession().setAttribute("koszyk", koszyk);
    }


    private void przygotujKategorie(DataBaseControl dbc, HttpServletRequest req,
                                    HttpServletResponse resp) {
        ArrayList<Produkt> produkty = new ArrayList<Produkt>();
        HashSet<String> kategorie = new HashSet<String>();
        //String query = "SELECT produkty.id_produktu, produkty.nazwa, produkty.cena " +
         //       "FROM public.produkty;";
        //String login = req.getParameter("login");
//        String id_koszyka_query = "SELECT \n" +
//                "  \"Koszyk\".id_koszyka\n" +
//                "FROM \n" +
//                "  public.\"Koszyk\", \n" +
//                "  public.\"user\"\n" +
//                "WHERE \n" +
//                "  \"Koszyk\".id_usera = \"user\".id_usera\n" +
//                "  AND \"user\".login = '" + login +"';";

        String query2 = "SELECT \n" +
                "  kategorie.nazwa as nazwa_kategorii, \n" +
                "  produkty.id_produktu, \n" +
                "  produkty.nazwa as nazwa_produktu, \n" +
                "  produkty.cena\n" +
                "FROM \n" +
                "  public.kategorie, \n" +
                "  public.produkty\n" +
                "WHERE \n" +
                "  kategorie.id_kategorii = produkty.id_kategorii;\n";

        ResultSet wynik = null;
        try {
//            wynik = dbc.zapytanie(id_koszyka_query);
//            if (wynik.next()){
//                req.getSession().setAttribute("id_koszyka", wynik.getInt("id_koszyka"));
//            }

            wynik = dbc.zapytanie(query2);
        } catch (SQLException e) {
            try {
                resp.getWriter().println("nie udalo sie wykonac zapytania");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            while(wynik.next()) {
                String kat = wynik.getString("nazwa_kategorii");
                produkty.add(new Produkt(wynik.getInt("id_produktu"),
                        wynik.getString("nazwa_produktu"), kat,
                        wynik.getInt("cena")));
                kategorie.add(kat);

            }
        } catch (SQLException e) {
            try {
                resp.getWriter().println("blad z dodawaniem uzytkownika");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        req.getSession().setAttribute("produkty", produkty);
        req.getSession().setAttribute("kategorie", kategorie);
    }

    private void polaczZBaza(DataBaseControl dbc, PrintWriter pw){
        try {
            dbc.polacz();
        } catch (ClassNotFoundException e) {
            pw.println("Blad z zaladowaniem sterownika");
        } catch (SQLException e) {
            pw.println("Nie polaczono z baza");
        }
    }
    private void rozlaczZbaza(DataBaseControl dbc, PrintWriter pw){
        try {
            dbc.rozlacz();
        } catch (SQLException e) {
            pw.println("Blad z zamknieciem bazy");
        }
    }
    private boolean sprawdzUzytkownika(DataBaseControl dbc, PrintWriter pw, HttpServletRequest req){
        //pobranie uzytkownikow z bazy
        ResultSet wynik = null;

        try {

            wynik = dbc.zapytanie("SELECT \"user\".login," +
                    "\"user\".password FROM public.\"user\";");
        } catch (SQLException e) {
            pw.println("Blad zapytania");
        }
        //sprawdzanie czy istnieje uzytkownik do zalogowania
        String login = req.getParameter("login");
        String haslo = req.getParameter("haslo");
        try {
            while(wynik.next()){
                String user = wynik.getString("login");
                String password = wynik.getString("password");
                if (user.equals(login) && password.equals(haslo)){
                    req.getSession().setAttribute("login", user);
                    return true;
                }
            }
        } catch (SQLException e) {
            pw.println("Blad przy pobieraniu danych z bazy");
        }
        return false;
    }
}
