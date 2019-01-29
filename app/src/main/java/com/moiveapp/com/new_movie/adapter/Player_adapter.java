package com.moiveapp.com.new_movie.adapter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moiveapp.com.new_movie.R;
import com.moiveapp.com.new_movie.model.TrailerResults;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import java.util.ArrayList;
import java.util.List;

public class Player_adapter extends RecyclerView.Adapter<Player_adapter.PHolder> {
    Context ctxt;
    Lifecycle lifecycle;
    List<TrailerResults> results=new ArrayList<>();

    public Player_adapter(Context ctxt,Lifecycle lifecycle) {
        this.ctxt = ctxt;
        this.lifecycle=lifecycle;
    }
    public void setTrailer(List<TrailerResults> results){
        this.results=results;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public PHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctxt).inflate(R.layout.player_activity,parent,false);
        return new PHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PHolder holder, int position) {
        final TrailerResults tr=results.get(position);
        lifecycle.addObserver(holder.ytp);
        holder.ytp.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String videoId = tr.getKey();
                        initializedYouTubePlayer.loadVideo(videoId, 0);
                        initializedYouTubePlayer.pause();
                    }
                });
            }
        }, true);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class PHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView ytp;
        public PHolder(View itemView) {
            super(itemView);
            ytp=itemView.findViewById(R.id.youtube_player_view);
        }
    }
}