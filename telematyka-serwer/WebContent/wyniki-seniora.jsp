<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
</style>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%=request.getParameter("login") %>
<%=request.getParameter("date") %>
Today's date: <%= (new java.util.Date()).toLocaleString()%>
</body>
</html>