<?php

	require "int.php";
	
	$sql = "SELECT * FROM users WHERE who=1;";

	$result = mysqli_query($con,$sql);

	$response = array();

	while ($row = mysqli_fetch_array($result)){
		$id = $row[0];
		$username = $row[1];
		$name = $row[2];
		$address = $row[4];
		$email = $row[5];
		$telephone = $row[6];
		$who = (int)$row[7];

		array_push($response,array('id' => $id ,'username' => $username , 'name' => $name , 'address' => $address , 'email' => $email , 'telephone' => $telephone,'who'=> $who));
	}
		echo json_encode($response);
	

mysqli_close($con);
?>	