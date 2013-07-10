<?php
////////////////// pro_bajarArchivos.php //////////////////
/*
- comprobar datos con la funcion segura
- comprobar que la variable de sesion indique que pertenece al proyecto
- comprobar que se le pase la id del proyecto a borrar
- forzar a descargar el archivo

*/

if(isset($_GET['f']))
{
 	$f = $_GET["f"];
	$f=urldecode($f);


	//echo'LA RUTA QUE APARECE ES ESTA: '.$_SESSION['d'];

	$f2=$_SESSION['dir'].$f;
	
header ("Content-Disposition: attachment; filename=".$f."");
header ("Content-Type:pplication/octet-stream");
header ("Content-Length: ".filesize($f2));
readfile($f2); 

	
}
else
{
	hader("location:error.php");
}

?>