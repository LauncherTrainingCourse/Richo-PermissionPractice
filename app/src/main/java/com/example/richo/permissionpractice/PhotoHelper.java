package com.example.richo.permissionpractice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by richo on 2016/12/23.
 */

public class PhotoHelper {
    private static final String TAG = PhotoHelper.class.getName();

    OkHttpClient client;

    public PhotoHelper() {
        client = new OkHttpClient.Builder()
                .build();
    }

    String run() throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.flickr.com")
                .addPathSegment("services")
                .addPathSegment("rest")
                .addQueryParameter("method", "flickr.photos.getRecent")
                .addQueryParameter("api_key", "42dbd5bcdb2964a243d8c50ab93fb96c")
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "?")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public ArrayList<String> loadPhotos() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String response = this.run();

            try {
                JSONObject object = new JSONObject(response);
                JSONObject photos = object.getJSONObject("photos");
                JSONArray photo = photos.getJSONArray("photo");
                for(int i=0; i<photo.length(); i++){
                    JSONObject item = photo.getJSONObject(i);
                    String url = "https://farm" +
                            item.getString("farm") +
                            ".staticflickr.com/" +
                            item.getString("server") +
                            "/" + item.getString("id") +
                            "_" + item.getString("secret") + ".jpg";

                    list.add(url);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
