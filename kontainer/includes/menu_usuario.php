<?php
function fecha(){ 
	$mes = date("n"); 
	$mesArray = array( 
		1 => "Enero", 
		2 => "Febrero", 
		3 => "Marzo", 
		4 => "Abril", 
		5 => "Mayo", 
		6 => "Junio", 
		7 => "Julio", 
		8 => "Agosto", 
		9 => "Septiembre", 
		10 => "Octubre", 
		11 => "Noviembre", 
		12 => "Diciembre" 
		); 
	$semana = date("D"); 
	$semanaArray = array( 
		"Mon" => "Lunes", 
		"Tue" => "Martes", 
		"Wed" => "Miercoles", 
		"Thu" => "Jueves", 
		"Fri" => "Viernes", 
		"Sat" => "Sábado", 
		"Sun" => "Domingo", 
		); 
	$mesReturn = $mesArray[$mes]; 
	$semanaReturn = $semanaArray[$semana]; 
	$dia = date("d"); $año = date ("Y"); 
	return $semanaReturn." ".$dia." de ".$mesReturn." de ".$año; }
////////////////// menu_usuario.php //////////////////
/*
- Crear nuevo proyecto (home.php?accion=crear)
- ir a la bandeja de entrada de sus mails (mail.php)
- mostrar el nombre del usuario
- salir (salir.php)
- mostrar fecha (si nos sobra espacio)

*/

?>
<div id="footer">
	<table>
	<tr>
		<td><a href="home.php">HOME</a></td>
		<td> <img src="images/img03.jpg"/> </td>
		<td><a href="home.php?accion=crear">Nuevo Proyecto</a></td>
		<td> <img src="images/img03.jpg"/> </td>
		<td><img align="texttop" src="./images/mail_message.png" /> <a href="mail.php">Mails</a></td>
		<td> <img src="images/img03.jpg"/> </td>
		<td width="380px" align="right"><a href="salir.php">Salir</a> ( <?php echo $_SESSION['user'] ?> )</td>
		<td> <img src="images/img03.jpg"/> </td>
		<td ><?=fecha() ?></td>
	</tr>
	</table>
</div>
	