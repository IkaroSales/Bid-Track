package com.rgames.guilherme.bidtruck.model.dao.http;

import android.content.Context;
import com.rgames.guilherme.bidtruck.model.basic.Motorista;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.model.dao.config.HttpMethods;
import com.rgames.guilherme.bidtruck.model.dao.config.URLDictionary;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class HttpRomaneio extends HttpBase<Romaneio> {

    private Context mContext;

    public HttpRomaneio(Context context) {
        mContext = context;
    }

    public List<Romaneio> select(Motorista motorista) throws Exception {
        List<Romaneio> list = new ArrayList<>();
        if (HttpConnection.isConnected(mContext)) {
            try {
                if (motorista.getCodigo() > 0) {
                    HttpURLConnection connection = HttpConnection.newInstance(URLDictionary.URL_ROMANEIO_DRIVER, HttpMethods.GET, false, true, String.valueOf(motorista.getCodigo()));
                    list = super.select(connection, Romaneio.class);
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Romaneio selectById(int id) {
        Romaneio romaneioReturn = null;
        if (HttpConnection.isConnected(mContext)) {
            try {
                HttpURLConnection connection = HttpConnection.newInstance(URLDictionary.URL_ROMANEIO, HttpMethods.GET, true, true, "/id");
                connection.getOutputStream().write(String.valueOf(id).getBytes());
                romaneioReturn = super.selectBy(connection, Romaneio.class);
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return romaneioReturn;
    }
}
