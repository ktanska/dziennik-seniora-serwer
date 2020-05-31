

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
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
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
		    while ((line = reader.readLine()) != null) {
		    	System.out.println(line);
		    	final JSONObject obj =  new JSONObject(line.substring(9));
				System.out.println(line);
				String log = (String)obj.getString("login");
				String pass = (String)obj.getString("password");
				String wg = (String)obj.getString("weight");
				String hg = (String)obj.getString("height");
				String gr = (String)obj.getString("blood_gr");
					db_connector db = new db_connector();
					if(db.user_register(log, pass, wg, hg, gr) == true) {
						PrintWriter output = response.getWriter();
						output.println("utworzono konto");
						output.flush();
					}
					else {
						PrintWriter output = response.getWriter();
						output.println("nazwa uzytkownika zajeta");
						output.flush();
					}
		    }
		  } catch (Exception e) { /*report an error*/ }
	}

}
