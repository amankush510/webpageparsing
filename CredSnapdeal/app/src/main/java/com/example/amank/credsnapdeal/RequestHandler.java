package com.example.amank.credsnapdeal;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amank on 25-11-2017.
 */

public class RequestHandler {
    private static String TAG = RequestHandler.class.getSimpleName();

    public static ArrayList<ProductModel> getData(String keyword){
        HttpURLConnection urlConnection;
        String uri = Constants.BASE_SERVER_URL + "keyword=" + keyword + Constants.END_PART;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            ArrayList<ProductModel> products = WebpageSourceCodeReader.read(urlConnection);
            return products;
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
