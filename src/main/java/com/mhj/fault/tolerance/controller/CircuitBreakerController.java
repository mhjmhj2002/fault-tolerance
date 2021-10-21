package com.mhj.fault.tolerance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CircuitBreakerController {
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getInvoiceCb")
    @CircuitBreaker(name = "getInvoiceCB", fallbackMethod = "getInvoiceFallback") 
    public String getInvoice() { 
       log.info("getInvoice() call starts here");
       ResponseEntity<String> entity= restTemplate.getForEntity("http://localhost:8080/invoice/rest/find/2", String.class);
       log.info("Response :" + entity.getStatusCode());
       return entity.getBody();
    }

    public String getInvoiceFallback(Exception e) {
       log.info("---RESPONSE FROM FALLBACK METHOD---");
       return "SERVICE IS DOWN, PLEASE TRY AFTER SOMETIME !!!";
    }

}
