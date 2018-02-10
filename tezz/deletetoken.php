<?php

	require "int.php";
	$id = $_POST['id'];

	
	$sql="DELETE FROM fcmtoken WHERE id='".$id."'";
	
	mysqli_query($con,$sql);
	mysqli_close($con);
?>