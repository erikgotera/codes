<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<script type="text/javascript">
$(document).ready(function() {
    $(".menu").click(function(event) {
        $('#content').load('Content',{content: $(this).attr('id')});
    });
});
</script>

<div id="registrationOK">
	
		<div class="espacio2"></div>
   		<center>Success Registration. Now <a class="menu" id="login.jsp" href=#>Login</a></center>
  		<div class="espacio"></div>
</div>