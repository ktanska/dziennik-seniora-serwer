<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>  
<%@page import="java.util.Date"%>
<%@page import="java.lang.Object"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.Instant"%>
<%@page import="java.sql.*"%>
<%
String driver = "com.mysql.jdbc.Driver";
try {
Class.forName(driver);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
Connection conn = null;
Statement statement = null;
ResultSet resultSet = null;
ArrayList<String> JS = new ArrayList<String>();
String raport = "";
String login_seniora = request.getParameter("login");
String data_pom = request.getParameter("date");
String rok = data_pom.substring(6, 10);
String mies = data_pom.substring(0, 2);
String dz = data_pom.substring(3, 5);
data_pom = rok+"-"+mies+"-"+dz;
String sql = "SELECT PK FROM user WHERE Login LIKE '"+login_seniora+"'";

conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kinga\\git\\repository\\telematyka-serwer\\telematyka.db");
Statement st = conn.createStatement();

ResultSet rs1 = st.executeQuery(sql);
String PK = rs1.getString("PK");
System.out.println("PK"+ PK);

String query = "select * from wyniki WHERE data_pom Like '%" + data_pom + "%' AND user_klucz Like '" + PK + "'";

ResultSet rs = st.executeQuery(query);
ResultSetMetaData rsmd = rs.getMetaData();
try {
	while (rs.next()) {
		JS.add("data/" + rs.getString("data_pom"));
		JS.add("tetno/" + rs.getString("tetno"));
		JS.add("cisnienie_s/" + rs.getString("cisnienie_s"));
		JS.add("cisnienie_r/" + rs.getString("cisnienie_r"));
		JS.add("waga/" + rs.getString("waga"));
		JS.add("cukier/" + rs.getString("cukier"));
		raport = raport + JS.toString() + ",";
	  } 
} catch (SQLException e) {
	e.printStackTrace();
}
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
table.greyGridTable {
  border: 2px solid #FFFFFF;
  width: 100%;
  text-align: center;
  border-collapse: collapse;
}
table.greyGridTable td, table.greyGridTable th {
  border: 1px solid #FFFFFF;
  padding: 3px 4px;
}
table.greyGridTable tbody td {
  font-size: 13px;
}
table.greyGridTable td:nth-child(even) {
  background: #EBEBEB;
}
table.greyGridTable thead {
  background: #FFFFFF;
  border-bottom: 4px solid #333333;
}
table.greyGridTable thead th {
  font-size: 15px;
  font-weight: bold;
  color: #333333;
  text-align: center;
  border-left: 2px solid #333333;
}
table.greyGridTable thead th:first-child {
  border-left: none;
}

table.greyGridTable tfoot td {
  font-size: 14px;
}
</style>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table class="greyGridTable" style="width: 70%; align: center">
<thead>
<tr>
<th>Pomiary</th>
<th>Dane</th>
</tr>
</thead>
<%
for(int i=0; i < JS.size(); i++){
%>
<tbody>
<%  if(!JS.get(i).substring(0, JS.get(i).indexOf("/")).equals("data")){ %>
<tr>
<td style="border-style: solid; font-weight: bold;"><%=JS.get(i).substring(0, JS.get(i).indexOf("/")) %></td>
<td style="border-style: solid; font-weight: bold;"><%=JS.get(i).substring(JS.get(i).indexOf("/")+1) %></td>
</tr>
<%} else { %>
<tr>
<th><%=JS.get(i).substring(0, JS.get(i).indexOf("/")) %></th>
<th><%=JS.get(i).substring(JS.get(i).indexOf("/")+1) %></th>
</tr>
</tbody>
<% }} %>
</body>
</html>