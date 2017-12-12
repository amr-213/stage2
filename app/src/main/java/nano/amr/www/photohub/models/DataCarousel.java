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

public class DataCarousel implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("gallery_id")
    @Expose
    private Integer galleryId;
    public final static Parcelable.Creator<DataCarousel> CREATOR = new Creator<DataCarousel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DataCarousel createFromParcel(Parcel in) {
            return new DataCarousel(in);
        }

        public DataCarousel[] newArray(int size) {
            return (new DataCarousel[size]);
        }

    }
            ;
    private final static long serialVersionUID = -5093538779100765470L;

    protected DataCarousel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.galleryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public DataCarousel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("url", url).append("galleryId", galleryId).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(url);
        dest.writeValue(galleryId);
    }

    public int describeContents() {
        return 0;
    }

}