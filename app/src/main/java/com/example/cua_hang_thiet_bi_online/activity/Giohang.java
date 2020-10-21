package com.example.cua_hang_thiet_bi_online.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.cua_hang_thiet_bi_online.R;
import com.example.cua_hang_thiet_bi_online.adapter.GiohangAdapter;
import com.example.cua_hang_thiet_bi_online.ultil.CheckConnection;

import java.text.BreakIterator;
import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {


    ListView lvgiohang;
    TextView txtthongbao;
     static TextView txttongtien;
    Button btnthanhtoan,btntieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
        EvenButton();
    }

    private void EvenButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),ThongtinKhachHang.class);
                    startActivity(intent);
                }else
                {
                    CheckConnection.Show_Toast_Short(getApplicationContext(),"gio hang chua co san pham de thanh toan");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xac nhan xoa san pham");
                builder.setMessage("Ban co chac muon xoa san pham nay");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     if (MainActivity.manggiohang.size()<=0){
                         txtthongbao.setVisibility(View.VISIBLE);
                     }else {
                         MainActivity.manggiohang.remove(position);
                         giohangAdapter.notifyDataSetChanged();
                         EventUltil();
                         if(MainActivity.manggiohang.size()<=0){
                             txtthongbao.setVisibility(View.VISIBLE);
                             giohangAdapter.notifyDataSetChanged();
                             EventUltil();
                         }
                     }
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    giohangAdapter.notifyDataSetChanged();
                    EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien=0;
        for(int i=0;i<MainActivity.manggiohang.size();i++)
        {
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+"D");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size()<=0)
        {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else
        {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lvgiohang=findViewById(R.id.listviewgiohang);
        txtthongbao=findViewById(R.id.textviewthongbao);
        txttongtien=findViewById(R.id.textviewtongtien);
        btnthanhtoan=findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua=findViewById(R.id.buttontieptucmuahang);
        toolbargiohang=findViewById(R.id.toolbargiohang);
        giohangAdapter=new GiohangAdapter(Giohang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}