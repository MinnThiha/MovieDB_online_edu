package com.moiveapp.com.new_movie.network;


import com.moiveapp.com.new_movie.model.DetailResponse;
import com.moiveapp.com.new_movie.model.DetailSs;
import com.moiveapp.com.new_movie.model.MovieResponse;
import com.moiveapp.com.new_movie.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("top_rated?api_key=37faa8bf84a71d12220f23afd6a27797")
    Call<MovieResponse> getToprated(@Query("page") int page);

    @GET("{a}?api_key=37faa8bf84a71d12220f23afd6a27797")
    Call<DetailResponse> detail(@Path("a") String id);

    @GET("{b}/images?api_key=37faa8bf84a71d12220f23afd6a27797")
    Call<DetailSs> getSs(@Path("b")String id);

    @GET("{c}/videos?api_key=37faa8bf84a71d12220f23afd6a27797")
    Call<TrailerResponse> getTrailer(@Path("c") String id);

}
