package nano.amr.www.photohub.API;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amr on 12/11/17.
 */


public class Builder {
    public static final String BASE_URL = "http://photohub-app-ios.herokuapp.com/api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {


        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        //getAccessToken is your own accessToken(retrieve it by saving in shared preference or any other option )
                        if(Global.UserToken == null || Global.UserToken.isEmpty()){
                            Log.e("retrofit 2","Authorization header is already present or token is empty....");
                            return chain.proceed(chain.request());
                        }
                        Request authorisedRequest = chain.request().newBuilder()
                                .addHeader("Authorization", Global.UserToken).build();
                        Log.e("retrofit 2","Authorization header is added to the url....");
                        return chain.proceed(authorisedRequest);
                    }}).build();

        Gson gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(defaultHttpClient)
                .build();



        return retrofit;
    }
}
