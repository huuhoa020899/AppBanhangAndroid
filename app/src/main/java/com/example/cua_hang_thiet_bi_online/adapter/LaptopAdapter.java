package com.example.cua_hang_thiet_bi_online.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cua_hang_thiet_bi_online.R;
import com.example.cua_hang_thiet_bi_online.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraylaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class  ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imglaptop;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
      ViewHolder viewHolder =null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlaptop = view.findViewById(R.id.textviewlaptop);
            viewHolder.txtgialaptop = view.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop= view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imglaptop= view.findViewById(R.id.imageviewlaptop);
            view.setTag(viewHolder);
        }else
        {
            viewHolder =(ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenlaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Gia:"+ decimalFormat.format(sanpham.getGiasanpham())+"D");
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noiimage)
                .error(R.drawable.error)
                .into(viewHolder.imglaptop);
        return view;
    }
}
