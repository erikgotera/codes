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

/**
 * Servlet implementation class UploadImage
 */
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImage() {
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
		DAO conexion;
		
		String newimage = (String)request.getParameter("newimage");
		
		HttpSession session = request.getSession(false);
		String usuario = (String) session.getAttribute("username");

        ////////  UPDATE EN LA BD //////////////
		String query = "UPDATE user SET image='"+newimage+"' WHERE username='"+usuario+"'";
			
		try {
			conexion = new DAO();
			try {
				conexion.updateSQL(query);
											
			} catch (SQLException e) {
				System.err.println("[CHANGE IMAGE]Se obtiene el siguiente error al realizar el insert en la BD: " + e.getMessage());
				e.printStackTrace();
			}

			conexion.desconnectarBD();

		} catch (Exception e1) {
			System.err.println("[CHANGE IMAGE]Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
			e1.printStackTrace();
		}
		//////// FIN UPDATE EN LA BD //////////////		
		
		//OBTENER TODOS LOS DATOS DEL USUARIO DESDE LA BD
    	
		try {
			conexion = new DAO();			
			
			try {
				resultado = conexion.executarSQL("SELECT image FROM user WHERE username='"+usuario+"'");
				
				if(resultado.next())
				{
					session.setAttribute("image",resultado.getString("image"));
				}
					
					resultado.close();	
					
			} catch (SQLException e) {
				System.err.println("[SELECT IMAGE]Se obtiene el siguiente error al realizar la consulta en la BD: " + e.getMessage());
				e.printStackTrace();
			}
			
			conexion.desconnectarBD();
			
		} catch (Exception e1) {
			System.err.println("[SELECT IMAGE]Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
			e1.printStackTrace();
		}

    	//FIN OBTENCION DATOS DE LA BD
		

			RequestDispatcher dispatcher = request.getRequestDispatcher("/perfil.jsp");
		
		    if (dispatcher != null) dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
