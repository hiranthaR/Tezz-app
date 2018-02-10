<?php

	require "int.php";

	$username = $_POST['username'];
	$name = $_POST['name'];
	$password = $_POST['password'];
	$address = $_POST['address'];
	$email = $_POST['email'];
	$telephone = $_POST['telephone'];
	$who =(int) $_POST['who'];

	$sql = "SELECT * FROM users WHERE username = '" .$username. "';";

	$result = mysqli_query($con,$sql);

	if (mysqli_num_rows($result)>0){
		echo "false";
	}else{
		$sql = "INSERT INTO users VALUES(null,'".$username."','".$name."','".$password."','".$address."','".$email."','".$telephone."','".$who."');";
		$result = mysqli_query($con,$sql);
		echo "true";
	}

mysqli_close($con);
?>