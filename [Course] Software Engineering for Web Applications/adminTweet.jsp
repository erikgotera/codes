<script type="text/javascript">
$(document).ready(function() {
    $(".borrar").click(function(event) {
        $('#content').load('borrarTweetcontroller',{idadmin: $(this).attr('id')});
    });
});
</script>

<%@ page import="dbaccess.DAO" %>
<%@ page import="java.sql.*" %>  

<%

ResultSet resultado = null;
DAO conexion;
try {
	conexion = new DAO();

	try {
		resultado = conexion.executarSQL("SELECT * FROM tweet ORDER BY idtweet DESC");%>
		<div id="muroPrincipal">
		
		<div class="espacio2"></div>
		
		<table border="0" id="tablaAdminTweet">
		
		<%
		while(resultado.next())
		{%>
		
				<tr><td><div id="tweetUser"><%=resultado.getString("user_id")%></div></td></tr>
				<tr><td><%=resultado.getString("text")%></td></tr>
				<tr><td><div id="tweetTimestamp"><%=resultado.getString("timestamp")%></div></td><td><a class="borrar" id="<%=resultado.getString("idtweet")%>" href=#>Borrar</a></td></tr>
				<tr><td colspan="3"><hr></td></tr>
				
				
		<%}%>
		
		</table>
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