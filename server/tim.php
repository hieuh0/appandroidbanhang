<?php

define('HOST','mysql.hostinger.vn');
define('USER','u638679002_hieu');
define('PASS','123456789');
define('DB','u638679002_hieu');
 
$dm = $_GET['tieude'];
$con = mysqli_connect(HOST,USER,PASS,DB);

 $response = array();
  $qr = "SELECT * FROM sanpham WHERE tieude REGEXP '$dm' ORDER BY id DESC ";
  $result = mysqli_query($con,$qr) or die(mysql_error());
if (mysqli_num_rows($result) > 0) {
    $response["sanpham"] = array();
    while ($row = mysqli_fetch_array($result)) {
        $product = array();
        $product["id"] = $row["id"];
        $product["tieude"] = $row["tieude"];
        $product["gia"] = $row["gia"];
        $product["ngay"] = $row["ngay"];
		$product["hinh"] = $row["hinh"];
        array_push($response["sanpham"], $product);
    }
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "No products found";
    echo json_encode($response);
}
?>	