<?php
define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');

$con = mysqli_connect(HOST,USER,PASS,DB);
$mail= $_POST['email'];

$check ="SELECT * FROM users WHERE email='$mail'";
$row = mysqli_query($con,$check);
$num_rows = mysqli_num_rows($row);
if($num_rows > 0){
echo "success";
}else{
echo "email Khong Ton Tai";
}

?>