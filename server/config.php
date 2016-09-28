<?php
define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$username = $_POST['username'];
$password = $_POST['password'];
 
$sql = "select * from users where username='$username' and password='$password'";
 
$res = mysqli_query($con,$sql);
 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>