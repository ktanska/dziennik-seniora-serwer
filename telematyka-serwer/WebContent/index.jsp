<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>  
<%@page import="java.util.Date"%>
<%@page import="java.lang.Object"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.Instant"%>
<%
String driver = "com.mysql.jdbc.Driver";
try {
Class.forName(driver);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
String login_lekarza=request.getParameter("username");
String haslo_lekarza=request.getParameter("password");
%>
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
<form style="margin: 70px;" name="loginForm" method="post">
   <p style="color:white"> LOGIN: <input type="text" name="username"/> </p><br/> 
   <p style="color:white"> HASLO: <input type="password" name="password"/> </p><br/>
    <input class="button" type="submit" value="Login" id="log" />
</form>
<%
   String query = "SELECT * from user WHERE Typ='lekarz'";
   connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kinga\\git\\repository\\telematyka-serwer\\telematyka.db");
   statement=connection.createStatement();
   resultSet = statement.executeQuery(query);
     try {
		do
		  {
		    String login = resultSet.getString("Login");
		    String haslo = resultSet.getString("Haslo");
		    System.out.println(login);
		    System.out.println(haslo);
		    if (login.equals(login_lekarza) && haslo.equals(haslo_lekarza)) {
		    	System.out.println("Zalogowano");
		    	 String redirectURL = "http://localhost:8080/telematyka-serwer/lekarz-domowa.jsp";
        			response.sendRedirect(redirectURL);
		    	}
		    else {
		    	System.out.println("Brak użytkownika w bazie");
		    }
		  } while (resultSet.next());
		connection.close();
     } catch (Exception e) {
     e.printStackTrace();
     }
 %>
</body>
<script>
$(document).ready(function() { 
    $("#log").click(function() { 
        var fn = $("#username").val();
        var vn = $("#password").val();
        $.post("index.jsp", { 
            login_lekarza: fn,
            haslo_lekarza: vn
        }, function(data) { 
            $("#msg").html(data); 
        }); 

    }); 
});
</script>
</html>