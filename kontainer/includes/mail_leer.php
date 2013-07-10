<?php
////////////////// mail_leer.php //////////////////
/*
- muestra el mensaj que se le pasa por get
- comprobar que el mensaje pertenece al usuario
- opcion de responder
*/

	//iniciamos la conexion con la bd
	include("./includes/conexionBD.php");

	$query = sprintf("SELECT * FROM mensajes WHERE id_mensaje='%s'", $_GET['id']);
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
?>

<table border="0px" align="center" width="600px">
		<tr><td colspan="2" align="center"><h3><u><?php echo $row['asunto'] ?></u></h3><br/></td></tr>
		<tr><td align="right"><b>De:</b></td><td> <?php echo $row['id_rem'] ?></td></tr>
		<tr><td align="right"><b>Para:</b></td><td><?php echo $row['id_dest'] ?></td></tr>
		<tr><td align="right"><b>Fecha:</b></td><td><?php echo $row['fecha'] ?></td></tr>
		<tr><td align="right" valign="top"><b>Mensaje:</b></td><td><textarea readonly="readonly" id="textnew" rows="16" name="texto"> <?php echo $row['texto'] ?> </textarea></td></tr>
		<tr><td colspan="2" align="center"><a href="mail.php?accion=nuevo&id=<?php echo $row['id_mensaje'] ?>"><button>Responder</button></a> <a href="mail.php?id_men=<?php echo $row['id_mensaje'] ?>" onclick="if(!confirm('ATENCION!!! Se borrará el mensaje seleccionado!!!'))return false"><button>Eliminar</button></a></td></tr>
		</table>
		
<?php
include("./includes/FinconexionBD.php");
?>