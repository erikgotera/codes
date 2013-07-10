<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%session = request.getSession(false);
if (session !=null && session.getAttribute("username")!=null) {
%>
<script type="text/javascript">
$(document).ready(function() {
        $('#navigation').load('menu_in.jsp');    
});
</script>	
<% }
else {%>
<script type="text/javascript">
$(document).ready(function() {
        $('#navigation').load('menu_out.jsp');    
});
</script>	
<%}; %>


<%@ page import="dbaccess.DAO" %>
<%@ page import="java.sql.*" %>  


<%
ResultSet resultado = null;
int cont = 0;
DAO conexion;
try {
	conexion = new DAO();

	try {
		resultado = conexion.executarSQL("SELECT * FROM tweet ORDER BY idtweet DESC");%>
		
		<div id="muroPrincipal">
		
		<div class="espacio2"></div>
		
		<table border="0" id="tablaMuroPrincipal">
		
		<%
		while(cont < 10)
		{
			if(resultado.next()) {%>
			
				<tr><td><div id="tweetUser"><%=resultado.getString("user_id")%></div></td></tr>
				<tr><td><%=resultado.getString("text")%></td></tr>
				<tr><td><div id="tweetTimestamp"><%=resultado.getString("timestamp")%></div></td></tr>
				<tr><td colspan="3"><hr></td></tr>
			<%}
			else
			{
				cont = 10;
			}
			cont ++;
		}%>
		
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