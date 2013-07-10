/**
 * 
 */
package dbaccess;

import java.sql.*;

import models.BeanUser;

/**
 * @author Erik Gotera, Francisco Perez
 *
 */
public class DAO {

	private	Connection connexio;	
	private	Statement sentencia;
	private PreparedStatement preparedStatement;
		
	public DAO() throws	Exception{	
	
		String user	= "root";	
		String password = "root";	
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();	
		
		connexio = DriverManager.getConnection("jdbc:mysql://localhost/metter_db?user="+user+"&password="+password);	
		sentencia = connexio.createStatement();	
		preparedStatement = null;
	}		
	
	//Executa	consultes	
	public ResultSet executarSQL(String	query) throws SQLException{		
		
		return	sentencia.executeQuery(query);	
	}	
	
	//Realizar insert
	public void insertSQL(String query, BeanUser user) throws SQLException{
		
		// PreparedStatements can use variables and are more efficient
	      preparedStatement = connexio.prepareStatement(query);
	      
	      // Parameters start with 0
	      preparedStatement.setString(1, user.getUser());
	      preparedStatement.setString(2, user.getName());
	      preparedStatement.setString(3, user.getSurname());
	      preparedStatement.setString(4, user.getPass());
	      preparedStatement.setString(5, user.getMail());
	      preparedStatement.setString(6, user.getSex());
	      preparedStatement.setString(7,user.getBirthday());
	      preparedStatement.executeUpdate();
	
	}
	
	//Realizar update
		public void updateSQL(String query) throws SQLException{
			
		      sentencia.executeUpdate(query);
		
		}
	
	public void desconnectarBD() throws SQLException{	
		
		sentencia.close();	
		connexio.close();	
	}

	public void insertTweetSQL(String query, String text, String user, String timestamp) throws SQLException{
		// PreparedStatements can use variables and are more efficient
	      preparedStatement = connexio.prepareStatement(query);
	      
	      // Parameters start with 0
	      preparedStatement.setString(1, user);
	      preparedStatement.setString(2, text);
	      preparedStatement.setString(3, timestamp);

	      preparedStatement.executeUpdate();
	
		
	}

	public void insertSQL(String query, String user_1, String id_user2) throws SQLException {
		// PreparedStatements can use variables and are more efficient
	      preparedStatement = connexio.prepareStatement(query);
	      
	      // Parameters start with 0
	      preparedStatement.setString(1, user_1);
	      preparedStatement.setString(2, id_user2);

	      preparedStatement.executeUpdate();
	}		
	
}
