package com.moiveapp.com.new_movie.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.moiveapp.com.new_movie.R;
import com.moiveapp.com.new_movie.Utils.AppConstants;
import com.moiveapp.com.new_movie.model.Backdrops;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ScreenShoot_Adapter extends RecyclerView.Adapter<ScreenShoot_Adapter.Sholder> {
    Context ctxt;
    List<Backdrops> backdrops=new ArrayList<>();


    public ScreenShoot_Adapter(Context ctxt) {
        this.ctxt = ctxt;
    }
    public void setBackdrops(List<Backdrops> backdrops){
        this.backdrops.clear();
        this.backdrops=backdrops;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Sholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(ctxt).inflate(R.layout.sv_ss,parent,false);
        return new Sholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Sholder holder, int position) {
        Backdrops bd=backdrops.get(position);
        Picasso.get().load(AppConstants.IMG_URL+bd.getFile_path()).placeholder(R.drawable.ic_file_download_black_24dp).into(holder.iv);
    }


    @Override
    public int getItemCount() {
        return backdrops.size();
    }

    public class Sholder extends RecyclerView.ViewHolder {
        ImageView iv;
        public Sholder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_ss);
        }
    }
}
