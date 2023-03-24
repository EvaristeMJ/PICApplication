package com.example.picapplication.database;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HttpConnection {
    private String url;
    private CloseableHttpClient httpClient;
    private JSONObject response;
    private JSONObject params;

    public HttpConnection(String url){
        this.url = url;
        httpClient = HttpClients.createDefault();
    }
    private void Post(JSONObject params){
        try {
            CloseableHttpResponse response = httpClient.execute(new HttpPost(url));
            String responseString = EntityUtils.toString(response.getEntity());
            this.response = new JSONObject(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setParams(JSONObject params){
        this.params = params;
    }
    public void send(){
        Post(params);
    }
    public void put(String key, String value){
        try {
            params.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject getResponse() {
        return response;
    }
}