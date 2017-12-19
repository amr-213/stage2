package nano.amr.www.photohub.API;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by amr on 12/12/17.
 */

public class Global {
    public static String UserToken;

    public void setUserToken(String token, Activity activity){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(token,"UserToken");
        editor.commit();
    }

    public String getUserToken(Activity activity){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String Token = sharedPref.getString("UserToken","");
        Log.e("Global",Token);
        return Token;
    }
}