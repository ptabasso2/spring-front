package com.datadog.pej.front;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.datadog.pej.front.*;

//@RequestMapping("/upstream")
@RestController
public class BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("#{environment['url'] ?: 'http://localhost:8088'}")
    private String url;

    public long count;

    //@RequestMapping("/second/{id}/third")
    @RequestMapping("*/*/*/*")
    public String upstream() {
        int randomValue = 5 + (int)(Math.random() * ((10 - 5) + 1));
        count = randomValue;
        processingData(randomValue);

        //Quote quote = restTemplate.getForObject(url + "/downstream", Quote.class);
        Quote quote = createQuote();
        processingQuote(quote);
        postProcessingQuote(quote);
        LOGGER.info("PathVariable value: ");
        return quote.toString()+"\n";
    }

    public String processingQuote(Quote quote){
        count = quote.getValue().getId();
        return quote.getType();
    }

    public Quote postProcessingQuote(Quote quote){
        count = quote.getValue().getId() + 12;
        return quote;
    }

    public String processingData(int val){
        String localValue;
        localValue = "Alpha" + val;
        count = val+18;
        return localValue;
    }

    public Quote createQuote(){
        com.datadog.pej.front.Value value = new com.datadog.pej.front.Value();
        value.setQuote("How it works!");
        value.setId(10 + (long)(Math.random() * ((20 - 10) + 1)));
        Quote quote = new Quote();
        quote.setType("Success");
        quote.setValue(value);
        return quote;
    }

}
