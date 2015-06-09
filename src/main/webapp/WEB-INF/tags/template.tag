<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="head" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="navigationTop" fragment="true" %>
<%@attribute name="navigationLeft" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="resources/css/styles.css">
	<link rel="stylesheet" type="text/css" href="resources/css/loginForm.css">
	<script src="resources/jquery/jquery-2.1.4.js"></script>
	<script src="resources/js/ListAnimation.js"></script>
	<!-- Custom css and javascript for one page -->
	<jsp:invoke fragment="head"/>
</head>
<body>
<div id="header">
	<h1>Events!</h1>
	<ul id="menu">
		<li><a href="/">Home</a></li>
		<li><a href="#statistics">Statistics</a></li>
		<li><a href="#gallery">Gallery</a></li>
		<li><a href="#ratings">Ratings</a></li>
	</ul>
</div>
<div id="body">
	<div id="navigation-left">
		<div class="post">
			<div class="btn-sign">
				<a href="#login-box" class="login-window">Login / Sign In</a>
			</div>
		</div>

		<div id="login-box" class="login-popup">
			<a href="#" class="close"><img src="close_pop.png" class="btn_close" title="Close Window" alt="Close"/></a>

			<form method="post" class="signin" action="#">
				<fieldset class="textbox">
					<label class="username">
						<span>Username or email</span>
						<input id="username" name="username" value="" type="text" autocomplete="on"
						       placeholder="Username">
					</label>

					<label class="password">
						<span>Password</span>
						<input id="password" name="password" value="" type="password" placeholder="Password">
					</label>

					<button class="submit button" type="button">Sign in</button>

					<p>
						<a class="forgot" href="#">Forgot your password?</a>
					</p>

				</fieldset>
			</form>
		</div>
		<br/>
		<jsp:include page="/categories"/>
	</div>

	<div id="section">
		<jsp:doBody/>
	</div>
</div>
<div id="footer">
	Thanks (c)
</div>
</body>
</html>
