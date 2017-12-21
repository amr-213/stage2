package nano.amr.www.photohub;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class FullImgaeView extends AppCompatActivity {

    public static final String KEY_URL = "image_url";
    public static final String KEY_NAME = "file_name";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_full_imgae_view);
        ImageView fullScreenImageView = (ImageView) findViewById(R.id.fullScreenImageView);
        Intent callingActivityIntent = getIntent();
        if(callingActivityIntent != null) {
            Uri imageUri = callingActivityIntent.getData();
            if(imageUri != null && fullScreenImageView != null) {
                imageURL = imageUri.toString();
                Picasso.with(this).load(imageUri).into(fullScreenImageView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.DownloadButton:
                //your action
                Download();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void Download(){
        if (imageURL != null) {
            Intent intent = new Intent(this, DownlaodImageService.class);
            intent.putExtra(KEY_URL, imageURL);
            int lastindex = imageURL.lastIndexOf("/");
            intent.putExtra(KEY_NAME, imageURL.substring(lastindex+1));
            startService(intent);
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
