<?php
define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$username = $_POST['name'];
$mail= $_POST['email'];
$pass= md5($_POST['encrypted_password']);
$code = $_POST['code'];
$check ="SELECT * FROM users Where email='$mail'";

$row = mysqli_query($con,$check);
$num_rows = mysqli_num_rows($row);
if($num_rows > 0){
echo "Email Da Ton Tai";
}else
{
$sql = "INSERT INTO users VALUES(null,'$username','$mail','$pass',CURRENT_TIMESTAMP,'$code')";
if(mysqli_query($con,$sql)){
echo 'success';
}else{
echo 'failure';
}

}
 
mysqli_close($con);
?>	