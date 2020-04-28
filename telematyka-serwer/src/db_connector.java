import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

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
	public void logowanie(String log, String pass, String typ) throws SQLException, IOException {
		String query = null;
		if (typ.equals("lekarz")) {
			query = "SELECT * from user WHERE Typ='lekarz'";
		}
		if (typ.equals("senior")) {
			query = "SELECT * from user WHERE Typ='senior'";
		}
		Connection conn = this.conn();
		Statement st = conn.createStatement();

	      ResultSet rs = st.executeQuery(query);
	     
	      try {
			do
			  {
			    String login = rs.getString("Login");
			    String haslo = rs.getString("Has³o");
			    System.out.println(login);
			    System.out.println(haslo);
			    if (login.equals(log) && haslo.equals(pass)) {
			    	System.out.println("Zalogowano");
			    	// do zrobienia: wys³anie informacji zwrotnej do u¿ytkownika
			    	break;
			    }
			    else {
			    	System.out.println("Brak u¿ytkownika w bazie");
			    }
			  } while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
		
	}
	public void insertdailyraport(String line) throws SQLException {
		// TODO Auto-generated method stub
		final JSONObject obj =  new JSONObject(line.substring(9));
		System.out.println(line);
		String sug = (String)obj.getString("sugar");
		String temp = (String)obj.getString("temperature");
		String bl_press = (String)obj.getString("blood pressure");
		String pulse = (String)obj.getString("pulse");
		String date = (String)obj.get("date");
		String login = (String)obj.get("login");
		
		Connection conn = this.conn();
		System.out.println(conn);
		String sql = "";
		PreparedStatement pstmt = conn.prepareStatement(sql);
	}
}
