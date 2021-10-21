package com.mhj.fault.tolerance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BulkheadController {

    @GetMapping("/getMessage")
    @Bulkhead(name = "getMessageBH", fallbackMethod = "getMessageFallBack")
    public ResponseEntity<String> getMessage(@RequestParam(value="name", defaultValue = "Hello") String name){

       return ResponseEntity.ok().body("Message from getMessage() :" +name);
    }

    public ResponseEntity<String> getMessageFallBack(RequestNotPermitted exception) {

       log.info("Bulkhead has applied, So no further calls are getting accepted");

       return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
      .body("Too many requests : No further request will be accepted. Plese try after sometime");
    }

}
