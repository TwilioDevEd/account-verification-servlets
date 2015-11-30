<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account Verification with Servlets</title>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha256-3dkvEK0WLHRJ7/Csr0BZjAWxERc5WH7bdeUya2aXxdU= sha512-+L4yy6FRcDGbXJ9mPG8MT/3UCDzwR9gPeyFNMCtInsol++5m3bk2bXWKdZjvybmohrAsn3Ua5x8gfLnbE1YkOg==" crossorigin="anonymous">
    <link href="//cdnjs.cloudflare.com/ajax/libs/authy-forms.css/2.2/form.authy.min.css" rel="stylesheet">
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

    <h1>We're going to be *BEST* friends</h1>
    <p> Thanks for your interest in signing up! Can you tell us a bit about yourself?</p>

    <form action="registration" method="post">
        <div class="form-group">
            <label for="name">Tell us your name:</label>
            <input type="text" class="form-control" name="name" id="name" placeholder="Zingelbert Bembledack" value="${name}">
            <span class="text-danger">${nameError}</span>
        </div>
        <div class="form-group">
            <label for="email">Enter Your E-mail Address:</label>
            <input type="text" class="form-control" name="email" id="email" placeholder="you@yourdomain.com" value="${email}">
            <span class="text-danger">${emailError}</span>
            <span class="text-danger">${emailInvalidError}</span>
        </div>
        <div class="form-group">
            <label for="password">Enter a password:</label>
            <input type="password" class="form-control" name="password" id="password">
            <span class="text-danger">${passwordError}</span>
        </div>
        <div class="form-group">
            <label for="authy-countries">Country code:</label>
            <input type="text" class="form-control" name="countryCode" id="authy-countries" value="${countryCode}">
            <span class="text-danger">${countryCodeError}</span>
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone number:</label>
            <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" value="${phoneNumber}">
            <span class="text-danger">${phoneNumberError}</span>
        </div>
        <button class="btn btn-primary">Sign up</button>
    </form>

</section><!-- /.container -->

<footer class="container">
    Made with <i class="fa fa-heart"></i> by your pals
    <a href="http://www.twilio.com">@twilio</a>
</footer>

<script src="//cdnjs.cloudflare.com/ajax/libs/authy-forms.js/2.2/form.authy.min.js"></script>

</body>
</html>