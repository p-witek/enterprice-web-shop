<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="pl.sklep.model.Cart" %>
    <%@ page import="pl.sklep.model.Product" %>

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

        <% Cart cart = (Cart) request.getAttribute("cart"); %>
        <table>
            <%
            if (cart != null){ for (int i = 0; i < cart.getProducts().size(); i++){
                  Product product = (Product) cart.getProducts().get(i);
                  String id = product.getIdString();
                  String name = product.getName();
                  String category = product.getCategory().getName();
                  String price = product.getPriceString();
                  String amount = product.getAmountString();%>
            <tr>
                <td><%= category %></td>
                <td><%= name %></td>
                <td><%= price %></td>
                <td><%="x"+ amount%></td>
            </tr>

            <% } %>
            <tr>
                <td>
                    <form action="zamowienia">
                        <button type="submit" name="buy" value="buy">Kup</button>
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