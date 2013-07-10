package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.DAO;

/**
 * Servlet implementation class borrarTweetcontroller
 */
public class borrarTweetcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public borrarTweetcontroller() {
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
		
		String id = (String)request.getParameter("id");
		String idadmin = (String)request.getParameter("idadmin");

		DAO conexion;
		try {
			conexion = new DAO();

			try {
				
				if(id != null)
				{
					conexion.updateSQL("DELETE FROM tweet WHERE idtweet ="+id);
				}
				else
				{
					conexion.updateSQL("DELETE FROM tweet WHERE idtweet ="+idadmin);
				}

			} catch (SQLException e) {
				System.err.println("Se obtiene el siguiente error al realizar el delete en la BD: " + e.getMessage());
				e.printStackTrace();
			}

			conexion.desconnectarBD();
			
		} catch (Exception e1) {
			System.err.println("Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
			e1.printStackTrace();
		}
		
		if(id != null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/muropersonal.jsp");
			 if (dispatcher != null) dispatcher.forward(request, response);
		}
		else
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/adminTweet.jsp");
			 if (dispatcher != null) dispatcher.forward(request, response);
		}
     	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
