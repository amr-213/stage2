package nano.amr.www.photohub.models;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class Event implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private DataEvent DataEvent;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    public final static Parcelable.Creator<Event> CREATOR = new Creator<Event>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return (new Event[size]);
        }

    }
            ;
    private final static long serialVersionUID = 5224681713155332514L;

    protected Event(Parcel in) {
        this.DataEvent = ((DataEvent) in.readValue((DataEvent.class.getClassLoader())));
        this.meta = ((Meta) in.readValue((Meta.class.getClassLoader())));
    }

    public Event() {
    }

    public DataEvent getData() {
        return DataEvent;
    }

    public void setData(DataEvent DataEvent) {
        this.DataEvent = DataEvent;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("DataEvent", DataEvent).append("meta", meta).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(DataEvent);
        dest.writeValue(meta);
    }

    public int describeContents() {
        return 0;
    }

}
