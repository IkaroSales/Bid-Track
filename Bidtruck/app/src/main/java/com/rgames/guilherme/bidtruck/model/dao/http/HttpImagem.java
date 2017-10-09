package com.rgames.guilherme.bidtruck.model.dao.http;

import android.util.Log;

import com.rgames.guilherme.bidtruck.model.basic.ImagemOcorrencia;
import com.rgames.guilherme.bidtruck.model.dao.config.HttpMethods;
import com.rgames.guilherme.bidtruck.model.dao.config.URLDictionary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by kevyn on 07/10/2017.
 */

public class HttpImagem extends HttpBase<ImagemOcorrencia> {

    public boolean insert(int ocorrencia, ArrayList<String> list) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("ocorrencia", ocorrencia);
            // while (i < list.size()) {
            jsonObject.accumulate("foto", list);

            HttpURLConnection connection = HttpConnection.newInstance(URLDictionary.URL_IMAGEM, HttpMethods.POST, true, true, "");
            return super.insert(connection, jsonObject.toString());
            //}

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;

    }

}
