<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.*" %>
    <%@ page import="pl.sklep.obiekty.Produkt" %>
    <% if (session.getAttribute("login") == null) response.sendRedirect("form"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <meta charset="UTF-8">
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <title></title>
</head>
<body>
    <div align="center">
        <% String login = (String) request.getAttribute("login");%>
        <p>Zalogowano jako <%= login%></p>
        <h1>Siema Eniu!!!</h1>

        <% HashSet<String> kategorie = (HashSet<String>) request.getAttribute("kategorie");%>
        <table>
            <% Iterator it = kategorie.iterator();
                while(it.hasNext()){
                String next =(String) it.next();%>
            <form action="kat">
                <tr>
                    <td>

                         <input type="submit" class="kat" name="kategoria" value="<%= next %>" />

                     </td>
                     <% if (it.hasNext()){
                      next =(String) it.next();%>
                     <td>

                          <input type="submit" class="kat" name="kategoria" value="<%= next %>" />


                     </td>
                     <% } %>
                </tr>
            </form>
            <% } %>
        </table>

        <table>
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
        </table>
    </div>
</body>
</html>