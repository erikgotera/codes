

<%@ page import="dbaccess.DAO" %>
<%@ page import="java.sql.*" %>  

<%
session = request.getSession(false);
String username = (String)session.getAttribute("username");

ResultSet resultado = null;
DAO conexion;
try {
	conexion = new DAO();

	try {
		resultado = conexion.executarSQL("SELECT * FROM tweet where user_id='"+username+"'");%>
		
		<table border="0" id="tablaTweets">
		
		<%
		while(resultado.next())
		{%>		

				<tr><td><div id="tweetUser"><%=resultado.getString("user_id")%></div></td></tr>
				<tr><td><%=resultado.getString("text")%></td></tr>
				<tr><td><div id="tweetTimestamp"><%=resultado.getString("timestamp")%></div></td><td><a id="editarTweet" href="#" onclick="cambiar('editTweet-<%=resultado.getString("idtweet")%>'); return false;">Editar</a> |</td><td><a class="borrar" id="<%=resultado.getString("idtweet")%>" href=#>Borrar</a></td></tr>
				<tr><td colspan="3"><hr></td></tr>
	
				<tr><td colspan="5">
					<div id="editTweet-<%=resultado.getString("idtweet")%>" style="display: none;">	
					<script type="text/javascript">
						$(document).ready(function() {
						    $("#tweetEdit<%=resultado.getString("idtweet")%>").validate({
						    	submitHandler: function(form) {
						    		$('#content').load('editTweet',$("#tweetEdit<%=resultado.getString("idtweet")%>").serialize());
						    	}
						    });
						});
						</script>
						<form action="" method="POST" name="tweetEdit<%=resultado.getString("idtweet")%>" id="tweetEdit<%=resultado.getString("idtweet")%>">
						<table>
							<tr><td>Edit Tweet</td></tr>
							<tr> 
							<td>Text:</td> 
							<td> <textarea id="text"  name="text" maxlength="140" class="required"><%=resultado.getString("text")%></textarea> </td> 
							</tr>
							<tr><td><input type="hidden" id="id_tweet" name="id_tweet" value="<%=resultado.getString("idtweet")%>"></td></tr>
							<tr><td><input class="submit" type="submit" value="Editar"/></td></tr>    
						</table>
						</form>
						
					</div>
				</td></tr>		
		<%}%>
		
		</table>
		<%resultado.close();	
		
		
		
	} catch (SQLException e) {
		System.err.println("Se obtiene el siguiente error al realizar el insert en la BD: " + e.getMessage());
		e.printStackTrace();
	}

	conexion.desconnectarBD();
	
} catch (Exception e1) {
	System.err.println("Se obtiene el siguiente error al conectarte a la BD: " + e1.getMessage());
	e1.printStackTrace();
}
%>