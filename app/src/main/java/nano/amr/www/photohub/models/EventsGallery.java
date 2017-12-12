package nano.amr.www.photohub.models;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EventsGallery implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<DataEvents> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    public final static Parcelable.Creator<EventsGallery> CREATOR = new Creator<EventsGallery>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EventsGallery createFromParcel(Parcel in) {
            return new EventsGallery(in);
        }

        public EventsGallery[] newArray(int size) {
            return (new EventsGallery[size]);
        }

    }
            ;
    private final static long serialVersionUID = 4841082391911234267L;

    protected EventsGallery(Parcel in) {
        in.readList(this.data, (nano.amr.www.photohub.models.DataEvents.class.getClassLoader()));
        this.meta = ((Meta) in.readValue((Meta.class.getClassLoader())));
    }

    public EventsGallery() {
    }

    public List<DataEvents> getData() {
        return data;
    }

    public void setData(List<DataEvents> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("data", data).append("meta", meta).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(meta);
    }

    public int describeContents() {
        return 0;
    }

}