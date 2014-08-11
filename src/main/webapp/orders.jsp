<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.ArrayList" %>
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
        <h1>Twoje zamowienia</h1>

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
                
                    <h4><%= id_order + " " %><%= order_date + " "%><%= order_address %></h4>
                    <p>
                        <% for (int j = 0; j < products.size(); j++){ %>
                            <%= products.get(j).getCategory().getName() + " "%> <%= products.get(j).getName() + " "%>
                                        <%= products.get(j).getPrice() %> <%="x"+ products.get(j).getAmount() %>
                            <br />
                        <%}%>
                    </p>

            <% } %>
            
            <%}else {%>
            
                <p>Nie masz jeszcze zadnych zrealizowanych zamowien :< :< :<</p>
            
            <% } %>
        <a href="form">glowna</a>
        
    </div>
</body>
</html>