package com.example.mvcdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public class SocketClient {
    public static void main(String[] args) {
        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/demo/demo-servlet/say");
            HttpResponse execute = httpClient.execute(httpGet);
            log.info("HttpGet response : " + EntityUtils.toString(execute.getEntity(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
