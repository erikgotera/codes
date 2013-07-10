<?php
////////////////// mail_borrar.php //////////////////
/*
- comprobar usuario
- eliminar el mensaje, comprobar que el mensaje corresponde al usuario
*/

//iniciamos la conexion con la bd
	include("./includes/conexionBD.php");
	
	
if(isset($_GET['id_men']))
		{
			//Borramos el mensaje
			
			$id_men=make_safe($_GET['id_men']);
			$query = sprintf("DELETE FROM mensajes WHERE id_mensaje=%s",$id_men);
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


include("./includes/FinconexionBD.php");
}
?>