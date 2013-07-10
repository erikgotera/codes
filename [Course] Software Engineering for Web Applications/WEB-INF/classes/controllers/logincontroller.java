package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbaccess.DAO;

import models.BeanLogin;
import utils.BeanUtilities;

/**
 * Servlet implementation class logincontroller
 */
public class logincontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logincontroller() {
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
		
		ResultSet resultado = null;
		
		BeanLogin login = new BeanLogin();
	    BeanUtilities.populateBean(login, request);
	    
	    if (login.isComplete()) {
	    	
	    	HttpSession session = request.getSession();
	    	session.setAttribute("username",login.getUser());
	    	
	    	//OBTENER TODOS LOS DATOS DEL USUARIO DESDE LA BD
	    	DAO conexion;
			try {
				conexion = new DAO();			
				
				try {
					resultado = conexion.executarSQL("SELECT * FROM user WHERE username='"+login.getUser()+"'");
					
						if(resultado.next())
						{
							session.setAttribute("name",resultado.getString("name"));
							session.setAttribute("surname",resultado.getString("surname"));
							session.setAttribute("email",resultado.getString("email"));
							session.setAttribute("sex",resultado.getString("sex"));
							session.setAttribute("birthday",resultado.getString("birthday"));
							session.setAttribute("image",resultado.getString("image"));
							session.setAttribute("admin", resultado.getInt("admin"));
						}
						
						resultado.close();	
						
				} catch (SQLException e) {
					System.err.println("[DATOS SESION]Se obtiene el siguiente error al realizar la consulta en la BD: " + e.getMessage());
					e.printStackTrace();
				}
				
				conexion.desconnectarBD();
				
			} catch (Exception e1) {
				System.err.println("[DATOS SESION]Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
				e1.printStackTrace();
			}

	    	//FIN OBTENCION DATOS DE LA BD
	    	
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/muro.jsp");
		    if (dispatcher != null) dispatcher.forward(request, response);
	    } else {
	     
	    request.setAttribute("login",login);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	    if (dispatcher != null) dispatcher.forward(request, response);
	    	
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
	public boolean equalPass(String user, String password) {
		
		ResultSet resultado = null;
		String passDB = null;
		boolean esta = false;
		
		DAO conexion;
		try {
			conexion = new DAO();			
			
			try {
				resultado = conexion.executarSQL("SELECT * FROM user WHERE username='"+user+"'");
				
					if(resultado.next())
					{
						passDB = resultado.getString("password");
					}
					
					if(password.equals(passDB)){
						esta = true;
					}
					
					resultado.close();	
					
			} catch (SQLException e) {
				System.err.println("[EQUALPASS]Se obtiene el siguiente error al realizar la consulta en la BD: " + e.getMessage());
				e.printStackTrace();
			}
			
			conexion.desconnectarBD();
			
		} catch (Exception e1) {
			System.err.println("[EQUALPASS]Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
			e1.printStackTrace();
		}
		
		return esta;
	
	}

}
