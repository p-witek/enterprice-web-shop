<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.ArrayList" %>
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
        <% String login = (String) request.getAttribute("login");%>
        <p>Zalogowano jako <%= login%></p>
        <h1>Siema Eniu!!!</h1>

        <% ArrayList<Produkt> prod = (ArrayList<Produkt>) request.getAttribute("wybrane");%>
        <table>
            <% if (prod != null) for (int i = 0; i < prod.size(); i++){
                  Produkt produkt = (Produkt) prod.get(i);
                  String id = produkt.getIdString();
                  String nazwa = produkt.getNazwa();
                  String kategoria = produkt.getKategoria();
                  String cena = produkt.getCenaString();%>
            <tr>
                <td><%= kategoria %></td>
                <td><%=nazwa %></td>
                <td><%= cena %></td>
                <td>
                    <form action="cart">
                        <input type="hidden" name="nazwa" value="<%= nazwa%>" />
                        <input type="hidden" name="cena" value="<%= cena%>" />
                        <input type="hidden" name="kategoria" value="<%= kategoria%>" />
                        <button type="submit" name="doKoszyka" value="<%= id%>">Do koszyka</button>
                    </form>
                </td>
            </tr>
            <% } else { out.println("Brak produktow"); }%>
            <tr>
                <td><a href="form">Wstecz</a></td>
            </tr>
        </table>

        <!--<table>
            <tr>
                <td>
                    <form action="wylogowanie">
                        <button type="submit" name="wyloguj">Wyloguj</button>
                    </form>
                </td>
                <td>
                    <form action="koszyk.jsp">
                        <button type="submit" name="pokazKosz">Pokaz Koszyk</button>
                    </form>
                </td>
            </tr>
        </table>-->
    </div>
</body>
</html>