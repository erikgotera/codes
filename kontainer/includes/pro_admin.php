<?php
////////////////// pro_admin.php //////////////////
/*
- comprobar datos con la funcion segura
- comprobar que la variable de sesion indique que pertenece al proyecto
- si eres admin tienes opciones de:
								agregar/borrar usuarios del proyecto
								listar usuarios del proyecto (con opcion para enviar mensajes)
								


*/

//mini menu de administrador de proyecto

//agregar usuarios
//listar usuarios -->redireccionar a listado
//borrar proyecto

//mensajes globales

if(isset($_GET['m']))
{
	switch($_GET['m'])
	{
		case 'agregar': include("includes/pro_admin_agregar.php"); break;
		case 'mail': include("includes/pro_admin_mail.php");break;
		case 'borrar': include("includes/pro_admin_borrar.php");break;
		
		default:include("includes/pro_menu_admin.php") ;break;
	}
}
else
{
//muestro el menu

?>
<div id=menuproyecto>

<p align="center"><b>MENU</b></p><br /><br />
<p align="center"><img src="images/usuario.png" align="middle"> <a href="proyecto.php?accion=admin&m=agregar">Agregar Usuarios al proyecto</a></p><br /><br />
<p align="center"><img src="images/usuario_listado.png" align="middle"> <a href="proyecto.php?accion=listar">Listar Usuarios del proyecto</a></p><br /><br />
<p align="center"><img src="images/circulares.png" align="middle"> <a href="proyecto.php?accion=admin&m=mail">Enviar Circulares</a></p><br /><br />
<p align="center"><img src="images/borrar_proyecto.png" align="middle"> <a href="proyecto.php?accion=admin&m=borrar">Eliminar Proyecto</a></p><br /><br />

<br /><br />
</div>

<?php
}
?>