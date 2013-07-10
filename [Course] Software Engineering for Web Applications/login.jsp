<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.BeanLogin" %>

<script>
$(document).ready(function(){
    $("#loginForm").validate({
    	submitHandler: function(form) {
    		$('#content').load('logincontroller',$("#loginForm").serialize());
    	}
    });
});
</script>

<% 
BeanLogin login = null;
if (request.getAttribute("login")!=null) {
	login = (BeanLogin)request.getAttribute("login");
}
else {
	login = new BeanLogin();
}
%>


<div id="loginF">
<div class="headersClass" align="center">Introduzca su usuario y contraseña</div>
<div class="espacio2"></div>
<form id=loginForm action="/Practica4/logincontroller" method="POST">
<table>
<tr> 
<td>Username:</td> 
<td> <input type="text" name="user" value="<%=login.getUser() %>" id="user" class="required" minlength="2"/> </td> 
<% 	
	if ( login.getError()[0] == 1) {
		%> 
		<td class="error"> The username not exists in our DB! </td>  
		<% 
	}
%>
</tr>
<tr>
<td>Password:</td>
<td><input id="pass" type="password" name="pass"  class="required" /></td>
<% 	
	if ( login.getError()[0] == 2) {
		%> 
		<td class="error"> The password is not correct! </td>  
		<% 
	}
%>
</tr>
<tr> 
<td> <input name="submit" type="submit" value="Enviar"> </td>
</tr>
</table>
</form>
<div class="espacio"></div>
</div>