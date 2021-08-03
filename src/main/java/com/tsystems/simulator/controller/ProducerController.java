package com.tsystems.simulator.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tsystems.simulator.exception.QueueException;
import com.tsystems.simulator.service.impl.JsonParserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/producer/")
public class ProducerController {

    @Autowired
    private JsonParserServiceImpl parserService;

    @PostMapping(value = "test", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody String json) throws QueueException, MalformedURLException, URISyntaxException, JsonProcessingException {
        parserService.mainParse(json);
    }

//    @GetMapping(value = "{id}")
//    public String getMessage(@PathVariable("id") String id, @RequestParam(value = "fields") String fields){
//
//    }

}
