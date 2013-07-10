<?php
////////////////// mail_nuevo.php //////////////////
/*
- formulario para enviar el mail (destinatario, asunto, texto)
- insertar registro en la bd

*/
if(isset($_POST['asunto']))
{
	///Si recibe el asunto del mensaje pues se procede a crearlo
	
	$fecha= date( "H:i d/m/y");
	///Guardamos en $fecha la hora y fecha que se creo el mensaje
	
	
	
	//iniciamos la conexion con la bd
	include("./includes/conexionBD.php");
	
	//vuelvo seguros todos los parametros del post
	$dest=make_safe($_POST['dest']);
	$texto=make_safe($_POST['texto']);
	$asunto=make_safe($_POST['asunto']);
	
	if($asunto == "")
	{
		$asunto="[ Sin asunto ]";
	}
	
	$query = sprintf("INSERT INTO mensajes (id_rem,id_dest,texto,asunto,fecha) VALUES ('%s','%s','%s','%s','%s')",$_SESSION['user'],$dest,$texto,$asunto,$fecha);
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

	//finalizamos la sesion con la bd
	include("./includes/FinconexionBD.php");
	
	//listamos los proyectos
	
	include("./includes/mail_listado.php");
}
else
{
	//si no se ha introducido aun el asunto del mensaje.
?>

	<form method='post' action='mail.php?accion=nuevo' enctype='multipart/form-data'>
	<table border="0px" align="center" width="600px">
	<tr><td colspan="2" align="center"><h3>NUEVO MENSAJE</h3><br/></td></tr>
	
	<?php
if(isset($_GET['id']))
	{
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
	
		if( $_SESSION['user'] == $row['id_dest'])
		{
			$para=$row['id_rem'];
		}
		else
		{
			$para=$row['id_dest'];
		}
	?>
		<tr><td align="right"><b>De:</b></td><td> <?php echo $_SESSION['user'] ?></td></tr>
		<tr><td align="right"><b>Para:</b></td><td><input type="text" name="dest" value="<?php echo $para ?>" /></td></tr>
		<tr><td align="right"><b>Asunto:</b></td><td><input type="text" name="asunto" maxlength="30" size="50px" value="Re: <?php echo $row['asunto'] ?>" /></td></tr>
		<tr><td align="right" valign="top"><b>Mensaje:</b></td><td><textarea id="textnew" rows="16" name="texto">             ---------------------------------------------------- <?php echo $row['texto'] ?>
		</textarea></td></tr>
	
	
<?php
	//finalizamos la sesion con la bd
	include("./includes/FinconexionBD.php");
	}
	else
	{?>
		<tr><td align="right"><b>De:</b></td><td> <?php echo $_SESSION['user'] ?></td></tr>
		
		<?php
		if(isset($_GET['para']))
		{
		?>
			<tr><td align="right"><b>Para:</b></td><td><?php $_GET['para'] ?></td></tr>
		<?php
		}
		else
		{
		?>
		<tr><td align="right"><b>Para:</b></td><td><input type="text" name="dest" /></td></tr>
		<?php
		}
		?>
		<tr><td align="right"><b>Asunto:</b></td><td><input type="text" name="asunto" maxlength="30" size="50px" /></td></tr>
		<tr><td align="right" valign="top"><b>Mensaje:</b></td><td><textarea id="textnew" rows="16" name="texto"> </textarea></td></tr>
			
	<?php	
	}
	?>
	<tr><td colspan="2" align="center"><input type="submit" name="enviar" value="Enviar"> <button><a href="mail.php" style="color:#000000;">Cancelar</a></button>  </td></tr>
	</table>
	</form>	
<?php	
}
?>