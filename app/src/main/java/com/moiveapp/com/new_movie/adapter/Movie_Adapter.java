package com.moiveapp.com.new_movie.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moiveapp.com.new_movie.Delegate.Movie_delegate;
import com.moiveapp.com.new_movie.R;
import com.moiveapp.com.new_movie.Utils.AppConstants;
import com.moiveapp.com.new_movie.model.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.Mholder>{

    Context ctxt;
    List<Results> results=new ArrayList<>();
    Movie_delegate delegate;

    public Movie_Adapter(Context ctxt) {
        this.ctxt = ctxt;
    }
    public void setResults(List<Results> results){
        this.results.clear();
        this.results=results;
        notifyDataSetChanged();
    }
    public void setDelegateListener(Movie_delegate delegate){
        this.delegate=delegate;
    }

    public void addResults(List<Results> results){
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Mholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(ctxt).inflate(R.layout.sample_view,parent,false);
        return new Mholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Mholder holder, int position) {
        Results r=results.get(position);
        holder.tv_title.setText(r.getTitle());
        holder.tv_desc.setText(r.getOverview());
        Picasso.get().load(AppConstants.IMG_URL+r.getPoster_path()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Mholder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_title,tv_desc;
        public Mholder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_svm);
            tv_title=itemView.findViewById(R.id.tv_title_sv);
            tv_desc=itemView.findViewById(R.id.tv_desc_sv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.delegateListener(results.get(getAdapterPosition()));
                }
            });
        }
    }
}
