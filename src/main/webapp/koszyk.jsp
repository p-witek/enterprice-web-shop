<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="pl.sklep.obiekty.Koszyk" %>
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
        <h1>Twoj koszyk</h1>

        <% Koszyk kosz = (Koszyk) session.getAttribute("koszyk");


        %>
        <table>
            <%
            if (kosz != null){ for (int i = 0; i < kosz.size(); i++){
                  Produkt produkt = (Produkt) kosz.get(i);
                  String id = produkt.getIdString();
                  String nazwa = produkt.getNazwa();
                  String kategoria = produkt.getKategoria();
                  String cena = produkt.getCenaString();
                  String ilosc = produkt.getIloscString();%>
            <tr>
                <td><%= kategoria %></td>
                <td><%=nazwa %></td>
                <td><%= cena %></td>
                <td><%="x"+ ilosc%></td>
            </tr>

            <% } %>
            <tr>
                <td>
                    <form action="zamowienia">
                        <button type="submit" name="subBuyServ" value="kup">Kup</button>
                    </form>
                </td>
            </tr>
            <%}else {%>
            <tr>
                <td>Nie masz jeszcze zadnych produktow w koszyku :< :< :< </td>
            </tr>
            <% } %>
        </table>
        <a href="form">glowna</a>
    </div>
</body>
</html>