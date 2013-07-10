<?php
////////////////// pro_menu_proyecto.php //////////////////
/*
- menu vertical
- mostrar nombre del proyecto y logo (imagen o logo por defecto)
- opcion de subir archivos (accion=subir)
- opciones adicionale si eres admin (accion=admin)
- opcion crear carpeta (accion=crear)
- listado de miembros del grupo (opcion para enviar mensajes)

*/



	
	
	
?>
<script type="text/javascript">
		function mostrar(capa){
		  var obj = document.getElementById(capa)
		  if(obj.style.display == "block") obj.style.display = "none"
		  else obj.style.display = "block"
		}
</script>


<div id="navigation">
<br />
<img src="logos/<?php echo $_SESSION['logoProyecto']; ?>"  /><br /><br />
<b><div id="tituloproyecto"><?php echo $_SESSION['nombreProyecto']; ?></div></b><br /><br />
<a href="proyecto.php">Archivos</a><br /><br />
<a href="proyecto.php?accion=subir">Subir archivo</a><br /><br />
<a href="#" onclick="mostrar('carpeta')">Crear carpeta</a><br /><br />

<div id="carpeta" style="display:none">
<form action="proyecto.php?accion=crearCarpeta"  method="post">
<img src="images/icono_carpeta_peke.png" align="top" border="0" /> <input type="text" name="nombreCarpeta" /><br /><br />
<input type="submit" value="Crear Carpeta" />
</form></div>


<a href="proyecto.php?accion=pegar">Pegar archivo</a> <br /><br />
<a href="proyecto.php?accion=listar">Usuarios</a><br /><br />
<?php
if($_SESSION['admin']==true)
{
	?>
	<a href="proyecto.php?accion=admin">Administrador</a><br /><br />
	
	<?php
}
?>
<a href="salir_proyecto.php">Salir del proyecto</a><br /><br />


</div>