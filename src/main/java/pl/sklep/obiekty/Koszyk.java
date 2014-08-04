package pl.sklep.obiekty;

import pl.sklep.kontrolery.DataBaseControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by piotr on 01.08.14.
 */
public class Koszyk extends ArrayList<Produkt> {
    private int id;

    public Koszyk (){
        id = -1;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean czyIstnieje(){
        if (id == -1) return false;
        else return true;
    }

    private void pobierzId(DataBaseControl dbc, String login){
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


        //boolean czyIstniejeKosz = false;
        try {
            //czyIstniejeKosz = wynik.next();
            if (wynik.next()){
                id = wynik.getInt("id_koszyka");
            }
            else{
                id = -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void przygotuj(DataBaseControl dbc, String login){
        pobierzId(dbc, login);
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
                "  koszyk_produkt.id_koszyka = " + id + ";";

        ResultSet wynik = null;
        if (id != -1) {
            try {
                wynik = dbc.zapytanie(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                while (wynik.next()) {
                    add(new Produkt(wynik.getInt("id_produktu"), wynik.getString("nazwa_produktu"),
                            wynik.getString("nazwa_kat"), wynik.getInt("cena"), wynik.getInt("ilosc")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
