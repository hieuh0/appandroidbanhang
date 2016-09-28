<?php
define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');

$con = mysqli_connect(HOST,USER,PASS,DB);
$mail= $_POST['email'];
$pass= md5($_POST['encrypted_password']);

$qr = "UPDATE users SET encrypted_password='$pass' WHERE email='$mail' ";

if(mysqli_query($con,$qr)){
echo "success";

}else{
echo "Loi";
}

 
mysqli_close($con);

?>