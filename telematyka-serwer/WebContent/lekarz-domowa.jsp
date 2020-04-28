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
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
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
		    if (login.equals(request.getParameter("username")) && haslo.equals(request.getParameter("password"))) {
		    	System.out.println("Zalogowano");
		    	// do zrobienia: wysłanie informacji zwrotnej do użytkownika
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
   %>
</body>
</html>