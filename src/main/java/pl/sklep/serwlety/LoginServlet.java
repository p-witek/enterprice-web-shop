package pl.sklep.serwlety;

import pl.sklep.DAO.DataBaseInterface;
import pl.sklep.DAO.UserDAO;
import pl.sklep.DAO.exceptions.DBException;
import pl.sklep.DAO.exceptions.DBRecordNotFound;
import pl.sklep.DAO.exceptions.JDBCDriverException;
import pl.sklep.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by piotr on 23.07.14.
 */
public class LoginServlet extends HttpServlet {
    private DataBaseInterface baseInterface;
    private UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        // Init model
        baseInterface = new DataBaseInterface();
        userDAO = new UserDAO(baseInterface);
//        categoryDAO = new CategoryDAO(baseInterface);
        dataBaseConnect();

        User loggedUser = checkUser(req.getParameter("login"), req.getParameter("password"));
        if (loggedUser != null){
            req.getSession().setAttribute("user", loggedUser);
            //prepareCategories(req);
            //req.setAttribute("login", loggedUser.getLogin());
            req.getRequestDispatcher("kat").forward(req, resp);

            //przygotujKategorie(baseInterface, req, resp);

            //String login = (String) req.getSession().getAttribute("login");
            //Koszyk koszyk = new Koszyk();
            //koszyk.przygotuj(baseInterface, login);

//            if (koszyk.czyIstnieje()){
//                req.getSession().setAttribute("koszyk", koszyk);
//            }
////            int idKoszyka = znajdzIdKoszyka(bazaDanych, req, resp);
////            if (idKoszyka != -1) {
////                przygotujKoszyk(bazaDanych, req, resp, idKoszyka);
////            }
        }
        else{
            resp.sendRedirect("form");
        }

        try {
            baseInterface.disconnect();
        } catch (DBException e) {
            System.out.println("Problem z rozlaczeniem bazy danych");
        }
    }

//    private void prepareCategories(HttpServletRequest req){
//        try {
//            req.setAttribute("categories", categoryDAO.getAllCategories());
//        }catch (DBRecordNotFound e){
//            System.out.println("W bazie nie ma kategorii!");
//            e.printStackTrace();
//        }
//        catch (DBException e) {
//            System.out.println("Bład przy pobieraniu danych z bazy");
//            e.printStackTrace();
//        }
//    }

    private User checkUser(String username, String password) {
        try {
            User user = userDAO.getUser(username);
            if (user.getPassword().equals(password)){
                return user;
            }

        } catch (DBRecordNotFound e) {
            System.out.println("User not found!");
            e.printStackTrace();
        } catch (DBException e) {
            System.out.println("Blad przy pobieraniu danych z bazy");
            e.printStackTrace();
        }
        return null;
    }

    private void dataBaseConnect(){
        try {
            baseInterface.connect();
        } catch (JDBCDriverException e) {
            System.out.println("Problem z wczytaniem sterownika");
        } catch (DBException e) {
            System.out.println("Blad z podlaczeniem do bazy danych");
        }
    }

//    private int znajdzIdKoszyka(DataBaseControl dbc, HttpServletRequest req,
//                                HttpServletResponse resp) {
//        String login = (String) req.getSession().getAttribute("login");
//
//        String query = "SELECT \n" +
//                "  id_koszyka \n" +
//                "FROM \n" +
//                "  public.\"user\", \n" +
//                "  public.\"Koszyk\"\n" +
//                "WHERE \n" +
//                "  \"user\".id_usera = \"Koszyk\".id_usera AND\n" +
//                "  \"user\".login = '" + login + "' AND\n" +
//                "  \"Koszyk\".id_zamowienia is NULL\n";
//
//        ResultSet wynik = null;
//
//        try {
//            wynik = dbc.query(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        int idKoszyka = -1;
//        //boolean czyIstniejeKosz = false;
//        try {
//            //czyIstniejeKosz = wynik.next();
//            if (wynik.next()){
//                idKoszyka = wynik.getInt("id_koszyka");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return idKoszyka;
//    }

//    private void przygotujKoszyk(DataBaseControl dbc, HttpServletRequest req,
//                                    HttpServletResponse resp) {
//        Koszyk koszyk = new Koszyk();
//        znajdzIdKoszyka(dbc, req, resp);
//        //koszyk.setId(idKoszyka);
//        String login = req.getParameter("login");
//        String query = "SELECT \n" +
//                "  produkty.nazwa as nazwa_produktu, \n" +
//                "  produkty.cena, \n" +
//                "  kategorie.nazwa as nazwa_kat,\n" +
//                "  produkty.id_produktu,\n" +
//                "  koszyk_produkt.ilosc\n" +
//                "FROM \n" +
//                "  public.produkty, \n" +
//                "  public.koszyk_produkt, \n" +
//                "  public.\"Koszyk\", \n" +
//                "  public.\"user\", \n" +
//                "  public.kategorie\n" +
//                "WHERE \n" +
//                "  \"user\".login = '" + login + "' AND" +
//                "  koszyk_produkt.id_koszyka = \"Koszyk\".id_koszyka AND\n" +
//                "  koszyk_produkt.id_produktu = produkty.id_produktu AND\n" +
//                "  \"user\".id_usera = \"Koszyk\".id_usera AND\n" +
//                "  kategorie.id_kategorii = produkty.id_kategorii AND" +
//                "  koszyk_produkt.id_koszyka = " + koszyk.getId() + ";";
//
//        ResultSet wynik = null;
//
//        try {
//            wynik = dbc.query(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            while(wynik.next()){
//                koszyk.add(new Produkt(wynik.getInt("id_produktu"), wynik.getString("nazwa_produktu"),
//                        wynik.getString("nazwa_kat"), wynik.getInt("cena"),wynik.getInt("ilosc")));
//            }
//        } catch (SQLException e) {
//
//            try {
//                PrintWriter pw = resp.getWriter();
//                pw.print("blad z dodaniem do koszyka");
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//
//        }
//
//        req.getSession().setAttribute("koszyk", koszyk);
//    }


//    private void przygotujKategorie(DataBaseInterface dbc, HttpServletRequest req,
//                                    HttpServletResponse resp) {
//        ArrayList<Produkt> produkty = new ArrayList<Produkt>();
//        HashSet<String> kategorie = new HashSet<String>();
//        //String query = "SELECT produkty.id_produktu, produkty.nazwa, produkty.cena " +
//         //       "FROM public.produkty;";
//        //String login = req.getParameter("login");
////        String id_koszyka_query = "SELECT \n" +
////                "  \"Koszyk\".id_koszyka\n" +
////                "FROM \n" +
////                "  public.\"Koszyk\", \n" +
////                "  public.\"user\"\n" +
////                "WHERE \n" +
////                "  \"Koszyk\".id_usera = \"user\".id_usera\n" +
////                "  AND \"user\".login = '" + login +"';";
//
//        String query2 = "SELECT \n" +
//                "  kategorie.nazwa as nazwa_kategorii, \n" +
//                "  produkty.id_produktu, \n" +
//                "  produkty.nazwa as nazwa_produktu, \n" +
//                "  produkty.cena\n" +
//                "FROM \n" +
//                "  public.kategorie, \n" +
//                "  public.produkty\n" +
//                "WHERE \n" +
//                "  kategorie.id_kategorii = produkty.id_kategorii;\n";
//
//        ResultSet wynik = null;
//        try {
////            wynik = dbc.query(id_koszyka_query);
////            if (wynik.next()){
////                req.getSession().setAttribute("id_koszyka", wynik.getInt("id_koszyka"));
////            }
//
//            wynik = dbc.query(query2);
//        } catch (SQLException e) {
//            try {
//                resp.getWriter().println("nie udalo sie wykonac zapytania");
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        try {
//            while(wynik.next()) {
//                String kat = wynik.getString("nazwa_kategorii");
//                produkty.add(new Produkt(wynik.getInt("id_produktu"),
//                        wynik.getString("nazwa_produktu"), kat,
//                        wynik.getInt("cena")));
//                kategorie.add(kat);
//
//            }
//        } catch (SQLException e) {
//            try {
//                resp.getWriter().println("blad z dodawaniem uzytkownika");
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        req.getSession().setAttribute("produkty", produkty);
//        req.getSession().setAttribute("kategorie", kategorie);
//    }

//    private void polaczZBaza(DataBaseInterface dbc, PrintWriter pw){
//        try {
//            dbc.connect();
//        } catch (ClassNotFoundException e) {
//            pw.println("Blad z zaladowaniem sterownika");
//        } catch (SQLException e) {
//            pw.println("Nie polaczono z baza");
//        }
//    }
//    private void rozlaczZbaza(DataBaseInterface dbc, PrintWriter pw){
//        try {
//            dbc.disconnect();
//        } catch (SQLException e) {
//            pw.println("Blad z zamknieciem bazy");
//        }
//    }
}
