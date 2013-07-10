<?php
////////////////// comprobarGrupo.php //////////////////
/*
- comprobar si tiene un id
- comprobar que el usuario esta en el grupo
- si tiene agregarlo a la variable de sesion

*/

if($_GET['id'])
{
	$id_proyecto=make_safe($_GET['id']);
	$query = sprintf("SELECT * FROM grupos g INNER JOIN proyectos p ON g.id_proyecto=p.id_proyecto WHERE g.user='%s' AND g.id_proyecto=%s",$_SESSION['user'],$id_proyecto);
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
	$row = mysql_fetch_array($result);
	if(!$row) header("location:error.php");
	else 
	{
		//si esta seleccionado
		$_SESSION['id_proyecto']=$id_proyecto;
		$_SESSION['logoProyecto']=$row['logo'];
		$_SESSION['nombreProyecto']=$row['nombre'];
		
		//compruevo si es el admin del proyecto
		
		$query = sprintf("SELECT * FROM proyectos WHERE id_admin='%s' AND id_proyecto=%s",$_SESSION['user'],$id_proyecto);
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
		$row = mysql_fetch_array($result);
		if($row) 
		{
			$_SESSION['admin']=true;
		}
		
		
		
		
	}
	
}





?>