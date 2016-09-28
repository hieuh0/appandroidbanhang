package com.example.hieuho.doankhoapham.Helper;

/**
 * Created by HieuHo on 7/25/2016.
 */
public class TaiKhoan {
    public int ID;
    public String email;
    public String matkhau;

    public TaiKhoan(String email, String matkhau) {
        this.email = email;
        this.matkhau = matkhau;
    }

    public TaiKhoan(Integer ID,String email, String matkhau) {
        this.ID=ID;
        this.email = email;
        this.matkhau = matkhau;
    }
}
