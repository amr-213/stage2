package nano.amr.www.photohub.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.esafirm.imagepicker.features.ImagePicker;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;
import java.util.Map;

import nano.amr.www.photohub.EventViewFragment;
import nano.amr.www.photohub.FullImgaeView;
import nano.amr.www.photohub.R;
import nano.amr.www.photohub.models.DataEvent;
import nano.amr.www.photohub.models.Event;
import nano.amr.www.photohub.models.Photo;

/**
 * Created by amr on 12/12/17.
 */

public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.ImageCell> {

    private List<Photo> imageList;
    private Context mContext;

    public EventViewAdapter(Context context, List<Photo> list) {
        imageList = list;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public ImageCell onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.imagecell, parent, false);

        // Return a new holder instance
        ImageCell viewHolder = new ImageCell(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageCell holder, int position) {
        Photo contact = imageList.get(position);
        holder.bind(contact);
//        Log.i(getClass().getSimpleName(),String.valueOf(contact));

    }

    @Override
    public int getItemCount() {
        if(imageList==null) return 0;
        else return imageList.size();
    }

    public class ImageCell extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView image;
        String imageUrl;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ImageCell(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            image = itemView.findViewById(R.id.PhotoView);
            itemView.setOnClickListener(this);
        }

        public void bind(Photo position){
            //TODO Load Correct Image Size

            imageUrl = ""+position.getUrl();

            int indexofslash = imageUrl.lastIndexOf("/");

            String url = MediaManager.get().url().transformation(new Transformation()
                    .height(300)
                    .width(300)
                    .crop("fill")
            ).generate(imageUrl.substring(indexofslash));

            Picasso.with(getContext())
                    .load(url)
                    .into(image);
        }

        @Override
        public void onClick(View v) {
            Intent fullScreenIntent = new Intent(getContext(), FullImgaeView.class);
            fullScreenIntent.setData(Uri.parse(imageUrl));
            getContext().startActivity(fullScreenIntent);
        }



    }
}
