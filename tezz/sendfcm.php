<?php

	require "int.php";

	$id = $_POST["id"];

	$message ="Your oder have finished!";
	$title = "Attention";

	$path_to_fcm = 'http://fcm.googleapis.com/fcm/send';
	$server_key = 'AAAACZ1eEeM:APA91bHmXWTwM3aNdrpjIHsBgJ4cPPLHtO5_uT5cdVLk72fITQIUcTtW2RhGOLgfQxCbpEdefUvz4YcgI-Vyno7VxzwmNw7eDU2AUcYeE2SmW2gUsXD2Re-VHnyiFu_TPVmtT-Mjq3_z';

	$sql = "SELECT token FROM fcmtoken WHERE id =".$id.";";
	$result = mysqli_query($con,$sql);

	if (mysqli_num_rows($result)>0){
		
		$row = mysqli_fetch_row($result);
		$key = $row[0];
		echo $key;

	$headers = array('Authorization:key='.$server_key,'Content-Type:application/json');

	$fields = array('to'=>$key,'data'=> array('title'=>$title,'text'=>$message,'sound'=>'default'));

	$payload = json_encode($fields);

	$curl_session = curl_init();

	curl_setopt($curl_session, CURLOPT_URL , $path_to_fcm);
	curl_setopt($curl_session, CURLOPT_POST , true);
	curl_setopt($curl_session, CURLOPT_HTTPHEADER , $headers);
	curl_setopt($curl_session, CURLOPT_RETURNTRANSFER , true);
	curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER , false);
	curl_setopt($curl_session, CURLOPT_IPRESOLVE , CURL_IPRESOLVE_V4);
	curl_setopt($curl_session, CURLOPT_POSTFIELDS , $payload);

	$result = curl_exec($curl_session);

	curl_close($curl_session);

}
	mysqli_close($con);

?>