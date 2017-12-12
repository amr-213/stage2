package nano.amr.www.photohub;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import nano.amr.www.photohub.API.APIS;
import nano.amr.www.photohub.API.Builder;
import nano.amr.www.photohub.models.EventsGallery;
import nano.amr.www.photohub.models.FacebookUser;
import nano.amr.www.photohub.models.PhotoCarousel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsList extends AppCompatActivity {

    APIS apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        apiInterface = Builder.getClient().create(APIS.class);

        Call<EventsGallery> call = apiInterface.getEvents(1);

       call.enqueue(new Callback<EventsGallery>() {
           @Override
           public void onResponse(Call<EventsGallery> call, Response<EventsGallery> response) {
               int statusCode = response.code();
               EventsGallery events = response.body();

               Log.i(getLocalClassName(), String.valueOf(events.getData().size()));
           }

           @Override
           public void onFailure(Call<EventsGallery> call, Throwable t) {

           }
       });


    }
}
