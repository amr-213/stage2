package nano.amr.www.photohub;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nano.amr.www.photohub.API.APIS;
import nano.amr.www.photohub.API.Builder;
import nano.amr.www.photohub.Adapter.EventsViewAdapter;
import nano.amr.www.photohub.R;
import nano.amr.www.photohub.models.EventsGallery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsListViewFragment extends Fragment {

    APIS apiInterface;

    public EventsListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_events_list_view, container, false);

        final RecyclerView eventsRv = (RecyclerView) root.findViewById(R.id.eventsRV);

        apiInterface = Builder.getClient().create(APIS.class);

        Call<EventsGallery> call = apiInterface.getEvents(1);

        call.enqueue(new Callback<EventsGallery>() {
            @Override
            public void onResponse(Call<EventsGallery> call, Response<EventsGallery> response) {
                int statusCode = response.code();
                EventsGallery events = response.body();

//                Log.e(getActivity().getLocalClassName(), String.valueOf(events));

                if (events != null){
                    eventsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    EventsViewAdapter adapter = new EventsViewAdapter(getActivity(), events.getData());
                    eventsRv.setAdapter(adapter);
                    Log.i(getActivity().getLocalClassName(), String.valueOf(events.getData().size()));
                }
            }

            @Override
            public void onFailure(Call<EventsGallery> call, Throwable t) {

            }
        });

        return root;
    }

}
