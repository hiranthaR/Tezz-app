<?php

	require "int.php";
	$fcm_token = $_POST["fcm_token"];
	$id = (int)$_POST['id'];

	$sql = "INSERT INTO fcmtoken VALUES ('".$id."','".$fcm_token."');";
	mysqli_query($con,$sql);
	mysqli_close($con);
?>