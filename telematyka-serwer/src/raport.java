

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class raport
 */
@WebServlet("/raport")
public class raport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public raport() {
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
		System.out.println("Przetwarzam zadania");
		StringBuffer jb = new StringBuffer();
		String raport = null;
		StringBuilder sb = null;
		 String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null) {
		    	System.out.println(line);
		    	raport = parser.parraport(line);
		    }
		  } catch (Exception e) { /*report an error*/ }
		  if (raport != null) {
			  raport = raport.substring(4);
			  raport = "[" + raport + "]";
			  sb = new StringBuilder(raport);
			  sb.deleteCharAt(raport.lastIndexOf(","));
		  }
		  PrintWriter output = response.getWriter();
			output.println(sb);
		output.flush();
	}

}
