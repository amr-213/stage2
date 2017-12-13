package nano.amr.www.photohub.Adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import nano.amr.www.photohub.R;
import nano.amr.www.photohub.models.DataEvents;
import nano.amr.www.photohub.models.Event;
import nano.amr.www.photohub.models.DataEvents;

/**
 * Created by amr on 12/12/17.
 */

public class EventsViewAdapter extends RecyclerView.Adapter<EventsViewAdapter.EventCell> {

    private List<DataEvents> eventsList;
    private Context mContext;

    public EventsViewAdapter(Context context, List<DataEvents> list) {
        eventsList = list;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public EventCell onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.eventcell, parent, false);

        // Return a new holder instance
        EventCell viewHolder = new EventCell(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventCell holder, int position) {
        DataEvents contact = eventsList.get(position);
        holder.bind(contact);
//        Log.i(getClass().getSimpleName(),String.valueOf(contact));

    }

    @Override
    public int getItemCount() {
        if(eventsList==null) return 0;
        else return eventsList.size();
    }

    public class EventCell extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView eventName;
        public TextView imagesCount;
        public ImageView eventMainImage;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public EventCell(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            eventName = itemView.findViewById(R.id.EventName);
            imagesCount = itemView.findViewById(R.id.ImagesCountTextView);
            eventMainImage = itemView.findViewById(R.id.EventMainImageView);
        }

        public void bind(DataEvents position){
            eventName.setText(position.getTitle());
            imagesCount.setText(""+position.getPhotosCount());
            //TODO Load Image Asycn
            Picasso.with(getContext())
                    .load(""+position.getMainImageUrl())
                    .into(eventMainImage);
        }
    }
}
