package org.ict.omokprj_1.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("omok/list.json")
    Call<List<OmokPlayer>> getOmokList();

    @POST("/omok")
    Call<String> register(@Body OmokPlayer player);


    @DELETE("/{ono}")
    Call<String> remove(@Path("ono") int ono);


}
