//package pl.sklep.serwlety;
//
//import pl.sklep.DAO.DataBaseInterface;
//import pl.sklep.obiekty.Koszyk;
//import pl.sklep.obiekty.Produkt;
//import pl.sklep.obiekty.Zamowienie;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
///**
//* Created by piotr on 30.07.14.
//*/
//public class BuyServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String wybor = req.getParameter("subBuyServ");
//
//        PrintWriter pw = resp.getWriter();
//
//        if (wybor.equals("kup")) {
//            int id_zamowienia = stworzZamowienie(req, resp);
//            aktualizujKoszyk(req, resp, id_zamowienia);
//        }
////        else if (wybor.equals("wyswietl")){
////            przygotujZamowienia(req, resp);
////        }
//
//        przygotujZamowienia(req, resp);
//
//        req.getRequestDispatcher("zamowienia.jsp").forward(req,resp);
//    }
//
//    private void przygotujZamowienia(HttpServletRequest req, HttpServletResponse resp) throws IOException{
//        DataBaseInterface dbc = polaczZBaza(resp.getWriter());
//        ArrayList<Zamowienie> zamowienia = new ArrayList<Zamowienie>();
//        String login = (String) req.getSession().getAttribute("login");
//
//        String zamowieniaQuery = "SELECT \n" +
//                "  \"Zamowienie\".id_zamowienia, \n" +
//                "  \"Zamowienie\".data_zamowienia, \n" +
//                "  \"Zamowienie\".adres\n" +
//                "FROM \n" +
//                "  public.\"Zamowienie\", \n" +
//                "  public.\"Koszyk\", \n" +
//                "  public.\"user\"\n" +
//                "WHERE \n" +
//                "  \"Koszyk\".id_zamowienia = \"Zamowienie\".id_zamowienia AND\n" +
//                "  \"user\".id_usera = \"Koszyk\".id_usera AND\n" +
//                "  \"user\".login = '" + login + "'" +
//                "ORDER BY id_zamowienia ASC;";
//
//        String produktyQuery = "SELECT\n" +
//                "produkty.id_produktu,\n" +
//                "produkty.nazwa AS nazwa_produktu,\n" +
//                "produkty.cena,\n" +
//                "kategorie.nazwa AS nazwa_kategorii,\n" +
//                "\"Zamowienie\".id_zamowienia,\n" +
//                "koszyk_produkt.ilosc\n" +
//                "FROM\n" +
//                "public.\"Koszyk\",\n" +
//                "public.\"Zamowienie\",\n" +
//                "public.koszyk_produkt,\n" +
//                "public.produkty,\n" +
//                "public.kategorie,\n" +
//                "public.\"user\"\n" +
//                "WHERE\n" +
//                "\"Zamowienie\".id_zamowienia = \"Koszyk\".id_zamowienia AND\n" +
//                "koszyk_produkt.id_koszyka = \"Koszyk\".id_koszyka AND\n" +
//                "produkty.id_produktu = koszyk_produkt.id_produktu AND\n" +
//                "kategorie.id_kategorii = produkty.id_kategorii AND\n" +
//                "\"user\".id_usera = \"Koszyk\".id_usera AND\n" +
//                "\"user\".login = '"+login+"'\n" +
//                "ORDER BY id_zamowienia ASC;";
//
//        ResultSet listaZamowien = null;
//        ResultSet listaProduktow = null;
//
//        try {
//            listaZamowien = dbc.query(zamowieniaQuery);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            listaProduktow = dbc.query(produktyQuery);
//        } catch (SQLException e) {
//            resp.getWriter().println("siema eniu!!!");
//        }
//
//        Map<Integer, ArrayList<Produkt>> mapaProduktow = null;
//        try {
//            mapaProduktow = mapujListyProduktow( listaProduktow, resp);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            while(listaZamowien.next()){
//                int id_zamowienia = listaZamowien.getInt("id_zamowienia");
//                Zamowienie tym = new Zamowienie(id_zamowienia,
//                        listaZamowien.getString("data_zamowienia"),
//                        listaZamowien.getString("adres"));
//
//                tym.setKupioneProdukty(mapaProduktow.get(id_zamowienia));
//                zamowienia.add(tym);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        req.getSession().setAttribute("zamowienia", zamowienia);
//    }
//
//    private Map<Integer, ArrayList<Produkt>> mapujListyProduktow(ResultSet listaProduktow,
//                                     HttpServletResponse resp) throws SQLException {
//
//        Map<Integer, ArrayList<Produkt>> categoryToProductMap = new HashMap<Integer, ArrayList<Produkt>>();
//       while(listaProduktow.next()){
//            int id_zamowienia = listaProduktow.getInt("id_zamowienia");
//            ArrayList<Produkt> produktList = categoryToProductMap.get(id_zamowienia);
//            if (produktList == null) {
//                produktList = new ArrayList<Produkt>();
//                categoryToProductMap.put(id_zamowienia, produktList);
//            }
//
//            Produkt produkt = new Produkt(listaProduktow.getInt("id_produktu"),
//                    listaProduktow.getString("nazwa_produktu"),
//                    listaProduktow.getString("nazwa_kategorii"), listaProduktow.getInt("cena"),
//                    listaProduktow.getInt("ilosc"));
//            // Read produkt from database record here
//            //dodajProduktDoListy(produkt, produktList);
//            produktList.add(produkt);
//        }
//
//        return categoryToProductMap;
//    }
//
//    /*private void dodajProduktDoListy(Produkt prod, ArrayList<Produkt> lista){
//        int id_prod = prod.getId();
//
//        for (int i = 0; i < lista.size();i++){
//
//        }
//    }*/
//
//    private int stworzZamowienie(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException{
//        DataBaseInterface dbc = polaczZBaza(resp.getWriter());
//        String data_zamowienia = aktualnaData();
//        String adres = "ulica";
//        String zamowienieQuery = "INSERT INTO public.\"Zamowienie\" (data_zamowienia, adres)\n" +
//                "VALUES ('" + data_zamowienia + "', '" + adres + "')\n" +
//                "RETURNING id_zamowienia;";
//
//        ResultSet rs = null;
//
//        try {
//            rs = dbc.query(zamowienieQuery);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        int id_zamowienia = 0;
//        try {
//            if (rs.next()) id_zamowienia = rs.getInt("id_zamowienia");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//            return id_zamowienia;
//
//    }
//
//    private String aktualnaData(){
//        Calendar kalendarz = Calendar.getInstance();
//        return kalendarz.get(Calendar.YEAR) + "-" + (kalendarz.get(Calendar.MONTH) + 1)
//                + "-" + kalendarz.get(Calendar.DAY_OF_MONTH);
//    }
//
//    private void aktualizujKoszyk(HttpServletRequest req, HttpServletResponse resp, int id_zamowienia) throws ServletException, IOException{
//        DataBaseInterface dbc = polaczZBaza(resp.getWriter());
//        Koszyk koszyk = (Koszyk) req.getSession().getAttribute("koszyk");
//        String koszykUpdate = "UPDATE public.\"Koszyk\"\n" +
//                "SET id_zamowienia = " + id_zamowienia + "\n" +
//                "WHERE id_koszyka = "+ koszyk.getId() +";";
//
//        try {
//            dbc.update(koszykUpdate);
//            req.getSession().removeAttribute("koszyk");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private DataBaseInterface polaczZBaza(PrintWriter pw){
//        DataBaseInterface dbc = new DataBaseInterface();
////        dbc.zaladujSterownik("org.postgresql.Driver");
////        dbc.ustawAdresSerwera("jdbc:postgresql://localhost/baza_sklep");
////        dbc.zalogujUzytkownika("postgres", "Nabuchodonozor2");
//        try {
//            dbc.connect();
//        } catch (ClassNotFoundException e) {
//            pw.println("Blad z zaladowaniem sterownika");
//        } catch (SQLException e) {
//            pw.println("Nie polaczono z baza");
//        }
//
//        return dbc;
//    }
//}
