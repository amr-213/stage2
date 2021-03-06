package nano.amr.www.photohub;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.MobileAds;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import nano.amr.www.photohub.API.APIS;
import nano.amr.www.photohub.API.Builder;
import nano.amr.www.photohub.API.Global;
import nano.amr.www.photohub.models.FacebookUser;
import nano.amr.www.photohub.models.Meta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    APIS apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        apiInterface = Builder.getClient().create(APIS.class);


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(getLocalClassName(),"User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken());
                ContinueWith(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.i(getLocalClassName(),"Login Caneled");
                //TODO (2) Show Login Caneled
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(getLocalClassName(),error.toString());
            }
        });
        MobileAds.initialize(this, "ca-app-pub-6132663058121354~5597198462");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String Token = new Global().getUserToken(this);
        Log.e("Global",Token);
        if (!Token.isEmpty()){
            Intent intent = new Intent(Login.this, EventsList.class);
            startActivity(intent);
            return;
        }
        AccessToken accessToken = null;
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.getToken().isEmpty()){
            ContinueWith(accessToken);
        }

    }

    private void ContinueWith(AccessToken accessToken){
        if (accessToken.getPermissions().contains("email")){
            Log.i(getLocalClassName(),"Has Email");

            Call<FacebookUser> call = apiInterface.getUser(accessToken.getToken());

            call.enqueue(new Callback<FacebookUser>() {
                @Override
                public void onResponse(Call<FacebookUser> call, Response<FacebookUser> response) {
                    int statusCode = response.code();
                    FacebookUser user = response.body();
                    Log.i(getLocalClassName(),user.getMeta().getToken());
                    Global.UserToken = "bearer:" + user.getMeta().getToken();
                    new Global().setUserToken(Global.UserToken,Login.this);
                    Intent intent = new Intent(Login.this, EventsList.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<FacebookUser> call, Throwable t) {

                }
            });



        }else {
            Log.e(getLocalClassName(), "Does not have Email");
            LoginManager.getInstance().logOut();
            //TODO (1) Show Error MSG
        }
    }
}
