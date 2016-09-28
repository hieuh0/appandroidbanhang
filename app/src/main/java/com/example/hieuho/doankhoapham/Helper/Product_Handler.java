package com.example.hieuho.doankhoapham.Helper;

/**
 * Created by HieuHo on 7/29/2016.
 */
public class Product_Handler {
    public int ID;
    public byte[] Hinh;
    public String Ten;
    public String Gia;
    public String MoTa;
    public int DanhMuc;
    public String SoDT;
    public String Ngay;
    public Product_Handler( byte[] hinh, String ten, String gia, String moTa, int danhMuc, String soDT) {

        this.Hinh = hinh;
        this.Ten = ten;
        this.Gia = gia;
        this.MoTa = moTa;
        this.DanhMuc = danhMuc;
        this.SoDT = soDT;
    }
}
