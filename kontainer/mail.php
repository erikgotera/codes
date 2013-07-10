<?php
	include("./includes/primero.php");
	//constantes de la web
	include("./includes/configuracion.php");
	
	//funcion de seguridad
	include("./includes/seguridad.php");
	
	include("./includes/head.php");
	//si la web esta online
	
		if(ONLINE)
		{
		
			//siempre se incluye el menu del usuario
			include("./includes/menu_usuario.php");
?>

<div id="navigation">
		<br /><img src="./images/mail_receive.png"/> <a href="mail.php">Bandeja de Entrada</a><br /><br />
		<img src="./images/kwrite.png"/> <a href="mail.php?accion=nuevo">Nuevo</a><br /><br />
		<img src="./images/mail_send.png"/> <a href="mail.php?accion=enviados">Enviados</a><br /><br />
			 
</div>



<div id="contenido">

<div id="textocontenido">
<?php



		
				
		//dependiendo de la accion
		switch ($_GET['accion'])
		{

			//enviar mail nuevo.
			case 'nuevo' : include("./includes/mail_nuevo.php");break;
			//mirar enviados.
			case 'enviados' : include("./includes/mail_enviados.php");break;
			//borrar mail.
			case 'borrar' : include("./includes/mail_borrar.php");break;
			//leer un mail
			case 'leer' : include("./includes/mail_leer.php");break;
			
			//por defecto la bandeja de entrada.
			default : include("./includes/mail_listado.php");break;
			
		}
		
		
?>	
</div>
</div>	


<?php
	}
	else
	{
		header("location: mantenimiento.php");
	}
	include("./includes/footer.php");
	include("./includes/ultimo.php");
	
	
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