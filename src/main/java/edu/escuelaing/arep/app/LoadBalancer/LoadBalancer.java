package edu.escuelaing.arep.app.LoadBalancer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoadBalancer {
    private OkHttpClient httpClient;
    private String baseUrl="http://172.17.0.1";
    private String[] ports = {":8087",":8088",":8089"};
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private int numberOfServers = 0;

    public LoadBalancer() {
        httpClient = new OkHttpClient();
    }

    public void loadbalancer() {
        numberOfServers= (numberOfServers+1) % ports.length;
    }

    public String post(String message, String path) throws Exception{
        RequestBody body = RequestBody.create(message,JSON);
        Request request = new Request.Builder()
                .url(baseUrl+ports[numberOfServers]+path)
                .post(body)
                .build();
        System.out.println("post enviado a "+baseUrl+ports[numberOfServers]+path);
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public String get(String path) throws Exception{
        Request request = new Request.Builder()
                .url(baseUrl+ports[numberOfServers]+path)
                .get()
                .build();
        Response response = httpClient.newCall(request).execute();
        System.out.println("get enviado a "+baseUrl+ports[numberOfServers]+path);
        return response.body().string();
    }
}
