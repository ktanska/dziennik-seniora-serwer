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
Statement statementsenior = null;
Statement state_feeling = null;
ResultSet resultSet = null;
ResultSet resultSetsenior = null;
ResultSet result_feeling = null;
%>
<!DOCTYPE html>
<html>
<style>
body {
background: #F0F8FF;
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
color:#6666FF;
margin-top:50px;
}
.wrapper {
  margin: 15px auto;
  max-width: 1100px;
}

.container-calendar {
  background: #ffffff;
  padding: 15px;
  max-width: 475px;
  margin: 0 auto;
  overflow: auto;
}

.button-container-calendar button {
  cursor: pointer;
  display: inline-block;
  zoom: 1;
  background: #00a2b7;
  color: #fff;
  border: 1px solid #0aa2b5;
  border-radius: 4px;
  padding: 5px 10px;
}

.table-calendar {
  border-collapse: collapse;
  width: 100%;
}

.table-calendar td, .table-calendar th {
  padding: 5px;
  border: 1px solid #e2e2e2;
  text-align: center;
  vertical-align: top;
}

.date-picker.selected {
  font-weight: bold;
  outline: 1px dashed #00BCD4;
}

.date-picker.selected span {
  border-bottom: 2px solid currentColor;
}

/* sunday */
.date-picker:nth-child(1) {
color: red;
}

/* friday */
.date-picker:nth-child(6) {
color: green;
}

#monthAndYear {
  text-align: center;
  margin-top: 0;
}

.button-container-calendar {
  position: relative;
  margin-bottom: 1em;
  overflow: hidden;
  clear: both;
}

#previous {
  float: left;
}

#next {
  float: right;
}

.footer-container-calendar {
  margin-top: 1em;
  border-top: 1px solid #dadada;
  padding: 10px 0;
}

.footer-container-calendar select {
  cursor: pointer;
  display: inline-block;
  zoom: 1;
  background: #ffffff;
  color: #585858;
  border: 1px solid #bfc5c5;
  border-radius: 3px;
  padding: 5px 1em;
}
</style>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script src="calendar.js"></script>

<%
   String query = "SELECT * from user WHERE Typ='lekarz'";
   String seniors = "Select Login from user Where Typ='senior'";
   connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kinga\\git\\repository\\telematyka-serwer\\telematyka.db");
   statement=connection.createStatement();
   statementsenior=connection.createStatement();
   resultSet = statement.executeQuery(query);
   resultSetsenior = statementsenior.executeQuery(seniors);
     try {
		do
		  {
		    String login = resultSet.getString("Login");
		    String haslo = resultSet.getString("Haslo");
		    System.out.println(login);
		    System.out.println(haslo);
		    if (login.equals(request.getParameter("username")) && haslo.equals(request.getParameter("password"))) {
		    	System.out.println("Zalogowano");
		    	%>
		    	<center><div id="titlefont">Dzienniczek zdrowia seniora</div></center>
		    	<div style="margin-top:70px">Wybierz pacjenta: </div>
		    	<form style="margin: 70px;" name=senior" method="post" action="wyniki-seniora.jsp">
		    	<select name="login">
		    	<%
		    	while(resultSetsenior.next()){
		    	%>
		    	<option>
		    	<%=resultSetsenior.getString("Login")%>
		    	</option>
		    	 <%
		    	// do zrobienia: wysłanie informacji zwrotnej do użytkownika
		    	}
		    	%></select>
		    	<input type="date" name="date">
		    	<input type="submit" value="zatwierdz">
		    	<%
		    	break;
		    }
		    else {
		    	System.out.println("Brak użytkownika w bazie");
		    }
		  } while (resultSet.next());
 connection.close();
 } catch (Exception e) {
 e.printStackTrace();
 }
String feeling = "Select samopoczucie from wyniki";
connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kinga\\git\\repository\\telematyka-serwer\\telematyka.db");
state_feeling=connection.createStatement();
result_feeling = state_feeling.executeQuery(feeling);
try {
		do
		  {
			%> <div>
		    <%=result_feeling.getString("samopoczucie") %> </div>
		    <%
		  } while (resultSet.next());
connection.close();
} catch (Exception e) {
e.printStackTrace();
}
%>
</body>
<script>
</script>
</html>