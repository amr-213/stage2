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

public class DataEvents implements Serializable, Parcelable
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
    private Object mainImageUrl;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    public final static Parcelable.Creator<DataEvents> CREATOR = new Creator<DataEvents>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DataEvents createFromParcel(Parcel in) {
            return new DataEvents(in);
        }

        public DataEvents[] newArray(int size) {
            return (new DataEvents[size]);
        }

    }
            ;
    private final static long serialVersionUID = 7336313579688261382L;

    protected DataEvents(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.photosCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mainImageUrl = ((Object) in.readValue((Object.class.getClassLoader())));
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public DataEvents() {
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

    public Object getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(Object mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("title", title).append("photosCount", photosCount).append("mainImageUrl", mainImageUrl).append("userId", userId).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(photosCount);
        dest.writeValue(mainImageUrl);
        dest.writeValue(userId);
    }

    public int describeContents() {
        return 0;
    }

}