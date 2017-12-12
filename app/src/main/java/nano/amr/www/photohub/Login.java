package nano.amr.www.photohub;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final APIS apiInterface;

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
                // check for email perm. and show error if not avalible
                if (loginResult.getAccessToken().getPermissions().contains("email")){
                    Log.i(getLocalClassName(),"Has Email");

                    Call<FacebookUser> call = apiInterface.getUser(loginResult.getAccessToken().getToken());

                    call.enqueue(new Callback<FacebookUser>() {
                        @Override
                        public void onResponse(Call<FacebookUser> call, Response<FacebookUser> response) {
                            int statusCode = response.code();
                            FacebookUser user = response.body();
                            Log.i(getLocalClassName(),user.getMeta().getToken());
                            Global.UserToken = "bearer:" + user.getMeta().getToken();
                            Intent intent = new Intent(Login.this, EventsList.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<FacebookUser> call, Throwable t) {

                        }
                    });



                }else {
                    Log.e(getLocalClassName(),"Does not have Email");
                    LoginManager.getInstance().logOut();
                    //TODO (1) Show Error MSG
                }

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
