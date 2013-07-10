<?php
////////////////// pro_listarArchivos.php //////////////////
/*
- comprobar datos con la funcion segura
- comprobar que la variable de sesion indique que pertenece al proyecto, la primera vez se recive por get
- listar todos los archivos y las carpetas del direcorio raiz (id_proyecto)
- agregar icono dependiendo de la extencion.	


*/




$d="\\";
/////////// recojo los valores del directorio
if(isset($_GET['d']))
{
	$d=urldecode($_GET['d']);
	if(LINUX)
	{
		$d=str_replace(":","//",$d);
	}
	else
	{
		$d=str_replace(":","\\",$d);
	}
	
	
}
else
{
	if(isset($_SESSION['directorioAuxiliar']))
	{
		$d=$_SESSION['directorioAuxiliar'];
	}
}

//////////////// Directorio de partida, tiene que ser el del proyecto, $_SESSION['id_proyecto']

$dir=RUTA.$_SESSION['id_proyecto'].$d;

///////// guardo en la session el directorio que me encuentro

$_SESSION['dir']=$dir;

//////////////// enseño la carpeta que esta
if(DEBUG)
{
	echo '<img src="images/icono_carpeta_remota.png" align="middle" border="0"> <b>Directorio actual:</b> '.$dir.'<br>'; 
}
else
{
	echo '<img src="images/icono_carpeta_remota.png" align="middle" border="0"> <b>Directorio actual:</b> '.$d.'<br>';	
}


///////////// Opcion para retroceder en las carpetas
if(LINUX)
{
	$daux=str_replace("//",":",$d);
}
else
{
	$daux=str_replace("\\",":",$d);
}
  $carpetas = split(":",$daux); 
  array_pop($carpetas); 
  array_pop($carpetas); 
  $daux2 = join(":",$carpetas); 
  echo '<a href="proyecto.php?d='.urlencode($daux2.":").'"><img src="images/icono_back.png" border="0" align="middle"></a><a href="proyecto.php?d='.urlencode($daux2.":").'">Carpeta anterior</a>';
  
  
/////////////// comienzo la tabla
 $directorio=opendir($dir); 
 echo "<br><b>Archivos:</b><br>"; 
 printf('<table border="0"><tr>');






//////////// opcion de subir archivo

/*
printf('<td colspan="3"><a href="proyecto.php?accion=descargar"><img src="images/icono_up.png" border="0" align="middle"></a><a href="proyecto.php?accion=descargar">Subir archivo</a>');
*/
//echo '</tr><tr>';
/////////////// Listado de archivos de la carpeta


//temporal
/*
if(LINUX)
{
	$daux3=str_replace("//",":",$d);
}
else
{
	$daux3=str_replace("\\",":",$d);
}
*/
	//estoy en:
	//$daux3=urlencode($daux3);
	$_SESSION['directorioAuxiliar']=$d;
	

 
  $fila=0;
  while ($archivo = readdir($directorio)) 
  {
  	if($archivo!='.' && $archivo!='..')
	{
		if(is_dir("$dir/$archivo"))
		{	
			if($fila<4)
			{
				if(LINUX)
				{
					$daux=$d.$archivo."//";
					$daux=str_replace("//",":",$daux);
				}
				else
				{
					$daux=$d.$archivo."\\";
					$daux=str_replace("\\",":",$daux);
				}
				
				echo '<td width="128" align="center"><a href="proyecto.php?d='.urlencode($daux).'"><img src="images/icono_carpeta.png" border="0"></a><a href="proyecto.php?d='.urlencode($daux).'">'.$archivo.'</a><br>';
				$fila=$fila+1;
			}
			else
			{
			
				if(LINUX)
				{
					$daux=$d.$archivo."//";
					$daux=str_replace("//",":",$daux);
				}
				else
				{
					$daux=$d.$archivo."\\";
					$daux=str_replace("\\",":",$daux);
				}
				
				echo '</tr><tr><td width="128" align="center"><a href="proyecto.php?d='.urlencode($daux).'"><img src="images/icono_carpeta.png" border="0"></a><a href="proyecto.php?d='.urlencode($daux).'">'.$archivo.'</a><br>';
				$fila=1;
			}
	  	}
	  	else
		{
			list($nombre,$exten) = split('[/.-]',$archivo);
			switch($exten)
			{
				case 'txt': $icono='icono_txt.png';;break;
				case 'jpg': $icono='icono_jpg.png';break;
				case 'bmp': $icono='icono_bmp.png';break;
				case 'rar': $icono='icono_rar.png';break;
				case 'pdf': $icono='icono_pdf.png';break;
				case 'gif': $icono='icono_gif.png';break;
				case 'png': $icono='icono_png.png';break;
				case 'exe': $icono='icono_exe.png';break;
				case 'bat': $icono='icono_bat.png';break;
				case 'cs':  $icono='icono_cs.png';break;
				case 'c':   $icono='icono_c.png';break;
				case 'mp3': $icono='icono_mp3.png';break;
				case 'avi': $icono='icono_avi.png';break;
				case 'doc': $icono='icono_doc.png';break;
				case 'odt': $icono='icono_odt.png';break;
				//faltan mas extenciones
				
				default: $icono='icono_defecto.png';break;
			}
			if($fila<4)
			{
				$archivo2=urlencode($archivo);
				$fila=$fila+1;
				
				printf('<td align="center" width="128"><a href="descargar.php?f=%s"><img src="./images/%s" border="0" width="128"></a><a href="proyecto.php?accion=borrar&id_arch=%s"',$archivo2,$icono,$archivo2);
				
				?>onclick="if(!confirm('ATENCION!!! Se borrará el archivo seleccionado!!'))return false"<?php 
				printf('><img src="images/opcion_delete.png" border="0" align="middle" alt="Borrar"></a><a href="proyecto.php?accion=copiar&id_arch=%s"><img src="images/opcion_cut.png" border="0" align="middle" alt="Cortar"></a><a href="proyecto.php?accion=bajar&f=%s">%s</a></td>',$archivo2,$archivo2,$archivo);
				
			}
			else
			{
				$fila=1;
				
				printf('</tr><tr><td align="center" width="128"><a href="descargar.php?f=%s"><img src="./images/%s" border="0" width="128"></a><a href="proyecto.php?accion=borrar&id_arch=%s"',$archivo2,$icono,$archivo2);
				
				?>onclick="if(!confirm('ATENCION!!! Se borrará el archivo seleccionado!!'))return false"<?php 
				printf('><img src="images/opcion_delete.png" border="0" align="middle" alt="Borrar"></a><a href="proyecto.php?accion=copiar&id_arch=%s"><img src="images/opcion_cut.png" border="0" align="middle" alt="Cortar"></a><a href="proyecto.php?accion=bajar&f=%s">%s</a></td>',$archivo2,$archivo2,$archivo);
				
			}
			
		} 
	}
 
  } 
  //acabo la tabla
	printf('</tr></table>');
	
	
	//opcion temporal
	
	//printf('<a href="proyecto.php?accion=pegar&d=%s">Pegar aki</a>',$daux3);


closedir($directorio); 
?>

