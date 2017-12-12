package nano.amr.www.photohub.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by amr on 12/12/17.
 */

public class DataInvite implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("gallery_id")
    @Expose
    private Integer galleryId;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("status")
    @Expose
    private Boolean status;
    public final static Parcelable.Creator<DataInvite> CREATOR = new Creator<DataInvite>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DataInvite createFromParcel(Parcel in) {
            return new DataInvite(in);
        }

        public DataInvite[] newArray(int size) {
            return (new DataInvite[size]);
        }

    }
            ;
    private final static long serialVersionUID = -8140634005603025954L;

    protected DataInvite(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.galleryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ownerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public DataInvite() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("code", code).append("galleryId", galleryId).append("ownerId", ownerId).append("status", status).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(galleryId);
        dest.writeValue(ownerId);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}