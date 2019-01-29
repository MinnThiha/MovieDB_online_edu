package com.moiveapp.com.new_movie.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moiveapp.com.new_movie.R;
import com.moiveapp.com.new_movie.Utils.AppConstants;
import com.moiveapp.com.new_movie.adapter.Player_adapter;
import com.moiveapp.com.new_movie.adapter.ScreenShoot_Adapter;
import com.moiveapp.com.new_movie.model.Backdrops;
import com.moiveapp.com.new_movie.model.DetailResponse;
import com.moiveapp.com.new_movie.model.DetailSs;
import com.moiveapp.com.new_movie.model.TrailerResponse;
import com.moiveapp.com.new_movie.model.TrailerResults;
import com.moiveapp.com.new_movie.network.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Detail_activity extends AppCompatActivity {
    ImageView ivposter;
    ImageView ivback;
    TextView tv_name,tv_rating,tv_detail;
    ApiService api;
    ProgressDialog pd;
    RecyclerView rv,rv_trailer;
    ScreenShoot_Adapter sad;
    String str;
    Player_adapter pad;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ivback=findViewById(R.id.iv_detail);
        ivposter=findViewById(R.id.iv_poster_da);
        tv_name=findViewById(R.id.tv_name_da);
        tv_rating=findViewById(R.id.tv_rating_da);
        tv_detail=findViewById(R.id.tv_detail);

        sad=new ScreenShoot_Adapter(this);
        pad=new Player_adapter(this,getLifecycle());

        rv=findViewById(R.id.rv_ss_da);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv.setAdapter(sad);

        rv_trailer=findViewById(R.id.rs_trailer);
        rv_trailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rv_trailer.setAdapter(pad);

        pd=new ProgressDialog(this);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(ApiService.class);

        if (getIntent()!=null) {
            Intent i = getIntent();
            Bundle bd = i.getExtras();
            str=bd.getString("123");

            api.detail(str).enqueue(new Callback<DetailResponse>() {
                @Override
                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                    if (response.isSuccessful()){
                        if (pd.isShowing()){
                            pd.dismiss();
                        }
                        DetailResponse dr=response.body();
                        tv_detail.setText(dr.getOverview());
                        tv_name.setText(dr.getTitle());
                        tv_rating.setText(dr.getVote_count());
                        Picasso.get().load(AppConstants.IMG_URL+dr.getBackdrop_path()).placeholder(R.drawable.ic_file_download_black_24dp).into(ivback);
                        Picasso.get().load(AppConstants.IMG_URL+dr.getPoster_path()).placeholder(R.drawable.ic_file_download_black_24dp).into(ivposter);
                        getBackdrops();


                    }
                    else {
                        if (pd.isShowing()){
                            pd.dismiss();
                        }
                        Toast.makeText(Detail_activity.this,"Connection Error",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DetailResponse> call, Throwable t) {
                    Toast.makeText(Detail_activity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }



            });
        }
    }


    void getBackdrops(){
        api.getSs(str).enqueue(new Callback<DetailSs>() {
            @Override
            public void onResponse(Call<DetailSs> call, Response<DetailSs> response) {
                DetailSs ds=response.body();
                List<Backdrops> backdrops=ds.getBackdrops();
                sad.setBackdrops(backdrops);
                getTrailer();
            }

            @Override
            public void onFailure(Call<DetailSs> call, Throwable t) {
                Toast.makeText(Detail_activity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getTrailer(){
        api.getTrailer(str).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                TrailerResponse tr=response.body();
                List<TrailerResults> results=tr.getResults();
                pad.setTrailer(results);

            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Toast.makeText(Detail_activity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
