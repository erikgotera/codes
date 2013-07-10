<?php
include("clases/class.phpmailer.php");
include("clases/class.smtp.php");

$mail = new PHPMailer();
$mail->IsSMTP();
$mail->SMTPAuth = true;
$mail->SMTPSecure = "ssl";
$mail->Host = "smtp.gmail.com";
$mail->Port = 465;
$mail->Username = "kontainer.balmes@gmail.com";
$mail->Password = "Barcelona92";

$mail->From = "kontainer@jaumebalmes.net";
$mail->FromName = "Administrador";
$mail->Subject = "Bienvenido a Kontainer!";
$mail->AltBody = "Hola, bienvenido\nxxxx.";
$mail->MsgHTML("Hola, te doy mi nuevo numero<br><b>xxxx</b>.");
//$mail->AddAttachment("files/files.zip");
//$mail->AddAttachment("files/img03.jpg");
$mail->AddAddress("gabrielmartires@gmail.com", "gabriel");
$mail->IsHTML(true);

if(!$mail->Send()) {
  echo "Error: " . $mail->ErrorInfo;
} else {
  echo "Mensaje enviado correctamente";
}
?>
