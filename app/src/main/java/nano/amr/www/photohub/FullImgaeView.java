package nano.amr.www.photohub;

import android.content.Intent;
import android.net.Uri;
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

    private String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
