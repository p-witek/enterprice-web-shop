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
        <%if (zam.size() > 0){%>
            
                <% for (int i = 0; i < zam.size(); i++){
                    Zamowienie zamowienie = (Zamowienie) zam.get(i);
                    String id = zamowienie.getIdString();
                    String data = zamowienie.getData();
                    String adres = zamowienie.getAdres();
                    ArrayList<Produkt> produkty = zamowienie.getKupioneProdukty();
                %>
                
                    <h4><%= id + " " %><%= data + " "%><%= adres %></h4>
                    <p>
                        <% for (int j = 0; j < produkty.size(); j++){ %>
                            <%= produkty.get(j).getKategoria() + " "%> <%= produkty.get(j).getNazwa() + " "%>
                                        <%= produkty.get(j).getCena() %> <%="x"+ produkty.get(j).getIlosc() %>
                            <br />
                        <%}%>
                    </p>

            <% } %>
            
            <%}else {%>
            
                <p>Nie masz jeszcze zadnych zrealizowanych zamowien :< :< :<</p>
            
            <% } %>
        <a href="form">glowna</a>
        
    </div>
</body>
</html>