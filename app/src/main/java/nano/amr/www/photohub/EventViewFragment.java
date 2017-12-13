package nano.amr.www.photohub;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nano.amr.www.photohub.API.APIS;
import nano.amr.www.photohub.API.Builder;
import nano.amr.www.photohub.Adapter.EventViewAdapter;
import nano.amr.www.photohub.R;
import nano.amr.www.photohub.models.Event;
import nano.amr.www.photohub.models.EventsGallery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventViewFragment extends Fragment {

    APIS apiInterface;
    String gallerId = "6";
    public EventViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        gallerId =  getActivity().getIntent().getStringExtra("galleryId");

        final View root = inflater.inflate(R.layout.fragment_event_view, container, false);

        final RecyclerView eventRV = (RecyclerView) root.findViewById(R.id.eventRV);

        apiInterface = Builder.getClient().create(APIS.class);

        Call<Event> call = apiInterface.getEvent(gallerId);
        
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                int statusCode = response.code();
                Event event = response.body();

//                Log.e(getActivity().getLocalClassName(), String.valueOf(event));
                ImageView eventImage = root.findViewById(R.id.EventImageView);
                TextView eventName = root.findViewById(R.id.EventName);
                TextView photoCount = root.findViewById(R.id.ImagesCountTextView);
                eventName.setText(event.getData().getTitle());
                photoCount.setText(""+event.getData().getPhotosCount());
                Picasso.with(getContext()).load(event.getData().getMainImageUrl()).into(eventImage);


                if (event != null){
                    eventRV.setLayoutManager(new GridLayoutManager(getActivity(),4));
                    EventViewAdapter adapter = new EventViewAdapter(getActivity(), event.getData().getPhotos());
                    eventRV.setAdapter(adapter);
                    Log.i(getActivity().getLocalClassName(), String.valueOf(event.getData().getPhotos().size()));
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });



        return root;
    }
}
