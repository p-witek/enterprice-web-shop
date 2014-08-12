<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
                <h1>Twoj koszyk</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 center ">
                <div class="btn-group">
                    <button onClick="location='kat'" type="button" class="btn btn-default">
                        <span>Glowna</span>
                    </button>
                    <button onClick="location='cart'" type="button" class="btn btn-default">
                        <span>Pokaz koszyk</span>
                    </button>
                    <button onClick="location='orders'" type="button" class="btn btn-default">
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
                <% Cart cart = (Cart) request.getAttribute("cart"); %>
                <% if (cart != null){ %>
                    <table class="table table-striped table-hover">
                    <tr>
                        <th>Kategoria produktu</th>
                        <th>Nazwa produktu</th>
                        <th>Cena za szt.</th>
                        <th>Ilosc</th>
                        <th>Cena razem</th>
                    </tr>

                    <% for (int i = 0; i < cart.getProducts().size(); i++){
                          Product product = (Product) cart.getProducts().get(i);
                          String id = product.getIdString();
                          String name = product.getName();
                          String category = product.getCategory().getName();
                          String price = product.getPriceString();
                          String amount = product.getAmountString();
                          String summaryPrice = product.getSummaryPriceString();%>
                    <tr>
                        <td><%= category %></td>
                        <td><%= name %></td>
                        <td><%= price %>zl</td>
                        <td><%="x"+ amount %></td>
                        <td><%= summaryPrice %>zl</td>
                    </tr>
                    <% } %>
                    <tr>
                        <td class="reset"></td>
                        <td class="reset"></td>
                        <td class="reset"></td>
                        <th>Suma</td>
                        <td><%= cart.getSummaryPriceString() %> zl</td>
                    </tr>
                </table>
                <div style="margin: 0 auto;" class="center">
                    <button  onClick="location='addOrder'"
                                    class="btn btn-default btn-lg" type="button">Zamow koszyk</button>
                </div>

                <%}else {%>
                    <p class="center">Nie masz jeszcze zadnych produktow w koszyku :< :< :<</p>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>