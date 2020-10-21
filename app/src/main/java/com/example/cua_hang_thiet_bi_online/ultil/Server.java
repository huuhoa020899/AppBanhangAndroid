package com.example.cua_hang_thiet_bi_online.ultil;

public class Server {
    public  static String localhost="192.168.41.111";
    public  static String DuongdanLoaisp ="http://"+ localhost +"/server/getloaisp.php";
    public  static String Duongdansanphammoinhat = "http://"+ localhost + "/server/getsanphammoinhat.php";
    public  static String Duongdandienthoai="http://"+localhost+"/server/getsanpham.php?page=";
    public  static String Duongdandonhang="http://"+localhost+"/server/thongtinkhachhang.php";
    public  static String Duongdanchitietdonhang="http://"+localhost+"/server/chitietdonhang.php";
}
