<?php
////////////////// mail_listado.php //////////////////
/*
- lista todos los mensajes por el asunto y el remitente
- al hacer click te redirige a mail_leer con el id del mail

*/

 
 if(!empty($_POST['campos'])) 
 	{
		include("./includes/conexionBD.php");
		
   		$lista=array_keys($_POST['campos']);
   		$query="DELETE FROM mensajes where id_mensaje IN (".implode(',',$lista).")";
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





///Listo los mensajes del usuario
	include("./includes/conexionBD.php");

	$query = sprintf("SELECT * FROM mensajes WHERE id_dest='%s' ORDER BY id_mensaje",$_SESSION['user']);
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
	$filas= mysql_num_rows($result);
	
	if($filas == 0)
		{
		////Si no devuelve nada la consulta 
			echo '<h3 align="center"> No hay ning�n mensaje en la Bandeja de Entrada </h3>';
			$entro=true;
		}
		else
		{
		printf ('<form method="post" action="mail.php" enctype="multipart/form-data">');
		
		printf ('<table id="tab_mens" align="center"> <tr><td colspan="4" align="center" style="border:none;"><h3>BANDEJA DE ENTRADA</h3></td></tr> <tr id="cabecera"><td></td><td>De</td><td>Asunto</td><td>Fecha</td></tr>'); 
			
			while($row = mysql_fetch_array($result))
			{
				printf('<tr><td><input type="checkbox" name="campos[%s]" /> </td><td>%s</td><td><a href="mail.php?accion=leer&id=%s">%s</a></td><td>%s</td></tr>',$row['id_mensaje'],$row['id_rem'],$row['id_mensaje'],$row['asunto'],$row['fecha']);
				
			}
		?>
		<tr><td colspan="4" style="border:none;"><img align="left" src="./images/arrow_turn_left.png" /><input type="submit" name="eliminar" value="Eliminar" onclick="if(!confirm('ATENCION!!! Se borrar�n los mensajes seleccionados!!!'))return false"></td></tr></table></form>
<?php

include("./includes/FinconexionBD.php"); 
		}
		

?>
