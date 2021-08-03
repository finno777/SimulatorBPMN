package com.tsystems.simulator.service;

import com.tsystems.simulator.exception.QueueException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface JsonParserService {

    void mainParse(String json) throws QueueException, MalformedURLException, URISyntaxException;
}
