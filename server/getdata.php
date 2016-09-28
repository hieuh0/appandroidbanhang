<?php
define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');

$con = mysqli_connect(HOST,USER,PASS,DB);

 
$resa = mysqli_query($con,"SELECT * FROM users");
$result = array();
while($row = mysqli_fetch_array($resa)){
array_push($result,
array('id'=>$row[0],
'name'=>$row[1],
'email'=>$row[2],
'encrypted_password'=>$row[3],
'created_at'=>$row[4]
));
}
echo json_encode(array("result"=>$result));
 
mysqli_close($db);
?>