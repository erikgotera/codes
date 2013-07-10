<script type="text/javascript">
$(document).ready(function() {
    $(".eliminar").click(function(event) {
        $('#content').load('EliminarUser',{id: $(this).attr('id')});
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
		resultado = conexion.executarSQL("SELECT u.username, u.name, u.surname, u.email FROM user u WHERE u.username <>'"+username+"'");%>
		
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
						<div class="acciones"><a class="eliminar" id="<%=resultado.getString("username")%>" href=#>Eliminar usuario</a></div>
					</div>
				</div>
				<br><br>						
		<%}%>
		
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