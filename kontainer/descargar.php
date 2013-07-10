<?
////////////////// pro_bajarArchivos.php //////////////////
/*
- comprobar datos con la funcion segura
- comprobar que la variable de sesion indique que pertenece al proyecto
- comprobar que se le pase la id del proyecto a borrar
- forzar a descargar el archivo

*/
	session_start();
	$f = $_GET["f"];
	$fcod = $_GET["f"];
	$f=urldecode($f);

	$f2=$_SESSION['dir'].$f;
	
	header ("Content-Disposition: attachment; filename=".$fcod."");
	header ("Content-Type:pplication/octet-stream");
	header ("Content-Length: ".filesize($f2));
	readfile($f2); 


?>