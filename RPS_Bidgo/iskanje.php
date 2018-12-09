<?php
require 'index.php';
/*
* kategorije pošlji kot string ločen z vejicami
*
* search pa kot array
*/

$response = array();
if(isset($_POST['kategorija'])) $kategorija = $_POST['kategorija'];
else $kategorija = "1, 2";
if(isset($_POST['search'])) $search = $_POST['search'];
else $search = "avto 1.6 corsa";


$iskanjeQuery = 
"SELECT drazba.id, zacetek, konec, naziv, opis, lokacija, konec
FROM drazba 
JOIN izdelek ON izdelek_id = izdelek.id
JOIN kategorija ON kategorija_id = kategorija.id";

$pogoj = 0;
if(!empty($kategorija)) $pogoj += 1; //poenostavi isset da ni treba vedno znova preverjati in združevati pogoje
if(!empty($search)) $pogoj +=2;

if($pogoj > 0){
	$iskanjeQuery .= " WHERE";
	if($pogoj >= 1){
		$kategorija = explode(',', $kategorija);
		$stringKategorija = "-998";
		for($i = 0; $i<count($kategorija); $i++){
			$stringKategorija .= " ," .strval($kategorija[$i]);
			
		}
		$iskanjeQuery .= " kategorija.id IN ($stringKategorija)";
	}
	
	if($pogoj == 3) $iskanjeQuery .= " AND";
	
	if($pogoj >= 2){
		$search = preg_split("/[\s,]+/", $search);
		$iskanjeQuery .= " (naziv LIKE '%$search[0]%'";
		for($i=1; $i<count($search); $i++){
			$iskanjeQuery .= " OR naziv LIKE '%$search[$i]%'";
		}
		$iskanjeQuery .= ")";
	}
}
$iskanjeRun = mysqli_query($con, $iskanjeQuery);
while($iskanjeResult = mysqli_fetch_array($iskanjeRun)){
	$konec = strtotime($iskanjeResult[6]);
	if(time() < $konec){
		array_push($response, array( 
							'id' => $iskanjeResult[0], 
							'zacetek' => $iskanjeResult[1], 
							'konec' => $iskanjeResult[2], 
							'naziv' => $iskanjeResult[3], 
							'opis' => $iskanjeResult[4],
							'lokacija' => $iskanjeResult[5]
							));
	}
}
json_encode(array('server_response' => $response));




?>