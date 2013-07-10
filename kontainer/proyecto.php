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
		
		
		//iniciamos la coneccion cn la bd
		include("./includes/conexionBD.php");
		
		//si es la primera vez que comprueve que esta en el grupo y
		//lo guarde en la variable de session.
		include("./includes/comprobarGrupo.php");
		
		//siempre se incluye el menu del proyecto
	include("./includes/pro_menu_proyecto.php");
		
		?><div id="contenido"><div id="textocontenido"><?php
		
		//dependiendo de la accion
		switch ($_GET['accion'])
		{
			//subir archivos
			case 'subir' : include("./includes/pro_subirArchivos.php");break;
			//descargar archivos
			case 'bajar' : include("./includes/pro_bajarArchivos.php");break;
			//borrar archivos
			case 'borrar' : include("./includes/pro_borrarArchivos.php");break;
			//seccion del admin, puede listar usuarios, agregar usuarios, etc
			case 'admin' : include("./includes/pro_admin.php");break;
			//crear carpetas
			case 'crearCarpeta' : include("./includes/pro_crearCarpeta.php");include("./includes/pro_listarArchivos.php");break;
			//mover archivos a carpetas
			//case 'mover' : include("./includes/pro_moverArchivos.php");break;
			
			//para cortar archivos, primero copiamos el archivo
			case 'copiar' : include("./includes/pro_copiarArchivos.php");break;
			
			//para pegar el archivo copiado anteriormente
			case 'pegar' : include("./includes/pro_pegarArchivos.php");break;
			
			
			
			//lista los usuarios del proyecto
			case 'listar': include("./includes/pro_listarUsuarios.php");break;
			
			//por defecto se muestran los archivos.
			default : include("./includes/pro_listarArchivos.php");
		}
	?>
	
	<?php
	
	

	//cerramos la coneccion cn la bd
	include("./includes/FinconexionBD.php");
	}
	else
	{
		header("location: mantenimiento.php");
	}
	include("./includes/footer.php");
	include("./includes/ultimo.php");
	
?>