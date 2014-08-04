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
            Koszyk koszyk = (Koszyk) sesja.getAttribute("koszyk");

            if (koszyk == null){
                koszyk = new Koszyk();
                stworzNowyKoszyk( bazaDanych, req, resp);
                koszyk.setId(znajdzIdKoszyka(bazaDanych, req));
                sesja.setAttribute("koszyk", koszyk);
            }

//            String query = "INSERT INTO public.koszyk_produkt (id_koszyka, id_produktu, ilosc)" +
//                    "VALUES ('" + id_koszyka + "', '" + id + "', 1);";

            Produkt prod = null;
            for (int i = 0; i < koszyk.size();i++){
                if (id == koszyk.get(i).getId()){
                    prod = koszyk.get(i);
                }
            }
            if (prod == null) prod = new Produkt(id, nazwa, kategoria, cena);

            try {
                aktualizujKoszyk(bazaDanych, koszyk, prod, resp.getWriter());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //resp.getWriter().print("ilosc: " + prod.getIlosc());


            req.getRequestDispatcher("kat").forward(req, resp);
        }
        else{
            req.getRequestDispatcher("wylogowanie").forward(req,resp);
        }
    }


    private void aktualizujKoszyk(DataBaseControl dbc ,Koszyk koszyk, Produkt prod, PrintWriter pw) throws SQLException{
        String dodajPierwszyProdukt = "INSERT INTO public.koszyk_produkt (id_koszyka, id_produktu, ilosc)" +
                "VALUES ('" + koszyk.getId() + "', '" + prod.getId() + "', 1);";

        String wyszukajProduktu = "SELECT \n" +
                "  koszyk_produkt.id_koszyka, \n" +
                "  koszyk_produkt.id_produktu\n" +
                "FROM \n" +
                "  public.koszyk_produkt\n" +
                "  WHERE\n" +
                "  id_koszyka = " + koszyk.getId() + " AND\n" +
                "  id_produktu = " + prod.getId() + ";";

        int iloscDanegoProduktu = iloscDanegoProduktu(dbc, prod.getId(), koszyk.getId(), pw) + 1;

       // pw.print(iloscDanegoProduktu);

        String aktualizujIlosc= "UPDATE public.koszyk_produkt\n" +
                "SET ilosc = "+ iloscDanegoProduktu +"\n" +
                "WHERE id_koszyka = " + koszyk.getId() + " AND id_produktu = " + prod.getId() + ";";

        ResultSet wyszukaneProdukty = dbc.zapytanie(wyszukajProduktu);

        if(wyszukaneProdukty.next()){
            dbc.aktualizujRekord(aktualizujIlosc);
            prod.setIlosc(iloscDanegoProduktu);
        }
        else{
            dbc.aktualizujRekord(dodajPierwszyProdukt);
            koszyk.add(prod);
        }

    }

//    private void zwiekszIloscProduktu(int id, Koszyk koszyk){
//        for (int i = 0; i < koszyk.size(); i++){
//            if (id == koszyk.get(i).getId()){
//                koszyk.get(i).zwiekszIlosc();
//            }
//        }
//    }

    private int iloscDanegoProduktu(DataBaseControl dbc, int id, int id_koszyka, PrintWriter pw){
        String iloscProduktowQuery = "SELECT \n" +
                "  ilosc\n" +
                "FROM \n" +
                "  public.koszyk_produkt \n" +
                "WHERE \n" +
                "  id_koszyka = " + id_koszyka + " AND\n" +
                "  id_produktu = " + id + ";";

        ResultSet wynik = null;

        try {
            wynik = dbc.zapytanie(iloscProduktowQuery);
        } catch (SQLException e) {
            //pw.print("siema eniu!!!");
        }

        int ilosc = 0;
        try {
            if (wynik.next()){
                ilosc = wynik.getInt("ilosc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ilosc;
    }

    private void stworzNowyKoszyk(DataBaseControl dbc, HttpServletRequest req, HttpServletResponse resp) {
        int id_usera = znajdzIdUsera(dbc, req, resp);

        String queryDodajKoszyk = "INSERT INTO public.\"Koszyk\" (id_usera) VALUES (" + id_usera + ")";

        try {
            dbc.aktualizujRekord(queryDodajKoszyk);
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
                "  AND \"user\".login = '" + login +"' AND" +
                "\"Koszyk\".id_zamowienia IS NULL;";

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
