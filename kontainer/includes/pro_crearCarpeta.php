<?php
////////////////// pro_crearCarpeta.php //////////////////
/*
- comprobar datos con la funcion segura
- comprobar que la variable de sesion indique que pertenece al proyecto
- crear carpetas en el directorio del proyecto (nombre de la carpeta: id_proyecto)
- verificar si antes la carpeta no existe
- limitar el maximo de carpetas permitidas
								


*/



if(isset($_POST['nombreCarpeta']))
{
	if($_POST['nombreCarpeta']!="")
	{
		$nombreCarpeta=make_safe($_POST['nombreCarpeta']);
		$enlace=$_SESSION['dir'].$nombreCarpeta;
		mkdir($enlace);
	}
	
}

?>