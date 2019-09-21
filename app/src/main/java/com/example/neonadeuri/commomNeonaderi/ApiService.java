package com.example.neonadeuri.commomNeonaderi;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("/study_v1/neonaduriProduct.jsp")
    Observable<Response<ResponseBody>> neonaduriProduct(
            @Field("productId") String productId,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("/study_v1/neonaduriAdd.jsp")
    Observable<Response<ResponseBody>> neonaduriAdd(
            @Field("phoneNumber") String phoneNumber,
            @Field("type") String type
    );
}
