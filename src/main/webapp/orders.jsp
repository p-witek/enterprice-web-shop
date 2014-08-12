<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.ArrayList" %>
    <%@ page import="pl.sklep.model.Cart" %>
    <%@ page import="pl.sklep.model.Product" %>
    <%@ page import="pl.sklep.model.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
    <div class="container upMargin bottomPadding">
        <div class="row">
            <div class="col-xs-12 center">
                <% User user = (User) session.getAttribute("user");%>
                    <p>Zalogowano jako <%= user.getLogin()%></p>
                <h1>Twoje zamowienia</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 center">
                <div class="btn-group">
                    <button onClick="location='kat'" type="button" class="btn btn-default">
                        <span>Glowna</span>
                    </button>
                    <button onClick="location='cart'" type="button" class="btn btn-default">
                        <span>Pokaz koszyk</span>
                    </button>
                    <button onClick="location='orders'" type="button" class="btn btn-default active">
                        <span>Historia zamowien</span>
                    </button>
                    <button onClick="location='disc'" type="button" class="btn btn-default">
                        <span>Wyloguj</span>
                    </button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 upMargin">
                <% ArrayList<Cart> carts = (ArrayList<Cart>) request.getAttribute("carts");
                %>
                <%if (carts.size() > 0){%>
                    <% for (int i = 0; i < carts.size(); i++){
                        Cart cart =  carts.get(i);
                        String id_order = cart.getOrder().getIdString();
                        String order_date = cart.getOrder().getDate();
                        String order_address = cart.getOrder().getAddress();
                        ArrayList<Product> products = cart.getProducts();
                    %>

                     <h4>#<%= id_order + " " %><%= order_date + " "%><%= order_address %></h4>

                    <table class="table table-striped table-hover">
                        <tr>
                            <th>Kategoria produktu</th>
                            <th>Nazwa produktu</th>
                            <th>Cena</th>
                            <th>Ilosc</th>
                            <th>Cena razem</th>
                        </tr>

                        <% for (int j = 0; j < products.size(); j++){ %>
                            <tr>
                                <td><%= products.get(j).getCategory().getName() %></td>
                                <td><%= products.get(j).getName() %></td>
                                <td><%= products.get(j).getPrice() %>zl</td>
                                <td><%= products.get(j).getAmount() %></td>
                                <td><%= products.get(j).getSummaryPriceString() %>zl</td>
                            </tr>
                        <%}%>

                        <tr>
                            <td class="reset"></td>
                            <td class="reset"></td>
                            <td class="reset"></td>
                            <th>Suma</th>
                            <td><%= cart.getSummaryPriceString() %> zl</td>
                        </tr>
                    </table>


                    <% } %>
                    <%}else {%>
                        <p class="center">Nie masz jeszcze zadnych zrealizowanych zamowien :< :< :<</p>
                    <% } %>
            </div>
        </div>
    </div>
</body>
</html>