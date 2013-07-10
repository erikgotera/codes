<script type="text/javascript">
$(document).ready(function() {
    $(".seguir").click(function(event) {
        $('#content').load('seguirUser',{id: $(this).attr('id')});
    });
    $(".noseguir").click(function(event) {
        $('#content').load('noseguirUser',{id: $(this).attr('id')});
    });
    $(".vermuro").click(function(event) {
        $('#content').load('verMuroFriend.jsp',{id: $(this).attr('id')});
    });
});
</script>

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
		//LISTADO DE LOS USUARIOS
		resultado = conexion.executarSQL("SELECT u.username, u.name, u.surname, u.email FROM user u WHERE u.username<>'"+username+"' AND u.username NOT IN (SELECT fr.user_2 FROM friendship fr WHERE fr.user_1='"+username+"')");%>
		
		
	<div id="listadoUsuarios">	
		
		<div class="espacio2"></div>
		
		<div class="headersClass" align="center">Todos los usuarios</div>
		
		<%
		while(resultado.next())
		{%>

						<div id="todosUsuarios">
							<div id="todosUsuarios2">
							<div class="nombreClass"><%=resultado.getString("username")%></div>
							<div><%=resultado.getString("name")%> <%=resultado.getString("surname")%></div>
							<div><%=resultado.getString("email")%></div>
							<div class="acciones"><a class="seguir" id="<%=resultado.getString("username")%>" href=#>Seguir</a></div>
							</div>
						</div>
						<br><br>
						
		<%}%>

		<%resultado.close();
		
		//LISTADO DE LOS AMIGOS
		resultado = conexion.executarSQL("SELECT u.username, u.name, u.surname, u.email, f.id_friendship "+
										"FROM user u, friendship f "+
										"WHERE u.username IN (SELECT fr.user_2 FROM friendship fr WHERE fr.user_1='"+username+"') "+
										"AND f.id_friendship IN (SELECT fr.id_friendship FROM friendship fr WHERE fr.user_1='"+username+"' AND fr.user_2 = u.username) "+
										"ORDER BY f.id_friendship;");%>
		
		<div class="espacio2"></div>
		<div class="headersClass" align="center">Amigos</div>
		
		<%
		while(resultado.next())
		{%>
		
				<div id="todosUsuarios">
					<div id="todosUsuarios2">
						<div class="nombreClass"><%=resultado.getString("username")%></div>
						<div><%=resultado.getString("name")%> <%=resultado.getString("surname")%></div>
						<div><%=resultado.getString("email")%></div>
						<div class="acciones"><a class="noseguir" id="<%=resultado.getString("id_friendship")%>" href=#>No Seguir</a> | <a class="vermuro" id="<%=resultado.getString("username")%>" href=#>Ver Muro</a></div>
					</div>
				</div>
						<br><br>					
		<%}%>
		
		<div class="espacio"></div>
		
	</div>
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