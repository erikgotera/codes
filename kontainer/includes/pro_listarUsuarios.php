<?php
////////////////// pro_listarUsuarios.php //////////////////
/*
- listar usuarios del grupo
- Opcion de enviar mails


*/
	//si el admin quiere eliminar a alguien entra aki

	if($_SESSION['admin'])
	{
		if(isset($_GET['id_user']))
		{
			//lo borramos del grupo
			
			$id_grupo=make_safe($_GET['id_user']);
			$query = sprintf("DELETE FROM grupos WHERE id_grupo=%s",$id_grupo);
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
			
			//enviar mensaje
			//funcion enviar mensaje
			
			
			
			
			
			
		}
	}
	
	echo'<img src="images/usuario_listado.png" align="middle">';
	
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
	printf('<table align="center" ><tr><td colspan="3"><b>Usuarios del proyecto</b></td></tr>');
	while($row = mysql_fetch_array($result))
	{
		if($row['id_admin']==$row['user'])
		{
			$img="admin.png";
		}
		else
		{
			$img="usuario.png";
		}
		if($_SESSION['admin'] && $row['user']!=$_SESSION['user'])
		{
			
			printf('<tr><td><img src="images/%s" align="middle"></td><td><b>%s</b></td><td><a href="mail.php?accion=nuevo&id=%s"><img src="images/mail.png" align="middle" border="0"></a></td><td><a href="proyecto.php?accion=listar&id_user=%s"',$img,$row['user'],$row['user'],$row['id_grupo']);
			?>onclick="if(!confirm('ATENCION!!! Se borrará el usuario seleccionado del proyecto!!'))return false"<?php
			printf('><img src="images/delete_user.png" align="middle"></a></td></tr>');
		}
		else
		{
			printf('<tr><td><img src="images/%s" align="middle"></td><td><b>%s</b></td><td><a href="mail.php?accion=nuevo&id=%s"><img src="images/mail.png" align="middle" border="0"></a></td></tr>',$img,$row['user'],$row['user']);
		}
		
	}
	printf('</table>');




?>

