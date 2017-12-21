package nano.amr.www.photohub;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.Visibility;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.util.Random;

import nano.amr.www.photohub.ContentProvider.PhotoContentProvider;
import nano.amr.www.photohub.ContentProvider.PhotoTable;
import nano.amr.www.photohub.models.Photo;

/**
 * Created by amr on 12/20/17.
 */

public class SampleAppWidgetProvider extends AppWidgetProvider {
    private static final String ACTION_CLICK = "ACTION_CLICK";
    private AppWidgetTarget appWidgetTarget;
    private Cursor photos;
    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, SampleAppWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                SampleAppWidgetProvider.class);

        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.layout_widget_simple);
            Log.w("WidgetExample", String.valueOf(number));

            appWidgetTarget = new AppWidgetTarget(context,R.id.widgetImageView, remoteViews, appWidgetIds) {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    super.onResourceReady(resource, transition);
                }
            };

            String imagePath = getImagePath(context);
            if (!imagePath.isEmpty()){
                Uri file = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName()+".my.package.name.provider",new File(imagePath));//"/sdcard/PhotoHub/xj2itdqjfrcv9l9w51u1.jpg"));
                Log.w("WidgetExample", String.valueOf(file));
                GlideApp.with(context.getApplicationContext())
                        .asBitmap()
                        .override(300,300)
                        .load(file)
                        .into(appWidgetTarget);

                pushWidgetUpdate(context, remoteViews);

            }else {
//                remoteViews.setViewVisibility(R.id.widgetImageView, View.INVISIBLE);
                remoteViews.setImageViewResource(R.id.widgetImageView,R.drawable.ic_image_white_24dp);
            }


            // Register an onClickListener
            Intent intent = new Intent(context, SampleAppWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetImageView, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    public String getImagePath(Context context) {

        String[] projection = {PhotoTable.COLUMN_ID,
                PhotoTable.COLUMN_PATH };

        photos = context.getApplicationContext().getContentResolver().query(
                PhotoContentProvider.CONTENT_URI, projection, null, null, "RANDOM()");

        if (photos.moveToFirst()){
            return photos.getString(1);
        }
        return "";
    }
}

