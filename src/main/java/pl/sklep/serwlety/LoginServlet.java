package pl.sklep.serwlety;

import pl.sklep.kontrolery.DataBaseControl;
import pl.sklep.obiekty.Produkt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private void przygotujKategorie(DataBaseControl dbc, HttpServletRequest req,
                                    HttpServletResponse resp) {
        ArrayList<Produkt> produkty = new ArrayList<Produkt>();
        HashSet<String> kategorie = new HashSet<String>();
        //String query = "SELECT produkty.id_produktu, produkty.nazwa, produkty.cena " +
         //       "FROM public.produkty;";

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
