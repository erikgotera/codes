<?php
////////////////// home_crearProyecto.php //////////////////
/*
- creara un nuevo registro en la bd
- el usuario sera su administrador
- se agregara el mismo a la tabla grupo


*/

if(isset($_POST['nom']))
{
	//////se crea el proyecto
	
	if(isset($_POST['imagen']))
	{	
		//controlo que el archivo no supere la cantidad maxima
		//$_FILES['archivo']['size'] nos da el tamaño en bytes 
		
		if($_FILES['file']['size']<1048576*MAX_ARCH && $_FILES['file']['size']!=0)
		{
			//si ha subido imagen.
			//ruta de la imagen del proyecto aleatoria
			$keychars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
					
			$length = 10;
			// RANDOM 
					
			$randkey = "";
			$max=strlen($keychars)-1;
					
			for ($i=0;$i<$length;$i++) 
			{
				$randkey .= substr($keychars, rand(0, $max), 1);
			}
			$randkey=$randkey.'.jpg';
			
			$ruta= ".//logos//".$randkey;
			move_uploaded_file( $_FILES [ 'file' ][ 'tmp_name' ], $ruta); 
		}
		else
		{
			$randkey="xdefecto.png";
		}
		
	}
	else
	{
		//sino subio imagen, cogera la imagen por defecto.
		$randkey="xdefecto.png";
	}
	
	
	//insertar registros a la bd proyecto nuevo y el mismo a grupo.
	
	
	//iniciamos la coneccion cn la bd
	include("./includes/conexionBD.php");
	//primer insert
	//vuelvo seguros todos los parametros del post
	$nom=make_safe($_POST['nom']);
	$query = sprintf("INSERT INTO proyectos (nombre,id_admin,logo) VALUES ('%s','%s','%s')",$nom,$_SESSION['user'],$randkey);
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
	//conseguimos el id_proyecto
	
	$id_proyecto=mysql_insert_id();
	//segundo insert
	$query = sprintf("INSERT INTO grupos (user,id_proyecto) VALUES ('%s','%s')",$_SESSION['user'],$id_proyecto);
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
	
	///////// se crea la carpeta del proyecto que tiene como nombre el id_proyecto
	
	if(!mkdir(RUTA.$id_proyecto, 0700))header("location:error.php");
	
	
	
	
	//finalizamos la sesion con la bd
	include("./includes/FinconexionBD.php");
	
	//listamos los proyectos
	
	include("./includes/home_listarProyectos.php");
}
else
{
	//si no se ha introducido aun el nombre del proyecto.
	?>
	<script type="text/javascript">
		function mostrar(capa){
		  var obj = document.getElementById(capa)
		  if(obj.style.display == "block") obj.style.display = "none"
		  else obj.style.display = "block"
		}
	</script>
	
	<form method='post' action='home.php?accion=crear' enctype='multipart/form-data'>
	
	<h1>Crear Proyecto nuevo</h1><br/>
	Introduce el nombre del proyecto: <input type="text" name="nom" /><br/>
	Incluir imagen del proyecto: <input type="checkbox" name="imagen" onclick="mostrar('imagen_subida')" /><br/>
	<div id="imagen_subida" style="display:none">
	Selecciona el logo del proyecto (opcional): <input name='file' type='file' /><br />
	(Tamaño de la imagen: 64x64)
	</div><br/>
	<input type="submit" name="enviar" value="Crear Proyecto" />
	</form>
	
	<br/><br/>
	<p align="center"><b>Condiciones de uso</b><p>
	<p align="center">El sitio web kontainer no se hace responsable del contenido subido a su servidor,
	el usuario es el unico responsable de su contenido.</p>
	<p align="center">Kontainer se reserva el derecho de eliminar cuentas que no considere oportunas o su contenido</p>
	
	<p align="center">He leido y acepto las condiciones del servicio <input type="checkbox" value="ok" /></p> 
	
	

<?php
	//mostrar condiciones y chechk box
}

?>
