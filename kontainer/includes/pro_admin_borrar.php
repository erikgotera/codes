<?php
///////////////// pro_admin_borrar.php /////////////////
/*
- borra el contenido de la carpeta del proyecto
- borra los datos de la bd relacionados con el proyecto(grupos,proyecto, mensajes no)


*/

if($_POST['eliminar'])
{

//compruevo si eres el admin
if($_SESSION['admin'])
{
	////////////////////// elimino los archivos de la carpeta /////////////////////
	$carpeta = RUTA.$_SESSION['id_proyecto'].'\\';
	
	////////////////////////////////////////////
	//función que elimina recursivamente todo el contenido de un directorio dado
	function eliminar_recursivo_contenido_de_directorio( $carpeta ){
	$directorio = opendir($carpeta);
	while ($archivo = readdir($directorio)){
	if( $archivo !='.' && $archivo !='..' ){
	//comprobamos si es un directorio o un archivo
	if ( is_dir( $carpeta.$archivo ) ){
	//si es un directorio, volvemos a llamar a la función para que elimine el contenido del mismo
	eliminar_recursivo_contenido_de_directorio( $carpeta.$archivo.'\\' );
	rmdir($carpeta.$archivo); //borrar el directorio cuando esté vacío
	} else {
	//si no es un directorio, lo borramos
	unlink($carpeta.$archivo);
	}
	}
	}
	closedir($directorio);
	}
	//////////////////////////////////////////////////////////////////
	
	
	eliminar_recursivo_contenido_de_directorio ($carpeta);
	rmdir($carpeta);
	
	
	////////////////////////////////////////////////////////
	
	/////// elimino registros del proyecto en la bd
	
	//elimino el proyecto
	$query = sprintf("DELETE FROM proyectos where id_proyecto=%s",$_SESSION['id_proyecto']);
	if(DEBUG==true)
	{
		echo $query.'<br/>';
	}
	$result = @mysql_query($query, $conexion);
	if(!$result )
	{
		echo 'error.';
		die();
	}
	//elimino los grupos
	$query = sprintf("DELETE FROM grupos WHERE id_proyecto=%s",$_SESSION['id_proyecto']);
	if(DEBUG==true)
	{
		echo $query.'<br/>';
	}
	$result = @mysql_query($query, $conexion);
	if(!$result )
	{
		echo 'error.';
		die();
	}
	
	
	header("location: salir_proyecto.php");
}
else
{
	header("location: error.php");
}


}
else
{
	?>
	<form action="proyecto.php?accion=admin&m=borrar" method="post">
	<img src="images/borrar_proyecto.png" align="middle"><br /><b>ATENCION!!!<br /> SE BORRARA TODO LA INFORMACION RELACIONADA CON EL PROYECTO: "<?php echo $_SESSION['nombreProyecto'];?>"<br />
	Esta accion no se podra desacer, Esta seguro que quiere continuar???</b>
	<input type="submit" value="Si, Deseo eliminar todo el proyecto" name="eliminar" />
	
	
	
	
	</form>
	
	
	
	<?php
}



?>