package com.moiveapp.com.new_movie.Activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.king.view.superswiperefreshlayout.SuperSwipeRefreshLayout;
import com.moiveapp.com.new_movie.Delegate.Movie_delegate;
import com.moiveapp.com.new_movie.R;
import com.moiveapp.com.new_movie.Utils.AppConstants;
import com.moiveapp.com.new_movie.adapter.Movie_Adapter;
import com.moiveapp.com.new_movie.model.MovieResponse;
import com.moiveapp.com.new_movie.model.Results;
import com.moiveapp.com.new_movie.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    SuperSwipeRefreshLayout srl;
    Movie_Adapter adapter;
    ApiService api;
    int page=1;
    int total_page=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv_ma);
        srl=findViewById(R.id.srl);
        adapter=new Movie_Adapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        srl.setRefreshing(true);
        srl.setDirection(SuperSwipeRefreshLayout.Direction.BOTH);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(ApiService.class);
        getData(1);

        srl.setOnRefreshListener(new SuperSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SuperSwipeRefreshLayout.Direction direction) {
                if (direction==SuperSwipeRefreshLayout.Direction.TOP){
                    getData(1);
                }
                else if (direction==SuperSwipeRefreshLayout.Direction.BOTTOM){
                    page++;
                    if (page<=total_page){
                        getData(page);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"No more Movies",Toast
                                .LENGTH_SHORT).show();
                    }
                }
            }
        });

        adapter.setDelegateListener(new Movie_delegate() {
            @Override
            public void delegateListener(Results results) {
                Intent i=new Intent(MainActivity.this,Detail_activity.class);
                Bundle bd=new Bundle();
                bd.putString("123",results.getId());
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }
    void getData(final int page_n){
        api.getToprated(page_n).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (srl.isRefreshing()){
                    srl.setRefreshing(false);
                }
                if(response.isSuccessful()){
                    MovieResponse mr=response.body();
                    List<Results> results=mr.getResults();
                    if (page_n==1){
                        total_page=Integer.parseInt(mr.getTotal_pages());
                        adapter.setResults(results);
                    }else {
                        adapter.addResults(results);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                if (srl.isRefreshing()){
                    srl.setRefreshing(false);
                }
                Toast.makeText(MainActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
