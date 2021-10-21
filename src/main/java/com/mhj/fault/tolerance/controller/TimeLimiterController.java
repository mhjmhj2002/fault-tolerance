package com.mhj.fault.tolerance.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TimeLimiterController {

    @GetMapping("/getMessageTL")
    @TimeLimiter(name = "getMessageTL")
    public CompletableFuture<String> getMessage() {
       return CompletableFuture.supplyAsync(this::getResponse);
    }

    private String getResponse() {

       if (Math.random() < 0.4) {       //Expected to fail 40% of the time
           return "Executing Within the time Limit...";
       } else {
           try {
               log.info("Getting Delayed Execution");
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       return "Exception due to Request Timeout.";
    }

}
