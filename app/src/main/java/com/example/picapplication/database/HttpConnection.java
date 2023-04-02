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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpConnection{
    private String url;
    private JSONObject params = new JSONObject();
    private JSONObject response = new JSONObject();
    public HttpConnection(String url){
        this.url = url;
    }
    public void addParam(String key, String value){
        try {
            params.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the request to the server and get the response
     */
    public void post(){
        try {
            String postJsonData = params.toString();
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postJsonData);
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            con.disconnect();
            System.out.println(response.toString());
            //printing result from response
            this.response = new JSONObject(response.toString());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public boolean isSuccessful(){
        try {
            if(response.getString("status").equals("success")){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    public JSONObject getResponse(){
        return response;
    }
}