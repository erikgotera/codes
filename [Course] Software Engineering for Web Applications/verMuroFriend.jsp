<script type="text/javascript">
$(document).ready(function() {
    $(".volver").click(function(event) {
        $('#content').load('usuarios.jsp');
    });
});
</script>
<%@ page import="dbaccess.DAO" %>
<%@ page import="java.sql.*" %>  

<%
String user = (String)request.getParameter("id");

ResultSet resultado = null;
DAO conexion;
try {
	conexion = new DAO();

	try {
		resultado = conexion.executarSQL("SELECT * FROM tweet where user_id='"+user+"'");%>
		<div id="muroPrincipal">
		
		<div class="espacio2"></div>
		
		<table border="0" id="tablaMuroFriend">

		
		<%
		while(resultado.next())
		{%>
		
				<tr><td><div id="tweetUser"><%=resultado.getString("user_id")%></div></td></tr>
				<tr><td><%=resultado.getString("text")%></td></tr>
				<tr><td><div id="tweetTimestamp"><%=resultado.getString("timestamp")%></div></td></tr>
				<tr><td colspan="3"><hr></td></tr>
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

<div align="center"><a class="volver" href="#">Volver</a></div>
<div class="espacio"></div>
</div>