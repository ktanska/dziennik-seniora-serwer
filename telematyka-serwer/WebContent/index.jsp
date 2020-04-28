<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<style>
body {
background: rgb(2,0,36);
background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(73,9,121,1) 0%, rgba(0,212,255,1) 100%);
}
input[type=text] {
  width: 50%;
  padding: 10px 15px;
  margin: 8px 0;
  box-sizing: border-box;
}
input[type=password] {
  width: 50%;
  padding: 10px 15px;
  margin: 8px 0;
  box-sizing: border-box;
}
#titlefont {
font-family: Georgia, serif;
font-size: 25px;
letter-spacing: 1.6px;
word-spacing: 0px;
color: #000000;
font-weight: 700;
text-decoration: none;
font-style: normal;
font-variant: normal;
text-transform: uppercase;
color:white;
margin-top:50px;
}
.button {
  background-color: e7e7e7; /* Green */
  border: none;
  color: black;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}
</style>
<head>
<meta charset="ISO-8859-1">
<title>Dziennik seniora</title>
</head>
<body>
<center><div id="titlefont">Strona lekarza - dzienniczek zdrowia seniora</div><div style="position:relative; padding-top:30px;"><img src="logo.png"></div></center>
<form style="margin: 70px;" name="loginForm" method="post" action="lekarz-domowa.jsp">
   <p style="color:white"> LOGIN: <input type="text" name="username"/> </p><br/> 
   <p style="color:white"> HASŁO: <input type="password" name="password"/> </p><br/>
    <input class="button" type="submit" value="Login" />
</form>
</body>
</html>