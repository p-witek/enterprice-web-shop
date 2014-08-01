<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.ArrayList" %>
    <%@ page import="pl.sklep.obiekty.Zamowienie" %>
    <%@ page import="pl.sklep.obiekty.Produkt" %>

    <% if (session.getAttribute("login") == null) response.sendRedirect("form"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
    <div align="center">
        <h1>Twoje zamowienia</h1>

        <% ArrayList<Zamowienie> zam = (ArrayList<Zamowienie>) session.getAttribute("zamowienia");
        %>
        <table>
            <%
            if (zam.size() > 0){ for (int i = 0; i < zam.size(); i++){
                  Zamowienie zamowienie = (Zamowienie) zam.get(i);
                  String id = zamowienie.getIdString();
                  String data = zamowienie.getData();
                  String adres = zamowienie.getAdres();
                  ArrayList<Produkt> produkty = zamowienie.getKupioneProdukty();
            %>
            <tr>
                <td><%= id %></td>
                <td><%= data %></td>
                <td><%= adres %></td>
            </tr>
            <tr>
                <table>
                    <% for (int j = 0; j < produkty.size(); j++){ %>
                        <tr>
                            <td><%= produkty.get(j).getKategoria() %></td>
                            <td><%= produkty.get(j).getNazwa() %></td>
                            <td><%= produkty.get(j).getCena() %></td>
                        </tr>
                    <% } %>
                </table>
            </tr>

            <% } %>
            <!-- <tr>
                <td>
                    <form action="zamowienia">
                        <button type="submit" name="subBuyServ" value="wyswietl">Kup</button>
                    </form>
                </td>
            </tr> -->
            <%}else {%>
            <tr>
                <td>Nie masz jeszcze zadnych zrealizowanych zamowien :< :< :< </td>
            </tr>
            <% } %>
        </table>
        <a href="form">glowna</a>
    </div>
</body>
</html>