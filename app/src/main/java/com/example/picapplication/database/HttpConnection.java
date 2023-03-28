package com.example.picapplication.database;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpConnection {
    private String url;
    private CloseableHttpClient httpClient;
    private JSONObject response;
    private List<NameValuePair> params = new ArrayList<>();

    public HttpConnection(String url){
        this.url = url;
        httpClient = HttpClients.createDefault();
    }
    public void send(){
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            this.response = new JSONObject(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setParams(List<NameValuePair> params){
        this.params = params;
    }

    public void addParam(String key, String value){
        params.add(new BasicNameValuePair(key,value));
    }
    public JSONObject getResponse() {
        return response;
    }
}