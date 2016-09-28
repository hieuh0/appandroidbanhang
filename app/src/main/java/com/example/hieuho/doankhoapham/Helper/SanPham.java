package com.example.hieuho.doankhoapham.Helper;

/**
 * Created by HieuHo on 7/26/2016.
 */
public class SanPham {
    public int id;
    public String tieude;
    public String gia;
    public String mota;
    public String ngay;
    public Integer danhmuc;
    public String dienthoai;
    public String hinh;
    public byte[] H;
    public SanPham(Integer id,String tieude, String gia, String ngay, String hinh) {
        this.id=id;
        this.tieude = tieude;
        this.gia = gia;
        this.ngay = ngay;
        this.hinh = hinh;
    }
    public SanPham(Integer id,String tieude,String mota, String gia, String dienthoai,String ngay, String hinh) {
        this.id=id;
        this.tieude = tieude;
        this.mota=mota;
        this.gia = gia;
        this.dienthoai=dienthoai;
        this.ngay = ngay;
        this.hinh = hinh;
    }
    public SanPham(Integer id,String tieude,String mota, String gia, String dienthoai,String ngay,Integer dm, byte[] hinh) {
        this.id=id;
        this.tieude = tieude;
        this.mota=mota;
        this.danhmuc=dm;
        this.gia = gia;
        this.dienthoai=dienthoai;
        this.ngay = ngay;
        this.H = hinh;
    }
}
