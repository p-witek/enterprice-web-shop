package pl.sklep.obiekty;

/**
 * Created by piotr on 25.07.14.
 */
public class Produkt {
    private int id;
    private String nazwa;
    private int cena;
    private String kategoria;
    private int ilosc;

    public Produkt(){

    }

    public Produkt(int id, String nazwa, String kategoria, int cena){
        this.id = id;
        this.kategoria = kategoria;
        this.nazwa = nazwa;
        this.cena = cena;
        ilosc = 1;
    }
    public Produkt(int id, String nazwa, String kategoria, int cena, int ilosc){
        this.id = id;
        this.kategoria = kategoria;
        this.nazwa = nazwa;
        this.cena = cena;
        this.ilosc = ilosc;
    }


    public String getNazwa(){
        return nazwa;
    }

    public int getCena(){
        return cena;
    }

    public String getCenaString(){
        return Integer.toString(cena);
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId() {
        return id;
    }

    public String getIdString(){
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setIlosc(int ilosc){
        this.ilosc = ilosc;
    }

    public int getIlosc() {
        return ilosc;
    }

    public String getIloscString(){
        return Integer.toString(ilosc);
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }
}
