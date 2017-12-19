package nano.amr.www.photohub;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nano.amr.www.photohub.API.APIS;
import nano.amr.www.photohub.API.Builder;
import nano.amr.www.photohub.Adapter.EventViewAdapter;
import nano.amr.www.photohub.R;
import nano.amr.www.photohub.models.Event;
import nano.amr.www.photohub.models.EventsGallery;
import nano.amr.www.photohub.models.Invitation;
import nano.amr.www.photohub.models.NewPhoto;
import nano.amr.www.photohub.models.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventViewFragment extends Fragment {
    public static final int RESULT_OK = -1;

    private static final int RC_CODE_PICKER = 2000;
    private Event thisEvent;
    private NewPhoto LastPhoto;
    APIS apiInterface;
    String gallerId = "6";
    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private LinearLayout layoutFabSave;
    private LinearLayout layoutFabEdit;
    private ArrayList<Image> images = new ArrayList<>();
    private RecyclerView eventRV;
    private ImageView eventImage;
    private TextView eventName;
    private TextView photoCount;
    public EventViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        gallerId =  getActivity().getIntent().getStringExtra("galleryId");

        final View root = inflater.inflate(R.layout.fragment_event_view, container, false);

        eventRV = (RecyclerView) root.findViewById(R.id.eventRV);
        eventRV.setLayoutManager(new GridLayoutManager(getActivity(),4));
        eventRV.setAdapter(null);
        eventImage = root.findViewById(R.id.EventImageView);
        eventName = root.findViewById(R.id.EventName);
        photoCount = root.findViewById(R.id.ImagesCountTextView);
        root.findViewById(R.id.EventImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });


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
                start();
            }

        });

        layoutFabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAccessCode();
            }
        });

        apiInterface = Builder.getClient().create(APIS.class);
        GetEventInfo();
        closeSubMenusFab();
        return root;
    }

    private void GetEventInfo() {
        Call<Event> call = apiInterface.getEvent(gallerId);

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                int statusCode = response.code();
                thisEvent = response.body();

                if (this != null){
                    eventName.setText(thisEvent.getData().getTitle());
                    photoCount.setText(""+thisEvent.getData().getPhotosCount());
                    Log.e("EventView", String.valueOf(thisEvent.getData()));
                    Picasso.with(getContext()).load(thisEvent.getData().getMainImageUrl()).into(eventImage);
                    EventViewAdapter adapter = new EventViewAdapter(getActivity(), thisEvent.getData().getPhotos());
                    eventRV.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

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

    public void start() {

//        StorePhoto("https://www.planwallpaper.com/static/images/Jennifer-in-Paradi_3204219n.jpg");

        ImagePicker imagePicker = ImagePicker.create(this)
                .returnAfterFirst(true) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select"); // image selection title

        imagePicker.single();


        imagePicker.imageFullDirectory(Environment.getExternalStorageDirectory().getPath()) // can be full path
                .start(RC_CODE_PICKER); // start image picker activity with request code
    }

    private void GetAccessCode(){
        Call<Invitation> call = apiInterface.getInvite(gallerId);
        call.enqueue(new Callback<Invitation>() {
            @Override
            public void onResponse(Call<Invitation> call, Response<Invitation> response) {
                int statusCode = response.code();
                Invitation invite = response.body();
                if (invite != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(" Invite Code");
                    builder.setMessage("Use This Code to share this event with your Friends \n Code = "+invite.getData().getCode());
                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            closeSubMenusFab();
                        }
                    });
                    builder.show();
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("InviteCode", invite.getData().getCode());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), "Invite Has Been Copied to your Clipboard", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Invitation> call, Throwable t) {

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            images = (ArrayList<Image>) ImagePicker.getImages(data);
            printImages(images);
            return;
        }
    }

    public void UploadImage(String data){

        String requestId = MediaManager.get().upload(data).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                // your code here
                Log.i("Uploader", "Start Uploading");

            }
            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                // example code starts here
                Double progress = (double) bytes/totalBytes;
                Log.i("Uploader", String.valueOf(progress));
                // post progress to app UI (e.g. progress bar, notification)
                // example code ends here
            }
            @Override
            public void onSuccess(String requestId, Map resultData) {
                // your code here
                //TODO Call BackEnd to storePhoto
                String url = (String) resultData.get("secure_url");
                StorePhoto(url);
                Log.i("Uploader", "Done Uploading");

            }
            @Override
            public void onError(String requestId, ErrorInfo error) {
                // your code here
                Log.i("Uploader", "Error Uploading");

            }
            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                // your code here
                Log.i("Uploader", "Reschdedule Uploading");

            }})
                .dispatch();

    }
    private void printImages(List<Image> images) {
        if (images == null) return;

        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0, l = images.size(); i < l; i++) {
            stringBuffer.append(images.get(i).getPath()).append("\n");
            UploadImage(images.get(i).getPath());
        }
        Log.e("HI",stringBuffer.toString());
    }

    private void StorePhoto (String imagURL){
        Call<NewPhoto> call = apiInterface.storePhoto(imagURL,gallerId);
        call.enqueue(new Callback<NewPhoto>() {
            @Override
            public void onResponse(Call<NewPhoto> call, Response<NewPhoto> response) {
                LastPhoto = response.body();
//                Log.e("Last Main Image", String.valueOf(response.code()));
//                Log.e("Last Main Image", String.valueOf(response.body()));
//                Log.e("Last Main Image", String.valueOf(response.errorBody()));
//                Log.i("Change Me",""+thisEvent.getData().getId()+thisEvent.getData().getTitle()+"put"+LastPhoto.getData().getId());
                Call<Event> call1 = apiInterface.ChangeMainImage(thisEvent.getData().getId(),thisEvent.getData().getTitle(),"put",LastPhoto.getData().getId());
                call1.enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
//                        Log.e("Chanege Main Image", String.valueOf(response.code()));
//                        Log.e("Chanege Main Image", String.valueOf(response.body()));
//                        Log.e("Chanege Main Image", String.valueOf(response.errorBody()));
                        GetEventInfo();
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<NewPhoto> call, Throwable t) {

            }
        });
    }
}
