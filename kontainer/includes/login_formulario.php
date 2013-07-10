<?php
////////////////// login_formulario.php //////////////////
/*
- recordar usuario con cookies utilizando un checkbox
- opcion de registrarse si no esta registrado
- redireccionarse a el mismo: index.php

*/
if(isset($_COOKIE['user']))
{
	$user=$_COOKIE['user'];
	$pass=$_COOKIE['pass'];
}
else
{
	$user="";
	$pass="";
}
?>

<form action="index.php" method="post" >
	<table border="0px" align="center" >
	<!--<tr><a href="../index.php"><img src="./images/kontainer.jpg" /> </a></tr>-->
	<div id="footer"><br /></div>
	<tr><td><h3>LOGIN:</h3></td></tr>
	<tr><td>Nombre de usuario:</td> <td><input type="text" name="user" id="user" value="<?php echo $user ?>"></td></tr>
	<tr><td>Contraseña:</td> <td><input type="password" name="password" id="password" value="<?php echo $pass ?>"></td></tr>
	<tr><td>Recordar contraseña: <input type="checkbox" name="checkbox" value="checkbox" /></td></tr>
	<tr><td align="center" colspan="2"><input type="submit" name="entrar" value="Entrar"><br/></td></tr>
	</table>
	
	<div id="footer">
	Si no estas registrado, registrate haciendo click <a style="color:#0bf60b; font-weight:bold" href="registro.php"> aqui.</a>
	</div>
	</form>