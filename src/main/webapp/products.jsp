<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.ArrayList" %>
    <%@ page import="pl.sklep.model.User" %>
    <%@ page import="pl.sklep.model.Product" %>
    <% if (session.getAttribute("user") == null) response.sendRedirect("form"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <!-- Latest compiled and minified JavaScript -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <title></title>
</head>
<body>





    <div class="container  upMargin bottomPadding">
        <div class="row">
            <div class="col-xs-12 center">
                <% User user = (User) session.getAttribute("user");%>
                        <p>Zalogowano jako <%= user.getLogin()%></p>
                        <h1>Siema Eniu!!!</h1>
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
            <div class="col-xs-12  upMargin">
                <% ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");%>

                <button onClick="location='kat'" class="btn btn-default btn-sm">
                    <span class="glyphicon glyphicon-circle-arrow-left"></span>
                    <span>Wstecz</span>
                </button>

                <% if (products != null){ %>
                    <table class="table table-striped table-hover">
                        <tr>
                            <th>Kategoria produktu</th>
                            <th>Nazwa produktu</th>
                            <th>Cena</th>
                            <th></th>
                        </tr>

                        <%for (int i = 0; i < products.size(); i++){
                            Product product = (Product) products.get(i);
                            String id = product.getIdString();
                            String name = product.getName();
                            String category = product.getCategory().getName();
                            String price = product.getPriceString();%>

                            <tr>
                                <td><%= category %></td>
                                <td><%= name %></td>
                                <td><%= price %>zl</td>
                                <td>
                                <button class="btn btn-default btn-xs" data-toggle="modal" data-target="#cartModal<%= id %>">
                                    <span class="glyphicon glyphicon-shopping-cart"></span>
                                    Do koszyka
                                </button>

                                <!-- Modal -->
                                <div class="modal fade" id="cartModal<%= id %>" tabindex="-1" role="dialog" aria-labelledby="cartModalLabel" aria-hidden="true">
                                  <div class="modal-dialog">
                                    <div class="modal-content">
                                      <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                        <h4 class="modal-title" id="cartModalLabel">Do koszyka</h4>
                                      </div>
                                      <div class="modal-body">
                                        <table class="table">
                                            <tr>
                                                <td class="bold">Kategoria produktu:</td>
                                                <td><%= category %></td>
                                            <tr>
                                            <tr>
                                                <td class="bold">Nazwa produktu:</td>
                                                <td><%= name %></td>
                                            </tr>
                                            <tr>
                                                <td class="bold">Cena za sztuke:</td>
                                                <td><%= price %> zl</td>
                                            </tr>
                                        </table>
                                      </div>
                                      <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <form class="inline" action="addProd">
                                            <input type="hidden" name="redirect" value="<%= category %>" />
                                            <button type="submit" class="btn btn-primary" name="toCart" value="<%= id %>">
                                                Dodaj i zostan na stronie
                                            </button>
                                        </form>
                                        <form class="inline" action="addProd">
                                            <input type="hidden" name="redirect" value="cart" />
                                            <button class="btn btn-primary" type="submit" name="toCart" value="<%= id %>">
                                                Dodaj i przejdz do koszyka
                                            </button>
                                        </form>
                                      </div>
                                    </div>
                                  </div>
                                </div>

                                    <!-- <form action="addProd">
                                        <button class="btn btn-default btn-xs" type="submit" name="toCart" value="<%= id %>">
                                            <span class="glyphicon glyphicon-shopping-cart"></span>Do koszyka</button>
                                    </form> -->
                                </td>
                            </tr>
                        <%}%>
                    </table>
                <% } else { out.println("Brak produktow"); }%>
            </div>
        </div>
    </div>
</body>
</html>