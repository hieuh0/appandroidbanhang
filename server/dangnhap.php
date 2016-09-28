<?php

$mail =$_POST['email'];
$mk=md5($_POST['encrypted_password']);
$user ="SELECT * FROM users Where email='$mail' AND encrypted_password='$mk' ";
define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');
 $con = mysqli_connect(HOST,USER,PASS,DB);
 
  $res = mysqli_query($con,$user);

$check = mysqli_fetch_array($res);

if(isset($check)){
echo "success";
}else{
echo "Sai Email Ho&#7863;c M&#7853;t Kh&#7849;u";
}
 
mysqli_close($con);
?>