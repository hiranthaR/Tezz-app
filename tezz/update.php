<?php

	require "int.php";
	$id = $_POST['id'];
	$username = $_POST['username'];
	$password = $_POST['password'];
	$first_name = $_POST['firstname'];
	$last_name = $_POST['lastname'];
	$email = $_POST['email'];

	$sql = "UPDATE users SET password='".$password."' , firstname = '".$first_name."',lastname = '".$last_name."',email = '".$email."' WHERE username = '" .$username. "';";

	if (mysqli_query($con,$sql)){
		echo 'true';
	}else{
		echo "false";
	}

	mysqli_close($con);
?>