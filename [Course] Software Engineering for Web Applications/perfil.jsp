<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<script>
 $(document).ready(function(){ 
     $("#imageForm").validate({ 
     	submitHandler: function(form) { 
     		$('#content').load('UploadImage',$("#imageForm").serialize()); 
     	} 
     }); 
     $("#perfilEdit").validate({ 
      	submitHandler: function(form) { 
      		$('#content').load('editProfile',$("#perfilEdit").serialize()); 
      	} 
      }); 
 }); 
</script> 

		       <%session = request.getSession(false);
		       String username = (String)session.getAttribute("username");
		       String name = (String)session.getAttribute("name");
		       String surname = (String)session.getAttribute("surname");
		       String mail = (String)session.getAttribute("email");
		       String sex = (String)session.getAttribute("sex");
		       String birthday = (String)session.getAttribute("birthday");
		       String image = (String)session.getAttribute("image");
		       
		       %>
	       <div id="loginOK">
		       <div id="cabeceraLoginOK">
		       		Perfil de <%=username%>
		       </div>
		       
		       <div id="imageUser">
		       

					<%if(image != null){ %>
					<img src="<%=image%>" height="150px" width="150px"/>
					<%}else{ %>
					<img src="img/NO-IMAGE-AVAILABLE.jpg" height="150px" width="150px" />
					<%} %>
					<div ><a id="cambiarImage" href="#" onclick="cambiar('formImage'); return false;">Cambiar Imagen</a></div>
					<div id="formImage" style="display: none;">
					
					<form action="" method="post" name="imageForm" id="imageForm">
					Paste URL image: <input id="newimage" name="newimage" value="<%=image%>"><br>
					(Puedes utilizar las siguientes web para subir imagenes: <a href="http://tinypic.com/" TARGET="_blank">tinypic</a> o <a href="http://www.subirimagenes.com/" TARGET="_blank">subirimagenes</a>)<br>
					<input type="submit" value="upload" />
					</form>
					
					</div>
		       </div>
		       
		       <div id="tablaUser">
			    <table  id="tablaInfoUser">
			       <tr class="row1"> 
			       <td>Name</td> 
			       <td>Surname</td> 
			       <td>Username</td> 
			       <td>Email</td> 
			       <td>Sex</td> 
			       <td>Birthday</td>
			       <td>Acciones</td>
			       </tr>
					<tr>
					<td><%=name%></td>
					<td><%=surname%></td>
					<td><%=username%></td>
					<td><%=mail%></td>
					<td><%=sex%></td>
					<td><%=birthday%></td>
					<td><a id="editarPerfil" href="#" onclick="cambiar('editPerfil'); return false;">Editar</a></td></tr>
					<tr>
					<td colspan="7">
					<div id="editPerfil" style="display: none;">	
						<form action="/Practica4/editProfile" method="POST" name="perfilEdit" id="perfilEdit">
						<table>
							<tr><td colspan="7" align="center">Edit Profile</td></tr>
							<tr> 
								<td><input id="name" name="name" value="<%=name%>" /> </td>
								<td><input id="surname" name="surname" value="<%=surname%>" /> </td>
								<td><%=username%><input type="hidden" id="username" name="username" value="<%=username%>"></td> 
								<td><input id="mail" name="mail" value="<%=mail%>" class="required email" /> </td> 
								<td>
									<select id="sex" name="sex" >
									  <option value="men">Men</option>
									  <option value="woman">Woman</option>
									</select> 
								</td> 
								<td><input id="birthday" name="birthday" value="<%=birthday%>" class="required date" /> </td>  
							</tr>
							<tr><td colspan="7" align="center"><input class="submit" type="submit" value="Editar"/></td></tr>    
						</table>
						</form>
					</div>
					</tr>
					</table>
				</div>
				<div class="espacio"></div>
			</div>
