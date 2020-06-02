import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public class parser {
	public static void par(String line) throws Exception {
		final JSONObject obj =  new JSONObject(line.substring(9));
		System.out.println(line);
		String typ = (String)obj.getString("type");
		String log = (String)obj.getString("login");
		String pass = (String)obj.getString("password");
		System.out.println("typ: "+typ);
		if (typ.equals("log")) {
			db_connector db = new db_connector(); //po³¹czenie z baz¹ danych
			db.logowanie(log, pass, "senior"); //wywolanie funckji sprawdzaj¹cej czy login istnieje w bazie
		}
		else if (typ.equals("dailyraport")) {
			db_connector db = new db_connector();
			db.insertdailyraport(line); //wywo³anie funkcji wpisuj¹cej do tabeli dzienny raport
		}
	}
	public static void pardoctorlogin(String line) throws Exception {
		System.out.println(line);
		String log = line.substring(9, line.indexOf("&"));
		System.out.println("login" + log);
		String pass = line.substring(line.lastIndexOf("=")+1);
		System.out.println("haslo" + pass);
		db_connector db = new db_connector();
		db.logowanie(log, pass, "lekarz");
		
	}
	public static String parraport(String line) throws SQLException {
		db_connector db = new db_connector();
		String raport = db.getRaports(line);
		return raport;
	}
	public static String wiadomosc(String line) throws SQLException {
		// TODO Auto-generated method stub
		db_connector db = new db_connector();
		String raport = db.getMessage(line);
		return raport;
	}
}
