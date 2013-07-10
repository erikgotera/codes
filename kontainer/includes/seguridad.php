<?php
////////////////// seguridad.php //////////////////
/*
- comprueva que seas un usuario revisando la variable de sesion,
si no eres usuario te redirecciona a error.php


*/

if(!isset($_SESSION['user']))
{
	header("location: index.php");
}



?>