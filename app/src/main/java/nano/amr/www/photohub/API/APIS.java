package nano.amr.www.photohub.API;

import java.util.ArrayList;

import nano.amr.www.photohub.models.Event;
import nano.amr.www.photohub.models.EventsGallery;
import nano.amr.www.photohub.models.FacebookUser;
import nano.amr.www.photohub.models.Invitation;
import nano.amr.www.photohub.models.Meta;
import nano.amr.www.photohub.models.NewPhoto;
import nano.amr.www.photohub.models.Photo;
import nano.amr.www.photohub.models.PhotoCarousel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import nano.amr.www.photohub.API.Global;
/**
 * Created by amr on 12/11/17.
 */

public interface APIS {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("galleries/{GalleryID}/invitationCode")
    Call<Invitation> getInvite(@Path("GalleryID") String galleryID);

    @GET("galleries/{GalleryID}")
    Call<Event> getEvent(@Path("GalleryID") String galleryID);

    @GET("photowall")
    Call<PhotoCarousel> getCarousel();

    @GET("galleries")
    Call<EventsGallery> getEvents(@Query("tab") int Type);

    @FormUrlEncoded
    @POST("auth/fb")
    Call<FacebookUser> getUser(@Field("access_token") String code);

    @FormUrlEncoded
    @POST("galleries")
    Call<Event> createNewEvent(@Field("title") String eventName);

    @FormUrlEncoded
    @POST("galleries/addUserToGalleryAccessList")
    Call<Meta> acceptInvite(@Field("access_list_token") String inviteCode);

    @FormUrlEncoded
    @POST("photos")
    Call<NewPhoto> storePhoto(@Field("url") String imageUrl, @Field("gallery_id") String galleryID);

    @FormUrlEncoded
    @POST("galleries/{GalleryID}")
    Call<Event> ChangeMainImage(@Path("GalleryID") Integer galleryID, @Field("title") String eventName, @Field("_method") String method, @Field("main_image_id") Integer ImageID);
}