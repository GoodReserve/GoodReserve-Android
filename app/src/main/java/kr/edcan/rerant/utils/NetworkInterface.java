/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project by Sunrin Internet High School EDCAN / IWOP / ZEROPEN
 * All rights reversed.
 */

package kr.edcan.rerant.utils;

import java.util.ArrayList;
import java.util.Date;

import kr.edcan.rerant.model.Bucket;
import kr.edcan.rerant.model.Menu;
import kr.edcan.rerant.model.Reservation;
import kr.edcan.rerant.model.Restaurant;
import kr.edcan.rerant.model.User;
import okhttp3.ResponseBody;
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

    // Authenticate

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
    Call<User> registerUser(@Field("name") String name, @Field("phone") String phoneNum,
                            @Field("password") String password, @Field("password_chk") String passwordChk, @Field("email") String email);

    // Restaurant Data
    @POST("/rest/list")
    Call<ArrayList<Restaurant>> getRestaurantList();

    @POST("/rest/search")
    @FormUrlEncoded
    Call<Restaurant> getRestaurantInfo(@Field("id") String restaurantId);

    @POST("/rest/search/name")
    @FormUrlEncoded
    Call<ArrayList<Restaurant>> getRestaurantListByWord(@Field("query") String query);

    @POST("/rest/search/tag")
    @FormUrlEncoded
    Call<ArrayList<Restaurant>> getRestaurantListByTag(@Field("query") String query);

    // Reservation
    @POST("/resv/cancel")
    @FormUrlEncoded
    Call<ResponseBody> cancelReservation(@Field("reservation_id") String reservationId, @Field("cancel_reason") int cancelReason,
                                         @Field("cancel_comment") String cancelComment);

    @POST("/me/resv")
    @FormUrlEncoded
    Call<ArrayList<Reservation>> getMyReservation(@Field("id") String id);

    @POST("/resv/add")
    @FormUrlEncoded
    Call<Reservation> generateReservation(@Field("reservation_id") String reservationId, @Field("reservation_marker") String _id,
                                          @Field("reservation_time") Date time, @Field("people") int peopleCount, @Field("reservation_payment") int payment,
                                          @Field("reservation_menu") String bucketId, @Field("reservation_price") int totalMoney);

    // Get Menu
    @POST("/menu/rest/list")
    @FormUrlEncoded
    Call<ArrayList<Menu>> getMenuList(@Field("restaurant_id") String restaurantId);

    // Bucket
    @POST("/bucket/add")
    @FormUrlEncoded
    Call<Bucket> newBucket(@Field("menus") ArrayList<Menu> menus);

    @POST("/bucket/update")
    @FormUrlEncoded
    Call<Bucket> updateBucket(@Field("bucket_id") String bucketId, @Field("menus") ArrayList<Menu> menus);

    @POST("/bucket/info")
    @FormUrlEncoded
    Call<Bucket> getBucketInfo(@Field("bucket_id") String bucketId);

}
