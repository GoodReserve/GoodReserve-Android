package kr.edcan.rerant.model;

/**
 * Created by JunseokOh on 2016. 9. 27..
 */
public class FacebookUser {


    public String userid, name, token;

    public FacebookUser(String userid, String name, String token) {
        this.userid = userid;
        this.name = name;
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

}
