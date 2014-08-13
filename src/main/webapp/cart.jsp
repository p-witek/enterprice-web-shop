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
        <!-- Latest compiled and minified JavaScript -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
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
                    <button onClick="location='cart'" type="button" class="btn btn-default active">
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
                <!-- <div style="margin: 0 auto;" class="center">
                    <button  onClick="location='addOrder'"
                                    class="btn btn-default btn-lg" type="button">Zamow koszyk</button>
                </div> -->

                <div style="margin: 0 auto;" class="center">
                    <button class="btn btn-default btn-lg" data-toggle="modal" data-target="#orderModal">
                        Zamow koszyk
                    </button>
                </div>

                <!-- Modal -->
                <div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="orderModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="orderModalLabel">Do koszyka</h4>
                      </div>
                      <form action="addOrder">
                          <div class="modal-body">
                            <label for="addressInput">Podaj adres zamowienia </label>
                            <input class="form-control" id="addressInput" type="text" name="address" />
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                Anuluj
                            </button>
                            <button class="btn btn-primary" type="submit">
                                Wyslij zamowienie
                            </button>

                          </div>
                      </form>
                    </div>
                  </div>
                </div>


                <%}else {%>
                    <p class="center">Nie masz jeszcze zadnych produktow w koszyku :< :< :<</p>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>