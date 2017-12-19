package nano.amr.www.photohub;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cloudinary.android.MediaManager;

import nano.amr.www.photohub.API.APIS;
import nano.amr.www.photohub.API.Builder;
import nano.amr.www.photohub.models.EventsGallery;
import nano.amr.www.photohub.models.FacebookUser;
import nano.amr.www.photohub.models.PhotoCarousel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);


    }
}
