/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project by Sunrin Internet High School EDCAN / IWOP / ZEROPEN
 * All rights reversed.
 */

package kr.edcan.rerant.utils;

import kr.edcan.rerant.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by JunseokOh on 2016. 8. 11..
 */
public interface NetworkInterface {

    @GET("/auth/facebook/token")
    Call<User> facebookLogin(@Query("access_token") String token);

    @POST("/auth/local/login")
    @FormUrlEncoded
    Call<User> nativeLogin(@Field("email") String email, @Field("password") String password);

    @POST("/auth/local/authenticate")
    @FormUrlEncoded
    Call<User> authenticateUser(@Field("token") String token);

    @POST("/auth/local/register")
    @FormUrlEncoded
    Call<User> registerUser(@Field("name") String name, @Field("phone") String phoneNum, @Field("password") String password, @Field("email") String email);


}
