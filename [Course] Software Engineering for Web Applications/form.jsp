<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.BeanUser" %>
   

 <script>
 $(document).ready(function(){
   $("#registerForm").validate({
	   submitHandler: function(form) {
   		$('#content').load('formcontroller',$("#registerForm").serialize());
   	},
	   rules: {
		   user: {
			   required: true,
			   minlength: 2
		   },		   
		   mail: {
		       required: true,
		       email: true
		     },
		   pass: {
			   required: true,
			   minlength: 5
		   },
		   pass_again: {
			   equalTo: "#pass" 
		   },
		   birthday: {
			      required: true,
			      date: true
			    }
	   },
		   messages: {
			 username: {
				 required:"Please specify your name",
				 minlength: "Please enter at least 2 characters" 
			 }, 
		     mail: {
		       required: "We need your email address to contact you",
		       email: "Your email address must be in the format of name@domain.com"
		     },
			   pass: {
				   required: "This field is required.",
				   minlength: "Please enter at least 5 characters"
			   },				   
		   }
		});

 });
 </script>
 

<% 
BeanUser user = null;
if (request.getAttribute("user")!=null) {
	user = (BeanUser)request.getAttribute("user");
}
else {
	user = new BeanUser();
}
%>
<div id="registerR">
<div class="headersClass" align="center">Registro de usuario</div>
<div class="espacio2"></div>
<form id=registerForm action="/Practica4/formcontroller" method="POST">
<table>
<tr> 
<td>Name:</td> 
<td> <input id="name" name="name" value="<%=user.getName() %>" size="29"/> </td> 
</tr>
<tr> 
<td>Surname:</td> 
<td> <input id="surname" name="surname" value="<%=user.getSurname() %>" size="29"/> </td> 
</tr>
<tr> 
<td>Username:</td> 
<td> <em>*</em><input id="user" name="user" value="<%=user.getUser() %>" size="25"/> </td> 
<% 	
		if ( user.getError()[0] == 1) {
			%> 
			<td class="error"> The username already exists in our DB! </td>  
			<% 
		}
	%>
</tr>
<tr> 
<td>Password:</td> 
<td> <em>*</em><input id="pass" type="password" name="pass" size="25"/> </td> 
</tr>
<tr> 
<td>Confirm Password:</td> 
<td> <em>*</em><input id="pass_again" type="password" name="pass_again" size="25"/> </td> 
</tr>
<tr> 
<td>E-Mail:</td> 
<td> <em>*</em><input id="mail"name="mail" value ="<%=user.getMail() %>" size="25"/> </td> 
</tr>
<tr> 
<td>Sex:</td> 
<td> <select id="sex" name="sex" >
	  <option value="men">Men</option>
	  <option value="woman">Woman</option>
	</select>  </td> 
</tr>
<tr> 
<td>Birthday:</td> 
<td> <em>*</em><input name="birthday" type="text" id="birthday" size="25" />  </td> 
</tr>   
<tr><td><em>*Required fields</em></td></tr>
<tr><td><input class="reset" type="reset" value="Reset"/></td><td><input class="submit" type="submit" value="Submit"/></td></tr>    
</table>
</form>
<div class="espacio"></div>
</div>