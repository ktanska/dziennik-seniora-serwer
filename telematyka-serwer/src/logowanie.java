

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class logowanie
 */
@WebServlet("/logowanie")
public class logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logowanie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		StringBuffer jb = new StringBuffer();
		 String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    boolean zalogowany = false;
		    while ((line = reader.readLine()) != null) {
		    	System.out.println(line);
		    	final JSONObject obj =  new JSONObject(line.substring(9));
				System.out.println(line);
				String typ = (String)obj.getString("type");
				String log = (String)obj.getString("login");
				String pass = (String)obj.getString("password");
				System.out.println("typ: "+typ);
				if (typ.equals("log")) {
					db_connector db = new db_connector(); //po³¹czenie z baz¹ danych
					zalogowany = db.logowanie(log, pass, "senior"); //wywolanie funckji sprawdzaj¹cej czy login istnieje w bazie
				}
		    }
		    	if(zalogowany == true) {
			    	PrintWriter output = response.getWriter();
					output.println("login accepted");
					output.flush();
			  }
		    	else {
		    		PrintWriter output = response.getWriter();
					output.println("login rejected");
					output.flush();
		    	}
		  } catch (Exception e) { /*report an error*/ }
	}

}
