
package nano.amr.www.photohub.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Invitation implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private DataInvite DataInvite;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    public final static Parcelable.Creator<Invitation> CREATOR = new Creator<Invitation>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Invitation createFromParcel(Parcel in) {
            return new Invitation(in);
        }

        public Invitation[] newArray(int size) {
            return (new Invitation[size]);
        }

    }
            ;
    private final static long serialVersionUID = 4177912400576945344L;

    protected Invitation(Parcel in) {
        this.DataInvite = ((DataInvite) in.readValue((DataInvite.class.getClassLoader())));
        this.meta = ((Meta) in.readValue((Meta.class.getClassLoader())));
    }

    public Invitation() {
    }

    public DataInvite getData() {
        return DataInvite;
    }

    public void setData(DataInvite DataInvite) {
        this.DataInvite = DataInvite;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("DataInvite", DataInvite).append("meta", meta).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(DataInvite);
        dest.writeValue(meta);
    }

    public int describeContents() {
        return 0;
    }

}
