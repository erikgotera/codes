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
 * Servlet implementation class editProfile
 */
public class editProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editProfile() {
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
		
		String user = (String)request.getParameter("username");
		String name = (String)request.getParameter("name");
	    String surname = (String)request.getParameter("surname");
	    String mail = (String)request.getParameter("mail");
	    String sex = (String)request.getParameter("sex");
	    String birthday = (String)request.getParameter("birthday");
	    
	    HttpSession session = request.getSession(false);
	    session.setAttribute("name",name);
		session.setAttribute("surname",surname);
		session.setAttribute("email",mail);
		session.setAttribute("sex",sex);
		session.setAttribute("birthday",birthday);
	    
	    
	    DAO conexion;
	    String query = "UPDATE user SET name='"+name+"', surname='"+surname+"', email='"+mail+"', sex='"+sex+"', birthday='"+birthday+"'  WHERE username='"+user+"'";
		
		try {
			conexion = new DAO();
			try {
				conexion.updateSQL(query);
											
			} catch (SQLException e) {
				System.err.println("[EDIT PROFILE]Se obtiene el siguiente error al realizar el insert en la BD: " + e.getMessage());
				e.printStackTrace();
			}

			conexion.desconnectarBD();

		} catch (Exception e1) {
			System.err.println("[EDIT PROFILE]Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
			e1.printStackTrace();
		}
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/perfil.jsp");
		    if (dispatcher != null) dispatcher.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
