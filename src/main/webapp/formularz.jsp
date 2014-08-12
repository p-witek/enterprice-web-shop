<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<% session.invalidate(); %>
<!DOCTYPE html>
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
    <div class="container">
        <div class="row upMargin">
            <div class="col-xs-4 col-xs-offset-4">
                <h3 class="bold center">Podaj login i haslo.</h3>
                <form role="form" action="logowanie" method="post">
                    <div class="form-group">
                        <label for="loginInput">Login</label>
                        <input id="loginInput" class="form-control" type="text" name="login" />
                    </div>
                    <div class="form-group">
                        <label for="loginInput">Haslo</label>
                        <input id="passwordInput" class="form-control" type="password" name="password" />
                    </div>
                    <div class="center">
                        <button class="btn btn-default block-center" type="submit" name="send" value="send">Wyslij</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>