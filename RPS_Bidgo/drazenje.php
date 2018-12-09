<?php
require 'index.php';

$idDrazba = $_POST['idDrazba'];
$idUporabnik = 1; //$_POST['idUporabnik'];
$ponudba = 5300.2; //$_POST['zacetnaCena'];

/*moraš še preveriti če čas ni potekel*/
$konec = mysqli_query($con, "SELECT konec FROM drazba WHERE id = $idDrazba");
$konec = mysqli_fetch_assoc($konec);
$konec = strtotime($konec['konec']);
$response = "";
if(time() < $konec){
	$select = "SELECT zacetnaCena FROM izdelek WHERE id=$idDrazba";
	$result = mysqli_query($con, $select);
	$result = $result->fetch_assoc();
	$zacetnaCena = $result['zacetnaCena'];
	
	if($ponudba >= $zacetnaCena ){
		$select = "SELECT uporabnik_id, drazba_id, MAX(cena) as cena FROM drazenja GROUP BY drazba_id DESC HAVING drazba_id = $idDrazba";  
		$result = mysqli_query($con, $select);
		$assoc = $result->fetch_assoc();
		$najvisjaCena = $assoc['cena'];
		
		if(mysqli_num_rows($result) != 0){
			if($najvisjaCena < $ponudba){
				$insert = "INSERT INTO drazenja VALUES (null, CURRENT_TIMESTAMP(), $ponudba, $idDrazba, $idUporabnik)";
				mysqli_query($con, $insert);
				$response = "Ponudba uspešno oddana";
			}else{
				$response = "Višja ponudba že obstaja";
			}
		}
	}else{
		$response = "Ponudba mora biti večja od začetne ponudbe";
	}
}else{
	$response = "Dražba se je že končala";
}
echo $response;	

?>