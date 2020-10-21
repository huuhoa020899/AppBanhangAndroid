package com.example.cua_hang_thiet_bi_online.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.cua_hang_thiet_bi_online.R;
import com.example.cua_hang_thiet_bi_online.model.Giohang;
import com.example.cua_hang_thiet_bi_online.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanpham extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndamua;
    int id=0;
    String TenChiTiet="";
    int GiaChitiet = 0;
    String HinhanhchiTiet="";
    String MoTaChiTiet="";
    int Idsanpham=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sanpham);
        Anhxa();
        AcitonToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohan:
                Intent intent = new Intent(getApplicationContext(), com.example.cua_hang_thiet_bi_online.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventButton() {
        btndamua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0)
                {
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists=false;
                    for(int i=0;i<MainActivity.manggiohang.size();i++)
                    {
                        if(MainActivity.manggiohang.get(i).getIdsp()==id)
                        {
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+sl);
                            if(MainActivity.manggiohang.get(i).getSoluongsp()>=10)
                            {
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(MainActivity.manggiohang.get(i).getSoluongsp()*GiaChitiet);
                            exists=true;
                        }

                    }
                    if(exists==false)
                    {
                        int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi=soluong*GiaChitiet;
                        MainActivity.manggiohang.add(new Giohang(id,TenChiTiet,Giamoi,HinhanhchiTiet,soluong));
                    }
                }
                else{
                    int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi=soluong*GiaChitiet;
                    MainActivity.manggiohang.add(new Giohang(id,TenChiTiet,Giamoi,HinhanhchiTiet,soluong));
                }
                Intent intent=new Intent(getApplicationContext(), com.example.cua_hang_thiet_bi_online.activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong= new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayadapter= new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayadapter);
    }

    private void GetInformation() {
        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("Thong tin san pham");
        id=sanpham.getID();
        TenChiTiet=sanpham.getTensanpham();
        GiaChitiet=sanpham.getGiasanpham();
        HinhanhchiTiet=sanpham.getHinhanhsanpham();
        MoTaChiTiet=sanpham.getMotasanpham();
        Idsanpham=sanpham.getIDsanpham();
        txtten.setText(TenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Gia"+decimalFormat.format(GiaChitiet)+"D");
        txtmota.setText(MoTaChiTiet);
        Picasso.get().load(HinhanhchiTiet)
                .placeholder(R.drawable.noiimage)
                .error(R.drawable.error)
                .into(imgchitiet);
    }

    private void AcitonToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsanpham);
        imgchitiet = findViewById(R.id.imageviewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiaschitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndamua = findViewById(R.id.buttondamua);
    }
}