import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;

public class db_connector {
	private static final String PreparedStatement = null;
	public Connection conn() {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = "jdbc:sqlite:C:\\Users\\kinga\\git\\repository\\telematyka-serwer\\telematyka.db";
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("notConnection");
		}
		System.out.println(conn);
		return conn;
	}
	public boolean logowanie(String log, String pass, String typ) throws SQLException, IOException {
		String query = null;
		if (typ.equals("lekarz")) {
			query = "SELECT * from user WHERE Typ='lekarz'";
		}
		if (typ.equals("senior")) {
			query = "SELECT * from user WHERE Typ='senior'";
		}
		Connection conn = conn();
		Statement st = conn.createStatement();

	      ResultSet rs = st.executeQuery(query);
	     
	      try {
			do
			  {
			    String login = rs.getString("Login");
			    String haslo = rs.getString("Haslo");
			    System.out.println(login);
			    System.out.println(haslo);
			    if (login.equals(log) && haslo.equals(pass)) {
			    	System.out.println("Zalogowano");
			    	conn.close();
			    	return true;
			    }
			    else {
			    	System.out.println("Brak u¿ytkownika w bazie");
			    	conn.close();
			    	return false;
			    }
			  } while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    conn.close();
		return false;
	}
	public void insertdailyraport(String line) throws SQLException {
		// TODO Auto-generated method stub
		final JSONObject obj =  new JSONObject(line.substring(9));
		System.out.println("sql"+line);
		String sug = (String)obj.getString("sugar");
		String temp = (String)obj.getString("temperature");
		String s_bl_press = (String)obj.getString("systolic blood pressure");
		String d_bl_press = (String)obj.getString("diastolic blood pressure");
		String pulse = (String)obj.getString("pulse");
		String date = (String)obj.get("date");
		String login = (String)obj.get("login");
		String pass = (String)obj.get("password");
	//	String feeling = (String)obj.get("feeling");
		String query = "SELECT PK FROM user WHERE Login LIKE '"+login+"' AND Haslo LIKE '"+pass+"'";
		
		Connection conn = conn();
		Statement st = conn.createStatement();

	    ResultSet rs = st.executeQuery(query);
	    String PK = rs.getString("PK");
	    System.out.println("PK"+ PK);
		
		System.out.println(conn);
		String sql = "INSERT INTO wyniki (cisnienie, tetno, cukier, user_klucz, data) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, s_bl_press);
		pstmt.setString(2,  pulse);
		pstmt.setString(3, sug);
		//pstmt.setString(4,  feeling);
		pstmt.setString(4,  PK);
		pstmt.setString(5, date);
		pstmt.executeUpdate();
		conn.close();
	}
	public String getRaports(String raport_date) throws SQLException {
		String raport = null;
		String query = "select * from wyniki WHERE data Like '%" + raport_date.substring(9) + "%'";
		Connection conn = conn();
		Statement st = conn.createStatement();

	    ResultSet rs = st.executeQuery(query);
	    List<Map<String, Object>> rows = new ArrayList<>();
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnCount = rsmd.getColumnCount();
	    try {
	    	while (rs.next()) {
//				Map<String, Object> row = new HashMap<>();
//			      for (int i = 1; i <= columnCount; i++) {
//			           // Note that the index is 1-based
//			           String colName = rsmd.getColumnName(i);
//			           Object colVal = rs.getObject(i);
//			           row.put(colName, colVal);
//			      }
//			      rows.add(row);
	    		JSONObject JS = new JSONObject();
	    		JS.put("tetno", rs.getString("tetno"));
	    		JS.put("cisnienie", rs.getString("cisnienie"));
	    		raport = raport + JS.toString() + ",";
			  } 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  //  raport = rows.toString();
	    return raport;
	}
}
