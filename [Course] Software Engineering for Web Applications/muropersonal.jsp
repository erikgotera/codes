<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
$(document).ready(function() {
    $(".borrar").click(function(event) {
        $('#content').load('borrarTweetcontroller',{id: $(this).attr('id')});
    });
    $("#tweetForm").validate({
    	submitHandler: function(form) {
    		$('#content').load('addTweet',$("#tweetForm").serialize());
    	}
    });
});
</script>

<div id="muroPersonal">

<div class="espacio2"></div>

<jsp:include page="listaTweets.jsp" />



<div id="tituloNuevoTweet"><a id="nuevoTweet" href="#" onclick="cambiar('addTweet'); return false;">Nuevo Tweet</a></div>

<div id="addTweet" style="display: none;">
					
	<form action="/Practica4/addTweet" method="POST" name="tweetForm" id="tweetForm">
	<table id="tablaTeeets2">
		<tr><td>Nuevo Tweet</td></tr>
		<tr> 
		<td>Text:</td> 
		<td> <textarea id="text" name="text" maxlength="140"/> </td> 
		</tr>
		<tr><td><input class="submit" type="submit" value="Crear"/></td></tr>    
	</table>
	</form>
</div>

<div class="espacio"></div>

</div>