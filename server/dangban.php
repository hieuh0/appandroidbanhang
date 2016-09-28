<?php
define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');
 
$ten= $_POST['tieude'];
$hinh= $_POST['hinh'];
$mt= $_POST['mota'];
$gia= $_POST['gia'];
$so= $_POST['sodienthoai'];
$dm= $_POST['danhmuc'];
$con = mysqli_connect(HOST,USER,PASS,DB);
 $sql = "INSERT INTO sanpham VALUES(null,'$ten','$hinh','$mt','$gia','$so',CURRENT_TIMESTAMP,'$dm')";
if(mysqli_query($con,$sql)){
echo 'success';
}else{
echo 'failure';
}
mysqli_close($con);
?>	