<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>KONTAINER v1.0 </title>
<link rel="stylesheet" type="text/css" href="./css/estilo.css" />

<script>

//Pon en la variable obligatorios el name de todos los campos que deben rellenar

obligatorio=["user","password","nombre","apellido","mail","tmptxt",];

//Pon en la veriable textoObligatorio el texto que quieres que aparezca en el alert

textoObligatorio=["Usuario","Password","Nombre","Apellido","E-mail","Codigo"];

function comprobar(este){
for(a=0;a<obligatorio.length;a++){

if(este.elements[obligatorio[a]].value==""){

alert("Por favor, rellena el campo "+textoObligatorio[a]);
este.elements[obligatorio[a]].focus();
return false;


}

}

return true;
}

</script>
</head>

<body>

<div id="top" class="top">
<img src="./images/top_left.jpg" alt="Esquina superior izquierda" class="esquina_sup_izq" />
<img src="./images/top_right.jpg" alt="Esquina superior derecha" class="esquina_sup_der" />
</div>
<div id="content" class="content">
<div id="box_control" class="boxcontrol">

<a href="index.php"><img align="left" src="./images/kontainer.jpg" /></a>

