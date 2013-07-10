<?php
ob_start();
session_start();

//agrego valores de configuracion
include("./includes/configuracion.php");

//funciones de seguridad
//aplicar seguridad al get
function make_safe($variable) 
{
   $variable = addslashes(trim($variable));
   return $variable;
} 
?>