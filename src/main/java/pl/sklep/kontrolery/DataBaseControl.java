package pl.sklep.kontrolery;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by piotr on 24.07.14.
 */
public class DataBaseControl {
    private String sterownikJDBC;
    private String adresSerwera;

    private String uzytkownik;
    private String haslo;

    private Connection polaczenie;
    private Statement st;
    private ResultSet wynikZapytania;

    public void zaladujSterownik(String s){
        sterownikJDBC = s;

    }

    public void zalogujUzytkownika(String u, String h){
        uzytkownik = u;
        haslo = h;
    }

    public void ustawAdresSerwera(String a){
        adresSerwera = a;
    }

    public void polacz()  throws ClassNotFoundException, SQLException {
        Class.forName(sterownikJDBC);
        polaczenie = DriverManager.getConnection(adresSerwera, uzytkownik, haslo);
    }
    public void rozlacz() throws SQLException {
        polaczenie.close();
    }

    public ResultSet zapytanie(String query) throws SQLException {
        st = polaczenie.createStatement();
        return st.executeQuery(query);
    }

    public void aktualizujRekord(String query) throws SQLException {
        st = polaczenie.createStatement();
        st.executeUpdate(query);
    }
}
