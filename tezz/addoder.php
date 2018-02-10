<?php

	require "int.php";
	$tid = $_POST['tid'];
	$cid = $_POST['cid'];
	$height = $_POST['height'];
	$chest = $_POST['chest'];
	$details = $_POST['details'];
	$side = $_POST['side'];
	$front = $_POST['front'];
	
	$sql = "INSERT INTO oders VALUES(null,'".$tid."','".$cid."','".$height."','".$chest."','".$details."','".$side."','".$front."');";

if (mysqli_query($con,$sql)){
		echo 't';
	}else{
		echo "f";
	}

mysqli_close($con);
?>	