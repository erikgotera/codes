<?php
////////////////// pro_subirArchivos.php //////////////////
/*
- comprobar datos con la funcion segura
- comprobar que la variable de sesion indique que pertenece al proyecto
- mostrar formularios para subir archivos
- colocar barra de progreso
- controlar el maximo de archivos permitidos
- controlar el tamaño maximo permitido
- luego de subido boton para volver a la carpeta

*/

if(isset($_POST['enviar']))
{
	if($_FILES['archivo']['size']<1048576*MAX_ARCH)
	{
		$archivo = $_FILES["file"]['name'];
		$ruta= $_SESSION['dir'].$archivo;
		move_uploaded_file( $_FILES [ 'file' ][ 'tmp_name' ], $ruta); 
	}
	header("location: proyecto.php");
}
else
{

?>
<img src="images/icono_carpeta_remota.png" align="middle" border="0" > : <b><?php 
	/*
	//version vieja
	
	$d=urldecode($_GET['d']);
	if(LINUX)
	{
		$d=str_replace(":","//",$d);
	}
	else
	{
		$d=str_replace(":","\\",$d);
	}
	
	*/
	
	//version nueva utilizando las variables de session
	
	$d=$_SESSION['directorioAuxiliar'];
	/*
	if(LINUX)
	{
		$d=str_replace(":","//",$d);
	}
	else
	{
		$d=str_replace(":","\\",$d);
	}
	*/
echo 'Directorio actual: '.$d; ?></b><br/><br/><br/><br /><br />
<p align="center">
<table border="0" align="center">
<tr>
<form method='post' action="proyecto.php?accion=subir" enctype='multipart/form-data' method="post">
<td><b>Seleccione el archivo:</b> </td><td><input name='file' type='file' /><input type="hidden" name="d" value="<?php echo $_GET['d']; ?>"</td></tr>

<tr><td colspan="2" align="center"><input type="submit" name="enviar" value="Subir archivo a carpeta actual" /></td></tr>
</form>
</table>
</p>
<br /><br />
<br /><br />
<b>Nota:</b> No se permitiran archivos de más de <?php echo MAX_ARCH; ?>Mb!<br/>
<br /><br />
<br /><br />
<?php
}
?>