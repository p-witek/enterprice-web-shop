package pl.sklep.serwlety;

import pl.sklep.kontrolery.DataBaseControl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by piotr on 23.07.14.
 */
public class LoginServlet extends HttpServlet {
    HttpServletRequest req;
    HttpSession sesja;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        sesja = req.getSession();
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        DataBaseControl bazaDanych = new DataBaseControl();

        bazaDanych.zaladujSterownik("org.postgresql.Driver");
        bazaDanych.ustawAdresSerwera("jdbc:postgresql://localhost/baza_sklep");
        bazaDanych.zalogujUzytkownika("postgres", "Nabuchodonozor2");
        polaczZBaza(bazaDanych, pw);
        //pw.println("polaczono z baza");
        boolean czyZalogowano = sprawdzUzytkownika(bazaDanych, pw);

        if (czyZalogowano){
            //req.getRequestDispatcher("form");
            resp.sendRedirect("form");
        }
        else{
            pw.println("Podano nieprawidlowe dane. Zaraz nastąpi przekierowanie...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        rozlaczZbaza(bazaDanych, pw);
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
    private boolean sprawdzUzytkownika(DataBaseControl dbc, PrintWriter pw){
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
                    sesja.setAttribute("login", user);
                    return true;
                }
            }

        } catch (SQLException e) {
            pw.println("Blad przy pobieraniu danych z bazy");
        }
        return false;
    }
}
