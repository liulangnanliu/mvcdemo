package com.example.mvcdemo.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class DemoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("DemoServlet-thread-name :{}", Thread.currentThread().getName());
        AsyncContext asyncContext = req.startAsync();

        new Thread(() -> {
            try {
                log.info("thread-name :{}", Thread.currentThread().getName() + " start");
                resp.getWriter().print("first write");
                resp.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "firstWriteThread").start();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread-name :{}", Thread.currentThread().getName() + " run complete");
            try {
                resp.getWriter().print("hello world");
            } catch (IOException e) {
                e.printStackTrace();
            }
            asyncContext.complete();
        }, "myDemoThread").start();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
