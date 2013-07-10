package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.BeanUser;
import utils.BeanUtilities;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbaccess.DAO;

/**
 * Servlet implementation class formcontroller
 */
public class formcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String SQL_INSERT_USER =
            "INSERT INTO user ("
            + "username,name,surname,password,email,sex,birthday"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public formcontroller() {
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
		
	BeanUser user = new BeanUser();
	   BeanUtilities.populateBean(user, request);
	   
	   
	    if (user.isComplete()) {
	    	     
			
			DAO conexion;
			try {
				conexion = new DAO();
			
				try {
					conexion.insertSQL(SQL_INSERT_USER, user);
					
				} catch (SQLException e) {
					System.err.println("Se obtiene el siguiente error al realizar el insert en la BD: " + e.getMessage());
					e.printStackTrace();
				}

				conexion.desconnectarBD();
				
			} catch (Exception e1) {
				System.err.println("Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
				e1.printStackTrace();
			}
			
			
			HttpSession session = request.getSession();
	    	session.setAttribute("user",user.getUser());
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("SuccessRegistration.jsp");
		    if (dispatcher != null) dispatcher.forward(request, response);
	    	
			
			
	    } else {
	     
	    request.setAttribute("user",user);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
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

	
	public boolean existeUser(String user) {
		
		ResultSet resultado = null;
		boolean encuentra = false;
		
		DAO conexion;
		try {
			conexion = new DAO();
		
			try {
				resultado = conexion.executarSQL("SELECT * FROM user WHERE username='"+user+"'");
			
				if (!resultado.next() )   //No existe el usuario en la BD
				{
					encuentra = false;
				}
				else encuentra = true;     //existe el usuario en la BD
				
			} catch (SQLException e) {
				System.err.println("[EXISTE USUARIO] Se obtiene el siguiente error al conectarte a la BD: " + e.getMessage());
				e.printStackTrace();
			}
			
			conexion.desconnectarBD();
			
		} catch (Exception e1) {
			System.err.println("[EXISTE USUARIO] Se obtiene el siguiente error al realizar la consulta en la BD: " + e1.getMessage());
			e1.printStackTrace();
		}
		
		return encuentra;
	}
		
		


}
