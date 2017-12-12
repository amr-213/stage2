
package nano.amr.www.photohub.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FacebookUser implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private DataUser DataUser;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    public final static Parcelable.Creator<FacebookUser> CREATOR = new Creator<FacebookUser>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FacebookUser createFromParcel(Parcel in) {
            return new FacebookUser(in);
        }

        public FacebookUser[] newArray(int size) {
            return (new FacebookUser[size]);
        }

    }
            ;
    private final static long serialVersionUID = -4488375371513228859L;

    protected FacebookUser(Parcel in) {
        this.DataUser = ((DataUser) in.readValue((DataUser.class.getClassLoader())));
        this.meta = ((Meta) in.readValue((Meta.class.getClassLoader())));
    }

    public FacebookUser() {
    }

    public DataUser getData() {
        return DataUser;
    }

    public void setData(DataUser DataUser) {
        this.DataUser = DataUser;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("DataUser", DataUser).append("meta", meta).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(DataUser);
        dest.writeValue(meta);
    }

    public int describeContents() {
        return 0;
    }

}
