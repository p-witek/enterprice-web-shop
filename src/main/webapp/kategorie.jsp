<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.*" %>
    <%@ page import="pl.sklep.model.Category" %>
    <%@ page import="pl.sklep.model.User" %>
    <% if (session.getAttribute("user") == null) response.sendRedirect("form"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <title></title>
</head>
<body>
    <div class="container upMargin bottomPadding">
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
            <div class="col-xs-12 center  upMargin">
                <% ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");%>
                <div  style="text-align: center;">
                    <table style="margin: 0 auto;">
                        <% Iterator it = categories.iterator();
                            while(it.hasNext()){
                            Category category = (Category) it.next();
                            String name = category.getName();%>

                            <tr>
                                <td style="padding: 5px 5px 5px 5px;">
                                    <form action="prod">
                                        <input type="submit" class="kat" name="enterCategory" value="<%= name %>" />
                                    </form>
                                </td>
                                 <% if (it.hasNext()){
                                  category = (Category) it.next();
                                  name = category.getName();%>
                                <td style="padding: 5px 5px 5px 5px;">
                                    <form action="prod">
                                      <input type="submit" class="kat" name="enterCategory" value="<%= name %>" />
                                    </form>
                                </td>
                                 <% } %>
                            </tr>
                        <% } %>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>