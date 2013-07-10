<?php
////////////////// pro_pegarArchivos.php //////////////////
/*
- Comprueva que existe algo guardado en la variable session
- Copia a la nueva posicion el archivo
- Borra el archivo antiguo
- variable de session a false



*/

if(isset($_SESSION['cortarArchivo']))
{
	//copio el archivo
	copy($_SESSION['cortarArchivoR'],$_SESSION['dir'].$_SESSION['cortarArchivo']);
 	//borro el viejo
	unlink($_SESSION['cortarArchivoR']); 
	//borro las variables de session
	$_SESSION['cortarArchivoR']=false;
	$_SESSION['cortarArchivo']=false;
	
	//redirecciono al listado
	header("location: proyecto.php");
	
	
}
else
{
	//header("location: error.php");
	header("location: proyecto.php");
}


?>