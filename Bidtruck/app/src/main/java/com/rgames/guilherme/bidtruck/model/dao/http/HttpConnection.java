package com.rgames.guilherme.bidtruck.model.dao.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.rgames.guilherme.bidtruck.model.basic.Motorista;
import com.rgames.guilherme.bidtruck.model.dao.config.HttpMethods;
import com.rgames.guilherme.bidtruck.model.dao.config.URLDictionary;
import com.rgames.guilherme.bidtruck.model.errors.ContextNullException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    private HttpConnection() {
    }

    public static HttpURLConnection newInstance(URLDictionary configUrl, HttpMethods metodo, boolean doOutput, boolean doInput, String paramExtras) throws IOException {
        URL url = new URL(configUrl.getValue() + paramExtras);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(15000);
        connection.setConnectTimeout(20000);
        connection.setRequestMethod(metodo.getValue());
        connection.setDoInput(doInput);
        connection.setDoOutput(doOutput);
        if (doOutput)
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.connect();
        return connection;
    }

    public static String ConnecetinTest() {
        String ret = null;
        try {
            HttpURLConnection connection = HttpConnection.newInstance(URLDictionary.URL_MAIN, HttpMethods.GET, true, false, "");
            ret = String.valueOf(connection.getResponseCode());
            ret = new StringBuilder(connection.getResponseCode()).append(" ").append(connection.getResponseMessage()).toString();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }else try {
            throw new ContextNullException();
        } catch (ContextNullException e) {
            e.printStackTrace();
            return false;
        }
    }
}
