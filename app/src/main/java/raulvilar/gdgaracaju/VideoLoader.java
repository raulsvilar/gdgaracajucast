package raulvilar.gdgaracaju;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.android.gms.cast.MediaInfo;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by raul on 02/04/15.
 */
public class VideoLoader extends AsyncTaskLoader<List<MediaInfo>> {

    private static final String TAG = "VideoItemLoader";
    private final String Url = "https://www.googledrive.com/host/";

    public VideoLoader(Context context) {
        super(context);
    }

    @Override
    public List<MediaInfo> loadInBackground() {
        List<MediaInfo> list = new ArrayList<>();
        InputStream is = null;
        try {
            StringBuilder sb = new StringBuilder();
            java.net.URL url = new java.net.URL(Url+"0B4ODVII5HH7dVDU4Z0l2YVdnd0k");
            URLConnection urlConnection = url.openConnection();
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
            urlConnection.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            Gson gson = new Gson();
            Palestra[] palestras = (gson.fromJson(json, Palestra[].class));
            for (Palestra palestra:palestras) {
                list.add(Utils.buildMediaInfo(palestra.name, Url+palestra.id, palestra.thumbnail));
            }
            return list;
        } catch (Exception e) {
            Log.e(TAG, "Não foi possível obter dados da mídia", e);
            return null;
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    private class Palestra {
        String thumbnail;
        String id;
        String name;

        public Palestra(String thumbnail, String id, String name){
            this.thumbnail = thumbnail;
            this.id = id;
            this.name = name;
        }
    }

}