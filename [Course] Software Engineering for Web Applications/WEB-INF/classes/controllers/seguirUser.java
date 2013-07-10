package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbaccess.DAO;

/**
 * Servlet implementation class seguirUser
 */
public class seguirUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public seguirUser() {
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
		String user_1 = (String)session.getAttribute("username");
		String id_user2 = (String)request.getParameter("id");

		DAO conexion;
		try {
			conexion = new DAO();

			try {
				conexion.insertSQL("INSERT INTO friendship (user_1,user_2) VALUES (?, ?)", user_1,id_user2);

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
