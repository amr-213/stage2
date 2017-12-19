package nano.amr.www.photohub.models;

/**
 * Created by amr on 12/19/17.
 */

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NewPhoto implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private DataCarousel data;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    public final static Parcelable.Creator<NewPhoto> CREATOR = new Creator<NewPhoto>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewPhoto createFromParcel(Parcel in) {
            return new NewPhoto(in);
        }

        public NewPhoto[] newArray(int size) {
            return (new NewPhoto[size]);
        }

    }
            ;
    private final static long serialVersionUID = 8608058275580494324L;

    protected NewPhoto(Parcel in) {
        this.data = ((DataCarousel) in.readValue((DataCarousel.class.getClassLoader())));
        this.meta = ((Meta) in.readValue((Meta.class.getClassLoader())));
    }

    public NewPhoto() {
    }

    public DataCarousel getData() {
        return data;
    }

    public void setData(DataCarousel data) {
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
        dest.writeValue(data);
        dest.writeValue(meta);
    }

    public int describeContents() {
        return 0;
    }

}