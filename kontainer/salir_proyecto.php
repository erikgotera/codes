<?php
session_start();
//se eliminan las variables del proyecto
unset($_SESSION["dir"]);
unset($_SESSION["id_proyecto"]);
unset($_SESSION["logoProyecto"]);
unset($_SESSION["nombreProyecto"]);
unset($_SESSION["cortarArchivoR"]);
unset($_SESSION["cortarArchivo"]);
unset($_SESSION["directorioAuxiliar"]);
$_SESSION["admin"]=false;

//se redirecciona al home

header("location:home.php");

?>