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
import android.widget.TextView;

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

    public EventViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_event_view, container, false);

        final RecyclerView eventRV = (RecyclerView) root.findViewById(R.id.eventRV);

        apiInterface = Builder.getClient().create(APIS.class);

        Call<Event> call = apiInterface.getEvent(""+6);
        
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                int statusCode = response.code();
                Event event = response.body();

//                Log.e(getActivity().getLocalClassName(), String.valueOf(event));

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
