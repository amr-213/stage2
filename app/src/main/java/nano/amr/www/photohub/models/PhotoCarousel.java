
package nano.amr.www.photohub.models;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;



public class PhotoCarousel implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<DataCarousel> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    public final static Parcelable.Creator<PhotoCarousel> CREATOR = new Creator<PhotoCarousel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PhotoCarousel createFromParcel(Parcel in) {
            return new PhotoCarousel(in);
        }

        public PhotoCarousel[] newArray(int size) {
            return (new PhotoCarousel[size]);
        }

    }
            ;
    private final static long serialVersionUID = -1909757791204091376L;

    protected PhotoCarousel(Parcel in) {
        in.readList(this.data, (nano.amr.www.photohub.models.DataCarousel.class.getClassLoader()));
        this.meta = ((Meta) in.readValue((Meta.class.getClassLoader())));
    }

    public PhotoCarousel() {
    }

    public List<DataCarousel> getData() {
        return data;
    }

    public void setData(List<DataCarousel> data) {
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