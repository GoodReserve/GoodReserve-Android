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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import kr.edcan.rerant.model.FacebookUser;
import kr.edcan.rerant.model.User;

public class DataManager {
    /* Login Type
    * 0 Native
    * 1: Facebook
    * */

    /* Data Keys */
    private static String HAS_ACTIVE_USER = "hasactive";
    private static String LOGIN_TYPE = "login_type";
    private static String USER_EMAIL = "email";
    private static String USER_NAME = "username";
    private static String USER_PHONE = "phone_number";
    private static String FACEBOOK_TOKEN = "facebook_token";
    private static String USER_AUTHTOKEN = "auth_token";
    private static String USER_RESERVATION = "user_reservation";
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
        editor.putBoolean(HAS_ACTIVE_USER, true);
        editor.putString(USER_NAME, user.getName());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_PHONE, user.getPhone());
        editor.putString(USER_AUTHTOKEN, user.getAuth_token());
        editor.putString(USER_RESERVATION, user.getReservation());
        editor.apply();
    }

    public Pair<Boolean, User> getActiveUser() {
        if (preferences.getBoolean(HAS_ACTIVE_USER, false)) {
            int userType = preferences.getInt(LOGIN_TYPE, -1);
            String username = preferences.getString(USER_NAME, "");
            String useremail = preferences.getString(USER_EMAIL, "");
            String userphone = preferences.getString(USER_PHONE, "");
            String usertoken = preferences.getString(USER_AUTHTOKEN, "");
            String userreservation = preferences.getString(USER_RESERVATION, "");
            User user = new User(userType, useremail, username, userphone, usertoken, userreservation);
            return Pair.create(true, user);
        } else return Pair.create(false, null);
    }

    public String getFacebookUserCredential() {
        if (preferences.getBoolean(HAS_ACTIVE_USER, false) && preferences.getInt(LOGIN_TYPE, -1) == 0) {
            return preferences.getString(FACEBOOK_TOKEN, "");
        } else return "";
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