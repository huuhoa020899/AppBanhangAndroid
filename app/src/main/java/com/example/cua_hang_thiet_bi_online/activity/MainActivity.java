package com.example.cua_hang_thiet_bi_online.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cua_hang_thiet_bi_online.R;
import com.example.cua_hang_thiet_bi_online.adapter.LoaispAdapter;
import com.example.cua_hang_thiet_bi_online.adapter.SanphamAdapter;
import com.example.cua_hang_thiet_bi_online.model.Giohang;
import com.example.cua_hang_thiet_bi_online.model.Loaisp;
import com.example.cua_hang_thiet_bi_online.model.Sanpham;
import com.example.cua_hang_thiet_bi_online.ultil.CheckConnection;
import com.example.cua_hang_thiet_bi_online.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewManhinhChinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp>mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="";
    String hinhanhloaisp ="";
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    public  static ArrayList<Giohang>manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();
        }
        else
        {
            CheckConnection.Show_Toast_Short(getApplicationContext(),"You Check Wireless Connection!!!");
            finish();
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

    private void CatchOnItemListView() {
        listViewManhinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.Show_Toast_Short(getApplicationContext(),"Checking your wireless connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.Show_Toast_Short(getApplicationContext(),"Checking your wireless connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.Show_Toast_Short(getApplicationContext(),"Checking your wireless connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,LienHeActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.Show_Toast_Short(getApplicationContext(),"Checking your wireless connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,ThongTinActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.Show_Toast_Short(getApplicationContext(),"Checking your wireless connection");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue =Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
             if(response!=null){
                 int ID=0;
                 String tensanpham="";
                 Integer giasanpham=0;
                 String Hinhanhsanpham="";
                 String Motasanpham="";
                 int IDsanpham=0;
                 for(int i=0;i<response.length();i++)
                 {
                     try {
                         JSONObject jsonObject= response.getJSONObject(i);
                         ID=jsonObject.getInt("id");
                         tensanpham=jsonObject.getString("tensp");
                         giasanpham=jsonObject.getInt("giasp");
                         Hinhanhsanpham=jsonObject.getString("hinhanhsp");
                         Motasanpham=jsonObject.getString("motasp");
                         IDsanpham=jsonObject.getInt("idsanpham");
                         mangsanpham.add(new Sanpham(ID,tensanpham,giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                         sanphamAdapter.notifyDataSetChanged();
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.Show_Toast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue =Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
             if(response!=null)
             {
                   for (int i=0;i<response.length();i++)
                   {
                       try {
                           JSONObject jsonObject = response.getJSONObject(i);
                           id=jsonObject.getInt("id");
                           tenloaisp=jsonObject.getString("tenloaisp");
                           hinhanhloaisp=jsonObject.getString("hinhanhloaisp");
                           mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                           loaispAdapter.notifyDataSetChanged();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
                   mangloaisp.add(3,new Loaisp(0,"Contact","http://cdn.onlinewebfonts.com/svg/img_266091.png"));
                   mangloaisp.add(4,new Loaisp(0,"Information","https://tse3.mm.bing.net/th?id=OIP.XYm_wXAZj_FHWIoyqs7R2AHaHa&pid=Api&P=0&w=300&h=300"));
             }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.Show_Toast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void ActionViewFlipper() {
        ArrayList<String>mangquangcao= new ArrayList<>();
        mangquangcao.add("https://sa.tinhte.vn/2014/08/2572609_Hinh_2.jpg");
        mangquangcao.add("http://anhnendep.net/wp-content/uploads/2016/03/hinh-anh-girl-xinh-gai-dep-de-thuong-nhat-viet-nam-99.jpg");
        mangquangcao.add("https://media.tinmoi.vn/upload/honghanh/2019/12/23/125813-ban-gai-tin-don-cua-quang-hai-cao-tay-dap-tra-du-luan-giua-on-ao3.jpg");
        mangquangcao.add("https://tronhouse.com/assets/data/editor/source/Blog/Su-quan-trong-cua-anh-quang-cao/hinh-anh-quang-cao-quan-trong-3.jpeg");
        for(int i=0; i<mangquangcao.size();i++)
        {
            ImageView imageView= new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    @SuppressLint("RestrictedApi")
    private void ActionBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    private void AnhXa()
    {
        toolbar= findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listViewManhinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(new Loaisp(0,"Home page","http://icons.iconarchive.com/icons/fps.hu/free-christmas-flat-circle/512/home-icon.png"));
        loaispAdapter =new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewManhinhChinh.setAdapter(loaispAdapter);
        mangsanpham = new ArrayList<>();
        sanphamAdapter= new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        if(manggiohang!=null)
        {

        } else {
            manggiohang= new ArrayList<>();
        }
    }
}