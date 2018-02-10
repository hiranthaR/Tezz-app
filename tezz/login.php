<?php

	require "int.php";
	$username = $_POST['username'];
	$password = $_POST['password'];
	
	$sql = "SELECT * FROM users WHERE username = '" .$username. "' AND password = '".$password."';";

	$result = mysqli_query($con,$sql);

	if (mysqli_num_rows($result)>0){
		$row = mysqli_fetch_row($result);
		$id = $row[0];
		$username = $row[1];
		$name = $row[2];
		$address = $row[4];
		$email = $row[5];
		$telephone = $row[6];
		$who = $row[7];

		$response = array('id' => $id ,'username' => $username , 'name' => $name , 'address' => $address , 'email' => $email , 'telephone' => $telephone,'who' => $who);

		echo json_encode($response);
	}else{
		echo "f";
	}

mysqli_close($con);
?>	