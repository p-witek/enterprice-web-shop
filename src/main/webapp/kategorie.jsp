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
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <title></title>
</head>
<body>
    <div align="center">
        <% User user = (User) session.getAttribute("user");%>
        <p>Zalogowano jako <%= user.getLogin()%></p>
        <h1>Siema Eniu!!!</h1>

        <% ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");%>
        <table>
            <% Iterator it = categories.iterator();
                while(it.hasNext()){
                Category category = (Category) it.next();
                String name = category.getName();%>

                <tr>
                    <td>
                        <form action="prod">
                            <input type="submit" class="kat" name="enterCategory" value="<%= name %>" />
                        </form>
                     </td>
                     <% if (it.hasNext()){
                      category = (Category) it.next();
                      name = category.getName();%>
                     <td>
                        <form action="prod">
                          <input type="submit" class="kat" name="enterCategory" value="<%= name %>" />
                        </form>

                     </td>
                     <% } %>
                </tr>

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
                    <form action="cart">
                        <button type="submit" name="showCart">Pokaz Koszyk</button>
                    </form>
                </td>
                <td>
                    <form action="zamowienia">
                        <button type="submit" name="showOrders" value="showOrders">Historia zamowien</button>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>