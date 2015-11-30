<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" xmlns:core="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="UTF-8">
    <title>Account Verification with Servlets</title>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha256-3dkvEK0WLHRJ7/Csr0BZjAWxERc5WH7bdeUya2aXxdU= sha512-+L4yy6FRcDGbXJ9mPG8MT/3UCDzwR9gPeyFNMCtInsol++5m3bk2bXWKdZjvybmohrAsn3Ua5x8gfLnbE1YkOg==" crossorigin="anonymous">
    <link href="css/site.css" rel="stylesheet">
</head>
<body>
<section id="main" class="container">
    <nav class="navbar navbar-default">
        <a class="navbar-brand" href="/">Account Verification</a>
        <p id="nav-links" class="navbar-text pull-right">
            <a href="/registration.jsp">Sign up</a>
        </p>
    </nav>

    <h1>${userName}</h1>
    <p>Account Status: ${accountStatus}</p>
    <core:if test="${!verified}">
        <p>
            <a href="/verify-code">Verify your account now.</a>
        </p>
    </core:if>
</section><!-- /.container -->

<footer class="container">
    Made with <i class="fa fa-heart"></i> by your pals
    <a href="http://www.twilio.com">@twilio</a>
</footer>

</body>
</html>