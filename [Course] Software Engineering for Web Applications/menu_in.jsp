<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function() {
    $(".menu").click(function(event) {
        $('#content').load('Content',{content: $(this).attr('id')});
    });
});
</script>


<table>
<tr>
<td> <a class="menu" id="muro.jsp" href=#> Principal </a> </td>
<td> <a class="menu" id="muropersonal.jsp" href=#> Mi Muro</a> </td>
<td> <a class="menu" id="usuarios.jsp" href=#> Usuarios</a> </td>
<td> <a class="menu" id="perfil.jsp" href="#"> Perfil </a> </td>
<%session = request.getSession(false);
int admin = (Integer) session.getAttribute("admin");
if (session !=null && admin == 1) {
%>
<td> <a class="menu" id="adminUser.jsp" href="#"> Administrar Usuarios </a> </td>
<td> <a class="menu" id="adminTweet.jsp" href="#"> Administrar Tweets </a> </td>	
<% }%>

<td><a class="menu" id="logout.jsp" href="#"> Logout </a> </td>
</tr>
</table>	


