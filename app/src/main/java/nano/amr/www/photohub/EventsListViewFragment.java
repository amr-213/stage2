package nano.amr.www.photohub;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import nano.amr.www.photohub.API.APIS;
import nano.amr.www.photohub.API.Builder;
import nano.amr.www.photohub.Adapter.EventsViewAdapter;
import nano.amr.www.photohub.R;
import nano.amr.www.photohub.models.Event;
import nano.amr.www.photohub.models.EventsGallery;
import nano.amr.www.photohub.models.Meta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsListViewFragment extends Fragment {

    APIS apiInterface;
    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private LinearLayout layoutFabSave;
    private LinearLayout layoutFabEdit;

    private String newEventName = "";
    private String inviteCode = "";

    private RecyclerView eventsRv;
    public EventsListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_events_list_view, container, false);

        eventsRv = (RecyclerView) root.findViewById(R.id.eventsRV);
        eventsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventsRv.setAdapter(null);

        fabSettings = (FloatingActionButton) root.findViewById(R.id.fabSetting);

        layoutFabSave = (LinearLayout) root.findViewById(R.id.layoutFabSave);
        layoutFabEdit = (LinearLayout) root.findViewById(R.id.layoutFabEdit);


        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        layoutFabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Event Name");
                builder.setMessage("Please Enter a name to create a new Event");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newEventName = input.getText().toString();
                        CreateNewEvent(newEventName);
                        Log.e("FAP",newEventName);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        layoutFabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Accept Invite");
                builder.setMessage("Please The Code You Received to access the Event");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inviteCode = input.getText().toString();
                        Log.e("FAP",inviteCode);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        apiInterface = Builder.getClient().create(APIS.class);

        GetEvents();

        closeSubMenusFab();

        return root;
    }

    private void GetEvents(){
        Call<EventsGallery> call = apiInterface.getEvents(1);

        call.enqueue(new Callback<EventsGallery>() {
            @Override
            public void onResponse(Call<EventsGallery> call, Response<EventsGallery> response) {
                int statusCode = response.code();
                EventsGallery events = response.body();

//                Log.e(getActivity().getLocalClassName(), String.valueOf(events));

                if (events != null){
                    EventsViewAdapter adapter = new EventsViewAdapter(getActivity(), events.getData());
                    eventsRv.setAdapter(adapter);
                    Log.i(getActivity().getLocalClassName(), String.valueOf(events.getData().size()));
                }
            }

            @Override
            public void onFailure(Call<EventsGallery> call, Throwable t) {

            }
        });
    }
    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabSave.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabSave.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }

    private void CreateNewEvent(String eventName){
        Call<Event> call = apiInterface.createNewEvent(eventName);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                GetEvents();
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });
    }

    private void AcceptNewInvite(String inviteCode){
        Call<Meta> call = apiInterface.acceptInvite(inviteCode);
        call.enqueue(new Callback<Meta>() {
            @Override
            public void onResponse(Call<Meta> call, Response<Meta> response) {
                GetEvents();
            }

            @Override
            public void onFailure(Call<Meta> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        GetEvents();
    }
}
