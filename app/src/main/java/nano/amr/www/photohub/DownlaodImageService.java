package nano.amr.www.photohub;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by amr on 12/20/17.
 */

public class DownlaodImageService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownlaodImageService() {
        super("DownloadImageService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String img_url = intent.getStringExtra(FullImgaeView.KEY_URL);
        String fileName = intent.getStringExtra(FullImgaeView.KEY_NAME);

        String dir = "/sdcard/PhotoHub/";

        File downloadDir = new File(dir);
        if (!downloadDir.exists()){
            downloadDir.mkdir();
        }

        File downloadFile = new File(dir+fileName);
        if (downloadFile.exists())
            downloadFile.delete();
        try {

            downloadFile.createNewFile();
            URL downloadURL = new URL(img_url);
            HttpURLConnection conn = (HttpURLConnection) downloadURL
                    .openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200)
                throw new Exception("Error in connection");
            InputStream is = conn.getInputStream();
            FileOutputStream os = new FileOutputStream(downloadFile);
            byte buffer[] = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                os.write(buffer, 0, byteCount);
            }
            os.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
