<?php

	require "int.php";
	$id = $_POST['id'];
	
	$sql="DELETE FROM users WHERE id='".$id."'";

	if (mysqli_query($con,$sql)){
		echo 'true';
	}else{
		echo "false";
	}

	mysqli_close($con);
?>