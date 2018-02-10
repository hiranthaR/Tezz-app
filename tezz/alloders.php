<?php

	require "int.php";
	$tid = $_POST['tid'];

	$sql = "SELECT * FROM oders WHERE tid='".$tid."';";

	$result = mysqli_query($con,$sql);

	$response = array();

	while ($row = mysqli_fetch_array($result)){
		$id = (int)$row[0];
		$tid = (int)$row[1];
		$cid = (int)$row[2];
		$height = $row[3];
		$chest = $row[4];
		$details = $row[5];
		$side = $row[6];
		$front = $row[7];

		array_push($response,array('id' => $id ,'tid' => $tid , 'cid' => $cid , 'height' => $height , 'chest' => $chest , 'details' => $details , 'side' => $side , 'front' => $front));
	}
		echo json_encode($response);
	

mysqli_close($con);
?>	