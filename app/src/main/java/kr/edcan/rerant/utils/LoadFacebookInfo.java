package kr.edcan.rerant.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import kr.edcan.rerant.model.FacebookUser;
import kr.edcan.rerant.model.User;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Junseok on 2016-10-13.
 */

public class LoadFacebookInfo extends AsyncTask<String, Void, Boolean> {
    private Call<User> facebookLogin;
    private Context c;
    private DataManager manager;
    private String fbToken;

    public LoadFacebookInfo(Context c, String facebookToken) {
        this.c = c;
        this.fbToken = facebookToken;
        this.manager = new DataManager(c);
    }

    @Override
    protected Boolean doInBackground(final String... strings) {
        facebookLogin = NetworkHelper.getNetworkInstance().facebookLogin(fbToken);
        return false;
    }
}