<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<% session.invalidate(); %>
<!DOCTYPE html>
<html>
<head lang="en">
    <link rel="Stylesheet" type="text/css" href="css/style.css" />
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
    <div align="center">
        <p>Podaj login i haslo.</p>
        <form action="logowanie" method="post">
            <table>
                <tr>
                    <td>Login</td>
                    <td><input type="text" name="login" /></td>
                </tr>
                <tr>
                    <td>Haslo</td>
                    <td><input type="password" name="haslo" /></td>
                </tr>
            </table>
                <button type="submit" name="wyslij" value="wyslij">Wyslij</button>
                <!--<button type="submit" name="rejestracja" value="rejestracja">Rejestracja</button>-->
        </form>
    </div>
</body>
</html>