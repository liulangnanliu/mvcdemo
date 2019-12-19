package com.example.mvcdemo.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class DemoAsyncListener implements AsyncListener{
    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        HttpServletResponse suppliedResponse = (HttpServletResponse)event.getSuppliedResponse();
        suppliedResponse.getHeader("test");
        log.info("onComplete-thread-name :{}", Thread.currentThread().getName());
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        log.info("onTimeout");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        log.info("onError");
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        log.info("onStartAsync");
    }
}
