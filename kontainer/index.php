<?php
	include("./includes/primero.php");
	//constantes de la web
	include("./includes/configuracion.php");
	
	include("./includes/head.php");
	//si la web esta online
	if(ONLINE)
	{
		//formulario de login
		
		
		if(isset($_POST['user']))
		{
			//comprobamos si el usuario esta registrado
			//se le redirecciona a home.php
			
			include("./includes/login_acceso.php");
	
	
		}
		else
		{
			//muestro el formulario
			include("./includes/login_formulario.php");
			
			
		}
	

	}
	else
	{
		header("location: mantenimiento.php");
	}
	include("./includes/ultimo.php");

include("./includes/footer.php");
?>