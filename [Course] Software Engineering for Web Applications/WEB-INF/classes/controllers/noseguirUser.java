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
 * Servlet implementation class noseguirUser
 */
public class noseguirUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public noseguirUser() {
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

		DAO conexion;
		try {
			conexion = new DAO();

			try {
				
				conexion.updateSQL("DELETE FROM friendship WHERE id_friendship ="+id);
				

			} catch (SQLException e) {
				System.err.println("Se obtiene el siguiente error al realizar el delete en la BD: " + e.getMessage());
				e.printStackTrace();
			}

			conexion.desconnectarBD();
			
		} catch (Exception e1) {
			System.err.println("Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
			e1.printStackTrace();
		}
		
     	RequestDispatcher dispatcher = request.getRequestDispatcher("/usuarios.jsp");
		 if (dispatcher != null) dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
