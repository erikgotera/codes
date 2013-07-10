<?php
///////////////// pro_admin_agregar.php /////////////////
/*
- agrega usuarios al proyecto


*/

if(isset($_POST['agregar']))
{
		$user=make_safe($_POST['usuario']);
		$query = sprintf("INSERT INTO grupos (user,id_proyecto) VALUES ('%s',%s)",$user,$_SESSION['id_proyecto']);
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
		echo 'usuario agregado con exito';
		///enviar mensaje al usuario
		
}
else
{
	?>
	<form action="proyecto.php?accion=admin&m=agregar" method="post" >
	<img src="images/usuario.png" align="middle"> Usuario: <br/><input type="text" name="usuario"> <br/><input type="submit" value="Agregar Usuario" name="agregar">
	
	</form>
	
	
	<?php
}

?>