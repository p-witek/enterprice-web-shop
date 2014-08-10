<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.ArrayList" %>
    <%@ page import="pl.sklep.model.User" %>
    <%@ page import="pl.sklep.model.Product" %>
    <% if (session.getAttribute("user") == null) response.sendRedirect("form"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
    <div align="center">
        <% User user = (User) session.getAttribute("user");%>
                <p>Zalogowano jako <%= user.getLogin()%></p>
                <h1>Siema Eniu!!!</h1>

        <% ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");%>
        <table>
            <% if (products != null) for (int i = 0; i < products.size(); i++){
                  Product product = (Product) products.get(i);
                  String id = product.getIdString();
                  String name = product.getName();
                  String category = product.getCategory().getName();
                  String price = product.getPriceString();%>
            <tr>
                <td><%= category %></td>
                <td><%= name %></td>
                <td><%= price %></td>
                <td>
                    <form action="cart">
                        <button type="submit" name="toCart" value="<%= id %>">Do koszyka</button>
                    </form>
                </td>
            </tr>
            <% } else { out.println("Brak produktow"); }%>
            <tr>
                <td><a href="kat">Wstecz</a></td>
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