package kr.edcan.rerant.utils;

import android.widget.EditText;

/**
 * Created by Junseok on 2016-10-04.
 */

public class StringUtils {
    public static boolean validEmail(EditText editText){
        return editText.getText().toString().trim().contains("@");
    }
    public static boolean checkPassword(EditText password, EditText repassword){
        return password.getText().toString().trim().equals(repassword.getText().toString().trim());
    }
    public static boolean fullFilled(EditText... e){
        for(EditText editText : e){
            if(editText.getText().toString().trim().equals("")) return false;
        }
        return true;
    }
}
