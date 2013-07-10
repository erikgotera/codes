package dbaccess;

import java.sql.*;

public class DAO {

	private	Connection connexio;	
	private	Statement sentencia;
	
	public DAO() throws	Exception{	
	
		String user	= "in2_int";	
		String password = "jiraint";	
		
		//Class.forName("com.oracle.jdbc.driver.OracleDriver").newInstance();
		DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		
		connexio = DriverManager.getConnection("jdbc:oracle:thin:@bd01prod:1521:bdqportal", user, password);	
		sentencia = connexio.createStatement();
	}		
	
	//Executa	consultes	
	public ResultSet executarSQL(String	query) throws SQLException{		
		return	sentencia.executeQuery(query);	
	}
	
	public void desconnectarBD() throws SQLException{	
		sentencia.close();	
		connexio.close();	
	}

}