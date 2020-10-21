package com.example.cua_hang_thiet_bi_online.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cua_hang_thiet_bi_online.R;
import com.example.cua_hang_thiet_bi_online.adapter.DienthoaiAdapter;
import com.example.cua_hang_thiet_bi_online.adapter.LaptopAdapter;
import com.example.cua_hang_thiet_bi_online.model.Sanpham;
import com.example.cua_hang_thiet_bi_online.ultil.CheckConnection;
import com.example.cua_hang_thiet_bi_online.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarlaptop;
    ListView lvllaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> manglaptop;
    int idlaptop=0;
    int page=1;
    View footerview;
    boolean isLoading=false;
    boolean limitadata=false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            Anhxa();
            GetIdloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreDate();
        }else
        {
            CheckConnection.Show_Toast_Short(getApplicationContext(),"Checking the internet");
        }

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
    private void LoadMoreDate() {
        lvllaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent =new Intent(getApplicationContext(),ChiTietSanpham.class);
                intent.putExtra("Thong tin san pham",manglaptop.get(i));
                startActivity(intent);
            }
        });
        lvllaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&&isLoading==false&&limitadata==false)
                {
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void GetIdloaisp() {
        idlaptop= getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idlaptop+"");
    }

    private void Anhxa() {
        lvllaptop = findViewById(R.id.listviewlaptop);
        toolbarlaptop = findViewById(R.id.toolbarlaptop);
        manglaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),manglaptop);
        lvllaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater= (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.progressbar,null);
        mHandler=new mHandler();
    }
    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdandienthoai +String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenlatop="";
                int Gialaptop=0;
                String Hinhanhlaptop="";
                String Motalaptop ="";
                int Idsplaptop=0;
                if(response!=null&&response.length()!=2){
                    try {
                        lvllaptop.removeFooterView(footerview);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0 ;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tenlatop=jsonObject.getString("tensp");
                            Gialaptop= jsonObject.getInt("giasp");
                            Hinhanhlaptop=jsonObject.getString("hinhanhsp");
                            Motalaptop = jsonObject.getString("motasp");
                            Idsplaptop = jsonObject.getInt("idsanpham");
                            manglaptop.add(new Sanpham(id,Tenlatop,Gialaptop,Hinhanhlaptop,Motalaptop,Idsplaptop));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    limitadata=true;
                    lvllaptop.removeFooterView(footerview);
                    CheckConnection.Show_Toast_Short(getApplicationContext(),"Data was is emtypy");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param =new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvllaptop.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class  ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}