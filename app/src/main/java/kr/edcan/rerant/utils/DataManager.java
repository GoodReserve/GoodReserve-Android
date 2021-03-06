/*
 * Created by Junseok Oh on 2016.
 * Copyright by Good-Reserve Project @kotohana5706
 * All rights reversed.
 */

package kr.edcan.rerant.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import kr.edcan.rerant.model.Bucket;
import kr.edcan.rerant.model.FacebookUser;
import kr.edcan.rerant.model.User;

public class DataManager {
    /* Login Type
    * 0 Facebook
    * 1: Native
    * */

    /* Data Keys */
    private static String BUCKET_SCHEMA = "bucket";
    private static String HAS_ACTIVE_BUCKET = "hasbucket";
    private static String USER_SCHEMA = "user_schema";
    private static String HAS_ACTIVE_USER = "hasactive";
    private static String LOGIN_TYPE = "login_type";
    private static String FACEBOOK_TOKEN = "facebook_token";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public DataManager(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences("Rerant", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void save(String key, String data) {
        editor.putString(key, data);
        editor.apply();
    }

    public void saveFacebookCredential(String facebookToken) {
        editor.putString(FACEBOOK_TOKEN, facebookToken);
        editor.apply();
    }

    public void saveUserInfo(User user, int loginType) {
        editor.putInt(LOGIN_TYPE, loginType);
        editor.putString(USER_SCHEMA, new Gson().toJson(user));
        editor.putBoolean(HAS_ACTIVE_USER, true);
        editor.apply();
    }

    public Pair<Boolean, User> getActiveUser() {
        if (preferences.getBoolean(HAS_ACTIVE_USER, false)) {
            int userType = preferences.getInt(LOGIN_TYPE, -1);
            User user = new Gson().fromJson(preferences.getString(USER_SCHEMA, ""), User.class);
            user.setUserType(userType);
            return Pair.create(true, user);
        } else return Pair.create(false, null);
    }

    public String getFacebookUserCredential() {
        if (preferences.getBoolean(HAS_ACTIVE_USER, false) && preferences.getInt(LOGIN_TYPE, -1) == 0) {
            return preferences.getString(FACEBOOK_TOKEN, "");
        } else return "";
    }

    public void saveCurrentBucket(Bucket bucket, String restaurantId) {
        bucket.setRestaurantId(restaurantId);
        editor.putString(BUCKET_SCHEMA, new Gson().toJson(bucket));
        editor.putBoolean(HAS_ACTIVE_BUCKET, true);
        editor.apply();
        Log.e("asdf", new Gson().fromJson(preferences.getString(BUCKET_SCHEMA, ""), Bucket.class).getRestaurantId());
    }

    public Pair<Boolean, Bucket> getCurrentBucket() {
        if (preferences.getBoolean(HAS_ACTIVE_BUCKET, false)) {
            Bucket bucket = new Gson().fromJson(preferences.getString(BUCKET_SCHEMA, ""), Bucket.class);
            return Pair.create(true, bucket);
        } else return Pair.create(false, null);
    }

    public boolean canMakeReservation(String restaurantId) {
        if (preferences.getBoolean(HAS_ACTIVE_BUCKET, false)) {
            return (new Gson().fromJson(preferences.getString(BUCKET_SCHEMA, ""), Bucket.class).getRestaurantId()).equals(restaurantId);
        } else return true;
    }

    public void destroyAllBucket() {
        editor.remove(BUCKET_SCHEMA);
        editor.remove(HAS_ACTIVE_BUCKET);
        editor.apply();
    }

    public boolean hasActiveBucket() {
        return preferences.getBoolean(HAS_ACTIVE_BUCKET, false);
    }

    public void removeAllData() {
        editor.clear();
        editor.apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean isFirst() {
        return preferences.getBoolean("IS_FIRST", true);
    }

    public void notFirst() {
        editor.putBoolean("IS_FIRST", false);
        editor.apply();
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

}