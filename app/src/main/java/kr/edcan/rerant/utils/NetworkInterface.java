/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project by Sunrin Internet High School EDCAN / IWOP / ZEROPEN
 * All rights reversed.
 */

package kr.edcan.rerant.utils;

import kr.edcan.rerant.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by JunseokOh on 2016. 8. 11..
 */
public interface NetworkInterface {

    @GET("/")
    Call<User> facebookLogin(@Field("token") String token);

}
