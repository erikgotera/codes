<?php



$conexion = @mysql_connect(SERVER,MYSQL_USER,MYSQL_PASS);

if(!$conexion)
{
	echo "<div align='center'><br /><br /><strong>ERROR!</strong><br /><br />No es pot connectar amb el servidor MySQL.</div>";
	die();
}

$db = @mysql_select_db(MYSQL_DB, $conexion);

if(!$db)
{
	echo "<div align='center'><br /><br /><strong>ERROR!</strong><br /><br />No es pot connectar amb la base de dades.</div>";
	die();
}

$msg_error = "<div align='center' style='font-family: verdana;'><br /><br /><strong>ERROR!</strong><br /><br />S&prime;ha produ&iuml;t un error inesperat.<br />Pot ser degut a una modificaci&oacute; de l&prime;estructura de la base de dades o problemes de conexi&oacute;.</div>";

?>