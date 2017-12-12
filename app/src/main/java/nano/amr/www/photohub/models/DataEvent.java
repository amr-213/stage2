package nano.amr.www.photohub.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amr on 12/12/17.
 */

public class DataEvent implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("photos_count")
    @Expose
    private Integer photosCount;
    @SerializedName("main_image_url")
    @Expose
    private String mainImageUrl;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    public final static Parcelable.Creator<DataEvent> CREATOR = new Creator<DataEvent>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DataEvent createFromParcel(Parcel in) {
            return new DataEvent(in);
        }

        public DataEvent[] newArray(int size) {
            return (new DataEvent[size]);
        }

    }
            ;
    private final static long serialVersionUID = -64180993256890544L;

    protected DataEvent(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.photosCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mainImageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.photos, (nano.amr.www.photohub.models.Photo.class.getClassLoader()));
    }

    public DataEvent() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(Integer photosCount) {
        this.photosCount = photosCount;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("title", title).append("photosCount", photosCount).append("mainImageUrl", mainImageUrl).append("userId", userId).append("photos", photos).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(photosCount);
        dest.writeValue(mainImageUrl);
        dest.writeValue(userId);
        dest.writeList(photos);
    }

    public int describeContents() {
        return 0;
    }

}