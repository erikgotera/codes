<?php
////////////////// pro_copiarArchivos.php //////////////////
/*
- Copiara en la variabla session la ruta entera del archivo a copiar
- Luego te redirigira a proyecto.php


*/


if($_GET['id_arch'])
{
	//guardo en la variable de session la ruta del archivo
	$id_arch=urldecode($_GET['id_arch']);
	$id_arch=make_safe($id_arch);
	$_SESSION['cortarArchivoR']=$_SESSION['dir'].$id_arch;
	$_SESSION['cortarArchivo']=$id_arch;
	
	header("location: proyecto.php?d=".$_SESSION['directorioAuxiliar']);

}
else
{
	header("location: error.php");
}

?>

