<?php
////////////////// pro_borrarArchivos.php //////////////////
/*
- comprobar datos con la funcion segura
- comprobar que se le pase la id del archivo a borrar
- comprobar que la variable de sesion indique que pertenece al proyecto
- comprobar que exista el archivo
- borrar arhivo
- en caso de error redireccionar a error.php

*/

if(isset($_GET['id_arch']))
{
	$nombreArchivo=make_safe($_GET['id_arch']);
	$enlace=$_SESSION['dir'].$nombreArchivo;
	if (!unlink($enlace))
	{
		header("location: error.php");
	} 
	else
	{
		header("location: proyecto.php");
	}
}
else
{
	header("location: error.php");
}

?>