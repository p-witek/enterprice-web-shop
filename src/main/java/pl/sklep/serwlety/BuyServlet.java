package pl.sklep.serwlety;

import pl.sklep.kontrolery.DataBaseControl;
import pl.sklep.obiekty.Zamowienie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by piotr on 30.07.14.
 */
public class BuyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String wybor = req.getParameter("subBuyServ");

        if (wybor.equals("kup")) {
            int id_zamowienia = stworzZamowienie(req, resp);
            aktualizujKoszyk(req, resp, id_zamowienia);
        }


    }

    private void przygotujZamowienia(){
        ArrayList<Zamowienie> zamowienia = new ArrayList<Zamowienie>();

        String zamowieniaQuery = "";
    }
    private int stworzZamowienie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        DataBaseControl dbc = polaczZBaza(resp.getWriter());
        String data_zamowienia = aktualnaData();
        String adres = "ulica";
        String zamowienieQuery = "INSERT INTO public.\"Zamowienie\" (data_zamowienia, adres)\n" +
                "VALUES ('" + data_zamowienia + "', '" + adres + "')\n" +
                "RETURNING id_zamowienia;";

        ResultSet rs = null;

        try {
            rs = dbc.zapytanie(zamowienieQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int id_zamowienia = 0;
        try {
            if (rs.next()) id_zamowienia = rs.getInt("id_zamowienia");
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return id_zamowienia;

    }

    private String aktualnaData(){
        Calendar kalendarz = Calendar.getInstance();
        return kalendarz.get(Calendar.YEAR) + "-" + (kalendarz.get(Calendar.MONTH) + 1)
                + "-" + kalendarz.get(Calendar.DAY_OF_MONTH);
    }

    private void aktualizujKoszyk(HttpServletRequest req, HttpServletResponse resp, int id_zamowienia) throws ServletException, IOException{
        DataBaseControl dbc = polaczZBaza(resp.getWriter());
        String koszykUpdate = "UPDATE public.\"Koszyk\"\n" +
                "SET id_zamowienia = " + id_zamowienia + "\n" +
                "WHERE id_koszyka = "+ req.getSession().getAttribute("id_koszyka")+";";

        try {
            dbc.dodajRekord(koszykUpdate);
            req.getSession().removeAttribute("id_koszyka");
            req.getSession().removeAttribute("koszyk");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DataBaseControl polaczZBaza(PrintWriter pw){
        DataBaseControl dbc = new DataBaseControl();
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

        return dbc;
    }
}
