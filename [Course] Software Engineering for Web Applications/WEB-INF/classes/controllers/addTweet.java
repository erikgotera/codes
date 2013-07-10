package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbaccess.DAO;

/**
 * Servlet implementation class addTweet
 */
public class addTweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String SQL_INSERT_TWEET =
            "INSERT INTO tweet ("
            + "user_id,text,timestamp"
            + ") VALUES (?, ?, ?)";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addTweet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("text/html");         
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
	    HttpSession session = request.getSession(false);
	    String username = (String)session.getAttribute("username");
	    String text = (String)request.getParameter("text");
	    
	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
	    String timestamp = sdf.format(date);
	    
	    
	    DAO conexion;
		try {
			conexion = new DAO();
		
			try {
				conexion.insertTweetSQL(SQL_INSERT_TWEET, text, username, timestamp);
				
			} catch (SQLException e) {
				System.err.println("Se obtiene el siguiente error al realizar el insert en la BD: " + e.getMessage());
				e.printStackTrace();
			}

			conexion.desconnectarBD();
			
		} catch (Exception e1) {
			System.err.println("Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
			e1.printStackTrace();
		}   	
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/muropersonal.jsp");
		    if (dispatcher != null) dispatcher.forward(request, response);
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
