package com.example.mvcdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

@Slf4j
@RestController
public class DemoController {


    @GetMapping(value = "/sayHello")
    public String sayHello() throws Exception {
        log.info("sayHello-thread-name :{}", Thread.currentThread().getName());
        return "hello world";
    }


    @GetMapping(value = "/test")
    public String test() {
        log.info("test-thread-name :{}", Thread.currentThread().getName());
        return "test";
    }

    @GetMapping("/events")
    public ResponseBodyEmitter handle() {
        log.info("emit-thread-name :{}", Thread.currentThread().getName() + " start");
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        new Thread(() -> {
            log.info("emit-thread-name :{}", Thread.currentThread().getName() + " hello once");
            try {
                emitter.send("Hello once ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("emit-thread-name :{}", Thread.currentThread().getName() + " hello again");
            try {
                emitter.send("Hello again ");
                emitter.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return emitter;
    }
}
