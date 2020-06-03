import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
		String sql = "INSERT INTO wyniki (cisnienie_s, cisnienie_r, tetno, cukier, user_klucz, data_pom) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, s_bl_press);
		pstmt.setString(2, d_bl_press);
		pstmt.setString(3,  pulse);
		pstmt.setString(4, sug);
		//pstmt.setString(4,  feeling);
		pstmt.setString(5,  PK);
		pstmt.setString(6, date);
		pstmt.executeUpdate();
		conn.close();
	}
	public String getRaports(String line) throws SQLException {
		final JSONObject obj =  new JSONObject(line.substring(9));
		System.out.println("sql"+line);
		String date = (String)obj.get("choosen_date");
		String login = (String)obj.get("login");
		String pass = (String)obj.get("password");
		String raport = null;
		
		String sql = "SELECT PK FROM user WHERE Login LIKE '"+login+"' AND Haslo LIKE '"+pass+"'";
		
		Connection conn = conn();
		Statement st = conn.createStatement();

	    ResultSet rs1 = st.executeQuery(sql);
	    String PK = rs1.getString("PK");
	    System.out.println("PK"+ PK);
	    
		String query = "select * from wyniki WHERE data_pom Like '%" + date + "%' AND user_klucz Like '" + PK + "'";

	    ResultSet rs = st.executeQuery(query);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    try {
	    	while (rs.next()) {
	    		JSONObject JS = new JSONObject();
	    		JS.put("tetno", rs.getString("tetno"));
	    		JS.put("cisnienie_s", rs.getString("cisnienie_s"));
	    		JS.put("cisnienie_r", rs.getString("cisnienie_r"));
	    		JS.put("waga", rs.getString("waga"));
	    		JS.put("cukier", rs.getString("cukier"));
	    		JS.put("data", rs.getString("data_pom"));
	    		raport = raport + JS.toString() + ",";
			  } 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  //  raport = rows.toString();
	    return raport;
	}
	public boolean user_register(String log, String pass, String wg, String hg, String grupa) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = conn();
		String query = "SELECT * from user WHERE Typ='senior'";
		Statement st = conn.createStatement();

	      ResultSet rs = st.executeQuery(query);
	    
			do
			  {
			    String login = rs.getString("Login");
			    String haslo = rs.getString("Haslo");
			    System.out.println(login);
			    System.out.println(haslo);
			    if (login.equals(log)) {
			    	return false;
			    }
			  }while(rs.next());
		String sql = "INSERT INTO user (Login, Haslo, Typ) VALUES (?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, log);
		pstmt.setString(2, pass);
		pstmt.setString(3, "senior");
		pstmt.executeUpdate();
		conn.close();
		Connection conn2 = conn();
		String sql1 = "SELECT PK FROM user WHERE Login LIKE '"+log+"' AND Haslo LIKE '"+pass+"'";
		
		Statement st1 = conn2.createStatement();

	    ResultSet rs1 = st1.executeQuery(sql1);
	    String PK = rs1.getString("PK");
	    System.out.println("PK"+ PK);
	    String sql2 = "INSERT INTO Dane (Waga, User_klucz, Wzrost, Grupa_krwi) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt_dane = conn2.prepareStatement(sql2);
		pstmt_dane.setString(1, wg);
		pstmt_dane.setString(2,  PK);
		pstmt_dane.setString(3, hg);
		pstmt_dane.setString(4, grupa);
		pstmt_dane.executeUpdate();
	    conn2.close();
		return true;
	}
	public String getMessage(String line) throws SQLException {
		// TODO Auto-generated method stub
		final JSONObject obj =  new JSONObject(line.substring(9));
		String samopoczucie = null;
		String kiedy = null;
		System.out.println("sql"+line);
		String login = (String)obj.get("login");
		if(obj.has("tresc")) {
			samopoczucie = (String)obj.get("tresc");
			kiedy = (String)obj.get("date");
		}
		String raport = null;
		
		String sql = "SELECT PK FROM user WHERE Login LIKE '"+login+"'";
		
		Connection conn = conn();
		Statement st = conn.createStatement();

	    ResultSet rs1 = st.executeQuery(sql);
	    String PK = rs1.getString("PK");
	    System.out.println("PK"+ PK);
	    
		String query = "select * from wiadomosc WHERE user_klucz Like '" + PK + "'";

	    ResultSet rs = st.executeQuery(query);
	    raport = rs.getString("tresc");
		if (samopoczucie != null) {
	    	String query_test = "SELECT tresc, data FROM samopoczucie WHERE user_klucz LIKE '"+PK+"'";
	    	Statement statementwiad=conn.createStatement();
	    	ResultSet resultSetwiad = statementwiad.executeQuery(query_test);
	    	if(resultSetwiad.getString("tresc") == null){
	    		String query_wiad = "INSERT INTO samopoczucie(tresc, user_klucz, data) VALUES (?,?,?)";
	    		PreparedStatement pstmt_samop = conn.prepareStatement(query_wiad);
	    		pstmt_samop.setString(1, samopoczucie);
	    		pstmt_samop.setString(2, PK);
	    		pstmt_samop.setString(3, kiedy);
	    		pstmt_samop.executeUpdate();
	    	}
	    	else {
	    		String sql_wiad="Update samopoczucie set tresc=?, data=? where user_klucz="+PK;
	    		PreparedStatement ps_wiad = conn.prepareStatement(sql_wiad);
	    		ps_wiad.setString(1,samopoczucie);
	    		ps_wiad.setString(2, kiedy);
	    		ps_wiad.executeUpdate();
	    	}
	    }
	    conn.close();
	    return raport;
	}
}
