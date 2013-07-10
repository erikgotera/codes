<?php
	include("./includes/primero.php");
	//constantes de la web
	include("./includes/configuracion.php");
	
	//funcion de seguridad
	include("./includes/seguridad.php");
	
	include("./includes/head.php");
	//si la web esta online
	if(ONLINE)
	{
		
		//siempre se incluye el menu del usuario
		include("./includes/menu_usuario.php");
		
		?><div id="textocontenido"><?php
		//dependiendo de la accion
		switch ($_GET['accion'])
		{
			//para crear un proyecto nuevo.
			case 'crear' : include("./includes/home_crearProyecto.php");break;
			//para borrar un proyecto que se es propietario.
			case 'borrar' : include("./includes/home_borrarProyecto.php");break;
			
			//por defecto se muestra el listado de proyectos del usuario.
			default : include("./includes/home_listarProyectos.php");break;
		}
	

	?></div><?php
	}
	else
	{
		header("location: mantenimiento.php");
	}
	include("./includes/footer.php");
	include("./includes/ultimo.php");
	
?>