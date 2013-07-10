<?php
////////////////// mail_enviados.php //////////////////
/*
- comprobar usuario
- listado de los mensajes enviados
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

	$query = sprintf("SELECT * FROM mensajes WHERE id_rem='%s' ORDER BY id_mensaje",$_SESSION['user']);
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
			echo '<h3 align="center"> No hay ningún mensaje en Enviados </h3>';
			$entro=true;
		}
		else
		{
		printf ('<form method="post" action="mail.php?accion=enviados" enctype="multipart/form-data">');
		
		printf ('<table id="tab_mens" align="center"> <tr><td colspan="4" align="center" style="border:none;"><h3>MENSAJES ENVIADOS</h3></td></tr> <tr id="cabecera"><td></td><td>Para</td><td>Asunto</td><td>Fecha</td></tr>'); 
			
			while($row = mysql_fetch_array($result))
			{
				printf('<tr><td><input type="checkbox" name="campos[%s]" /> </td><td>%s</td><td><a href="mail.php?accion=leer&id=%s">%s</a></td><td>%s</td></tr>',$row['id_mensaje'],$row['id_dest'],$row['id_mensaje'],$row['asunto'],$row['fecha']);
				
			}?>
		<tr><td colspan="4" style="border:none;"><img align="left" src="./images/arrow_turn_left.png" /><input type="submit" name="eliminar" value="Eliminar" onclick="if(!confirm('ATENCION!!! Se borrarán los mensajes seleccionados!!!'))return false"></td></tr></table></form>
<?php
include("./includes/FinconexionBD.php"); 
		}
?>