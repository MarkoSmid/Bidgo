<?php

require 'index.php';
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;
use PHPMailer\PHPMailer\SMTP;

$zmagovalecQuery =
					"SELECT ime, email, drazenja.id, drazba.konec, uporabnik_id, drazba_id, MAX(cena) as cena FROM drazenja 
					INNER JOIN drazba ON drazba_id = drazba.id
					INNER JOIN uporabnik ON uporabnik_id = uporabnik.id
					GROUP BY drazba_id HAVING drazba.konec < NOW();";
$zmagovalecQueryRun = mysqli_query($con, $zmagovalecQuery);


if(mysqli_num_rows($zmagovalecQueryRun) != 0){
	while ($zmagovalecResult = $zmagovalecQueryRun->fetch_assoc()) {
		$notificationsQuery = "SELECT id FROM obvestilozakonecdrazbe WHERE drazenja_id = {$zmagovalecResult['id']};";
		$notificationsQueryRun = $con->query($notificationsQuery);
		$notificationsResult = $notificationsQueryRun->fetch_assoc();
		if(mysqli_num_rows($notificationsQueryRun) == 0){
			$insertNotificationQuery = "INSERT INTO obvestilozakonecdrazbe VALUES (null, 0, {$zmagovalecResult['id']});";
			$insertNotificationQueryRun = $con->query($insertNotificationQuery);
			posljiMail($zmagovalecResult['email'], $zmagovalecResult['ime'], $zmagovalecResult['id'], $con);
		}
	}
}







function posljiMail($recieverEmail, $recieverName, $idDrazba, $povezava){

	/* Exception class. */
	require 'PHPMailer/src/Exception.php';

	/* The main PHPMailer class. */
	require 'PHPMailer/src/PHPMailer.php';

	/* SMTP class, needed if you want to use SMTP. */
	require 'PHPMailer/src/SMTP.php';

	$imePosiljatej = 'Bidgo';
	$imePrejemnik = $recieverName;
	$prejemnikEposta = $recieverEmail;
	$zadeva = 'Čestitamo! Ste zmagovalec dražbe številka ' .$idDrazba;
	$vsebina = getVsebina($idDrazba, $povezava);

	$mail = new PHPMailer;                              // Passing `true` 
	$mail->CharSet = 'UTF-8';
	//Tell PHPMailer to use SMTP
	$mail->isSMTP();
	
	//Enable SMTP debugging
	// 0 = off (for production use)
	// 1 = client messages
	// 2 = client and server messages
	$mail->SMTPDebug = 0;
	//Set the hostname of the mail server
	$mail->Host = 'smtp.gmail.com';
	// use
	// $mail->Host = gethostbyname('smtp.gmail.com');
	// if your network does not support SMTP over IPv6
	//Set the SMTP port number - 587 for authenticated TLS, a.k.a. RFC4409 SMTP submission
	$mail->Port = 587;
	//Set the encryption system to use - ssl (deprecated) or tls
	$mail->SMTPSecure = 'tls';
	//Whether to use SMTP authentication
	$mail->SMTPAuth = true;
	//Username to use for SMTP authentication - use full email address for gmail
	$mail->Username = "bidgo.info@gmail.com";
	//Password to use for SMTP authentication
	$mail->Password = "bidgo123:)";
	//Set who the message is to be sent from
	$mail->setFrom('bidgo.info@gmail.com', $imePosiljatej);
	//Set an alternative reply-to address
	$mail->addReplyTo('bidgo.info@gmail.com', $imePosiljatej);
	//Set who the message is to be sent to
	$mail->addAddress($prejemnikEposta, 'Bidgo Ekipa');
	//Set the subject line
	$mail->Subject = $zadeva;
	//Read an HTML message body from an external file, convert referenced images to embedded,
	//convert HTML into a basic plain-text alternative body
	$mail->IsHtml(true);
	$mail->Body = $vsebina;
	//$mail->msgHTML(file_get_contents('contents.html'), __DIR__);
	//Replace the plain text body with one created manually
	$mail->AltBody = $vsebina;
	//Attach an image file
	//$mail->addAttachment('images/phpmailer_mini.png');
	//send the message, check for errors
	if (!$mail->send()) {
		echo "Mailer Error: " . $mail->ErrorInfo;
	} else {
		echo "Message sent!";
	}
}

function getVsebina($idDrazba, $povezava){
	$vsebinaQuery = 
	"SELECT uporabnik.id, ime, priimek, telefon, email FROM uporabnik
	INNER JOIN izdelek ON uporabnik_id = uporabnik.id
	INNER JOIN drazba ON izdelek_id = izdelek.id
	INNER JOIN drazenja ON drazba_id = drazba.id
	INNER JOIN obvestiloZaKonecDrazbe ON drazenja_id = drazenja.id
	WHERE drazba.id = $idDrazba";

	$vsebinaRun = mysqli_query($povezava, $vsebinaQuery);
	$vsebinaResult = mysqli_fetch_assoc($vsebinaRun);
	$ime = $vsebinaResult['ime'];
	$priimek = $vsebinaResult['priimek'];
	$telefon = $vsebinaResult['telefon'];
	$email = $vsebinaResult['email'];

	$vsebina = "Čestitamo! Zmagali ste dražbo $idDrazba! <br>
	<br> Pohitite in kontaktirajte prodajalca in se dogovorite za način prevzema. <br>
	Kontaktni podatki prodajalca: <br>   - ime in priimek: $ime $priimek 
		<br> - telefonska številka: $telefon <br> -   elektronska pošta: $email <br> <br>
	Veliko sreče pri nadaljnih podvigih. <br> Ekipa Bidgo ;)";

		
	

	return $vsebina;
}


?>