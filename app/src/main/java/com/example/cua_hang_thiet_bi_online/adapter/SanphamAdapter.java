package com.example.cua_hang_thiet_bi_online.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cua_hang_thiet_bi_online.R;
import com.example.cua_hang_thiet_bi_online.activity.ChiTietSanpham;
import com.example.cua_hang_thiet_bi_online.model.Sanpham;
import com.example.cua_hang_thiet_bi_online.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder =new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder( ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Gia:"+ decimalFormat.format(sanpham.getGiasanpham())+"D");
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noiimage)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham);

    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham,txtgiasanpham;

        public ItemHolder( View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageviewsanpham);
            txttensanpham =itemView.findViewById(R.id.textviewtensanpham);
            txtgiasanpham =itemView.findViewById(R.id.textviewgiasanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanpham.class);
                    intent.putExtra("Thong tin san pham",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.Show_Toast_Short(context,arraysanpham.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
