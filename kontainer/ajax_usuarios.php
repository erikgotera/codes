<?php
	include("includes/configuracion.php");
	include("./includes/conexionBD.php");

	$query = sprintf("select * from usuarios where user='%s'",$_GET['q']);
	if(DEBUG==true)
	{
		echo $query.'<br/>';
	}
	$result = @mysql_query($query, $conexion);
	if(!$result )
	{
		//echo 'NO';
		die();
	}
	else
	{
		//imagen bien
		//echo 'OK';
	}
	$row = mysql_fetch_array($result);
	if(!$row)
	{
		echo '<img src="images/icono_usuario_libre.png" align="left"  border="0" />';
		//echo'<div id="disponible">Usuario Disponible</div>';
	}
	else
	{

	echo'<img src="images/icono_usuario_ocupado.png" align="left"  border="0" />';
	//echo '<div id="nodisponible">Usuario No Disponible</div>';
	}
	include("./includes/FinconexionBD.php");

?>

