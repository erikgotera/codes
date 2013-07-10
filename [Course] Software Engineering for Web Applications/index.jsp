<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Metter</title>

<link rel="stylesheet" type="text/css" href="css/estil.css" />

<script type="text/javascript" src="jquery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="jquery/jquery.validate.js"></script>
<script>
	function cambiar(esto)
	{
	vista=document.getElementById(esto).style.display;
	if (vista=='none')
		vista='block';
	else
		vista='none';

	document.getElementById(esto).style.display = vista;
	}
</script>


</head>
<body>

<!-- Begin Wrapper -->
   <div id="wrapper">
   
         <!-- Begin Header -->
         <div id="header">
         	<div class="espacioHeader"></div>
			<img src="img/logoMeetter2.png"  />		 
		 </div>
		 <!-- End Header -->
		 
		 <!-- Begin Navigation -->
         <div id="navigation">
		
			<jsp:include page="menu_out.jsp" />
		 </div>
		 <!-- End Navigation -->
		 
         <!-- Begin Faux Columns -->
		 <div id="faux">
		 
		  <div class="espacioIndex"></div>
		 
		       <!-- Begin Content Column -->
		       <div id="content">

				<jsp:include page="muro.jsp" />
	  
		       </div>
		       <!-- End Content Column -->


         </div>	   
         <!-- End Faux Columns --> 

         <!-- Begin Footer -->
         <div id="footer">
		       
               Gracias por confiar en Meetter		

         </div>
		 <!-- End Footer -->
		 
   </div>
   <!-- End Wrapper -->

</body>
</html>