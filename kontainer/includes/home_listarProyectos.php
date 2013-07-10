<?php
////////////////// home_listarProyectos.php //////////////////
/*
- listar todos los proyectos del usuario (tabla grupo) con imagen y titulo del proyecto
- linkear los proyectos a: proyecto.php?id=id_proyecto


*/

//listo los proyectos del usuario

	include("./includes/conexionBD.php");

	$query = sprintf("SELECT * FROM grupos g INNER JOIN proyectos p ON g.id_proyecto=p.id_proyecto WHERE g.user='%s' ORDER BY p.id_proyecto",$_SESSION['user']);
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
	printf('<table align="center">');
	while($row = mysql_fetch_array($result))
	{
		printf('<tr><td><a href="proyecto.php?id=%s"><img src="./logos/%s" border="0"></td><td><a href="proyecto.php?id=%s">%s</a></td><tr>',$row['id_proyecto'],$row['logo'],$row['id_proyecto'],$row['nombre']);
	}
	printf('</table>');
	
	
	
 	include("./includes/FinconexionBD.php");


?>