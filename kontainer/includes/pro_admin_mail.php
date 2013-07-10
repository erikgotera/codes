<?php /////////////////////// pro_admin_mail.php ///////////////
/*
- enviar mensajes a todo el grupo
*/
echo'<img src="images/circulares.png" align="middle"> Circulares<br/>';

if(isset($_POST['enviar']))
{
	$text=make_safe($_POST['texto']);
	$id_proyecto=$_SESSION['id_proyecto'];
	$query = sprintf("SELECT * FROM grupos g INNER JOIN proyectos p ON g.id_proyecto=p.id_proyecto WHERE g.id_proyecto=%s",$id_proyecto);
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
	//envio los mensajes a cada usuario del proyecto
	while($row = mysql_fetch_array($result))
	{
		$query2 = sprintf("INSERT INTO mensajes (id_rem,id_dest,asunto,texto) VALUES ('%s','%s','%s','%s')",$_SESSION['user'],$row['user'],$asunto,$texto);
		if(DEBUG==true)
		{
			echo $query.'<br/>';
		}
		$result2 = @mysql_query($query2, $conexion);
		if(!$result2 )
		{
			echo 'error.';
			die();
		}
		echo 'Mensaje enviado a: '.$row['user'].'.<br/>';
	
	}

}
else
{
	//formulario para enviar mensajes circulares
	?>
	
	<form action="proyecto.php?accion=admin&m=mail" method="post">
	Asunto: <input type="text" name="asunto" /><br />
	Texto: <textarea name="texto"> </textarea><br />
	<input type="submit"  name="enviar" value="Enviar mensaje a todo el grupo" />
	
	
	</form>
	
	
	
	<?php
	
	
	
	
	
			
}







?>