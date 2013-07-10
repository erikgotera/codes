<?php
////////////////// login_acceso.php //////////////////
/*
- comprovar datos enviados por post
- encriptar contraseña antes de comparar 
- verificar si esta en la bds
- agregar variables de sesion: 
						$_SESSION['user']
						$_SESSION['nivel']
						? $_SESSION['id_proyecto']=false

- redireccionar a: home.php				
	
*/
//verificando datos
$user=make_safe($_POST['user']);
$pass=make_safe($_POST['password']);
//encriptamos la contraseña
$pass=md5($pass);

//seleccionamos

include("./includes/conexionBD.php");
$query = sprintf("SELECT * FROM usuarios WHERE user='%s' AND pass='%s'",$user,$pass);
if(DEBUG==true)
{
	echo $query.'<br/>';
}
$result = @mysql_query($query, $conexion);
if(!$result )
{
	echo $msg_error;
	die();
}
						
include("./includes/FinconexionBD.php");

$row = mysql_fetch_array($result);
if(!$row) $succes = false;
else 
{
//agregamos variables de session
	$_SESSION['id_proyecto']=false;
	$_SESSION['nivel'] = $row['nivel'];
	$_SESSION['user'] = $row['user'];			
	$succes = true;	
	
	//enviamos kukis
	setcookie("user",$user,99999999999);
	setcookie("pass",$pass,99999999999);
}
if(!$succes)
{ 	
	
	//si no esta enseñamos denuebo el formulario
	include("./includes/login_formulario.php");
	echo 'usuario o contraseña erroneos!!!';
	
}
else
{	
	//si esta logueado se redirecciona a home.php
	header("location: home.php");
}


?>


