<?php
	include("./includes/primero.php");
	//constantes de la web
	include("./includes/configuracion.php");
	
	include("./includes/head.php");
	//si la web esta online
	if(ONLINE)
	{
		//si esta habilitado el registro de usuarios
		if(REGISTRO)
		{			
			
			if(isset($_POST['user']))
			{
				
				//registro al usuario
				
				if ($_SESSION['tmptxt'] == $_POST['tmptxt']) 
				{
					include("./includes/conexionBD.php");
					//vuelvo seguros todos los parametros del post
					$user=make_safe($_POST['user']);
					$pass=make_safe($_POST['password']);
					$nombre=make_safe($_POST['nombre']);
					$apellido=make_safe($_POST['apellido']);
					$mail=make_safe($_POST['mail']);
					//encriptamos la contraseña
					$pass= md5 ($pass); 
					
					//el nivel 2 es el de los usuarios, el 1 corresponde al root
					$query = sprintf("INSERT INTO usuarios (user,pass,nombre,apellido,mail,nivel) VALUES ('%s','%s','%s','%s','%s',2)",$user,$pass,$nombre,$apellido,$mail);
					if(DEBUG==true)
					{
						echo $query.'<br/>';
					}
					$result = @mysql_query($query, $conexion);
					if(!$result )
					{
						echo 'Nombre de usuario en uso.';
						die();
					}
						
					include("./includes/FinconexionBD.php");
					
					//envio de mail de comunicacion.
					
					include("./includes/enviar_mail.php");


				}
				else
				{
					header("location: registro.php");
				}
				
				
				
				
				
			}
			else
			{
				
			
				//enseño el formulario
				// incluir captcha
				?>
				<script language = "javascript">
function createRequestObject(){
      var peticion;
      var browser = navigator.appName;
            if(browser == "Microsoft Internet Explorer"){
                  peticion = new ActiveXObject("Microsoft.XMLHTTP");
            }else{
                  peticion = new XMLHttpRequest();
}
return peticion;
}
var http = new Array();
function ObtDatos(url){
      var act = new Date();
      http[act] = createRequestObject();
      http[act].open('get', url);
      http[act].onreadystatechange = function() {
      if (http[act].readyState == 4) {
            if (http[act].status == 200 || http[act].status == 304) {
  		var texto
		texto = http[act].responseText
                    var DivDestino = document.getElementById("DivDestino");
                    DivDestino.innerHTML = "<div id='error'>"+texto+"</div>";                
}
}
}
http[act].send(null);
}
function compUsuario(Tecla) {
     Tecla = (Tecla) ? Tecla: window.event;
     input = (Tecla.target) ? Tecla.target : 
     Tecla.srcElement;
     if (Tecla.type == "keyup") {
          var DivDestino = document.getElementById("DivDestino");
          DivDestino.innerHTML = "<div></div>";
          if (input.value) {
               ObtDatos("ajax_usuarios.php?q=" + input.value);
          } 
     }
}
</script>
				
				
	<form action="registro.php" method="post" onsubmit="return comprobar(this)">
	<table border="0px" align="center" >
	<!--<tr><img src="./images/kontainer.jpg" /> </tr>-->
	<div id="footer"><br /></div>
	<tr><td align="center" colspan="2"><h3>Rellena este formulario para registrarte.</h3><br/></td></tr>
	<tr><td>Nombre de usuario:</td><td> <input type="text" name="user" id="user" onkeyup = "compUsuario(event)"></td><td width="16px"><div  id = "DivDestino"></div><br/></td></tr>
	<tr><td>Contraseña: </td><td><input type="password" name="password" id="password"><br/></td><td></td></tr>
	<tr><td>Nombre: </td><td><input type="text" name="nombre" id="nombre"><br/></td><td></td></tr>
	<tr><td>Apellido: </td><td><input type="text" name="apellido" id="apellido"><br/></td><td></td></tr>
	<tr><td>E-mail: </td><td><input type="text" name="mail" id="mail"><br/></td><td></td></tr>
	<tr><td align="center" colspan="2"><img src="captcha.php" width="100" height="30" vspace="3"><br></td><td></td></tr>
	<tr><td align="center" colspan="2"><input name="tmptxt" type="text" maxlength="8" size="8" style="font-size:18px"><br></td><td></td></tr>
	<tr><td align="center" colspan="2"><input type="submit" name="registro" value="Registrarse"> <button><a href="./index.php" >Volver</a></button> </td><td></td></tr>
	</table>
	
	
	</form>
	<div id="footer"><br /></div>
	<?php
				
				
				
			}
		
		}
		else
		{
			echo 'El registro de usuarios no esta activado en estos momentos.' ;
		}
	
		
		include("./includes/footer.php");
		
	}
	else
	{
		header("location: mantenimiento.php");
	}
	include("./includes/ultimo.php");
		
?>