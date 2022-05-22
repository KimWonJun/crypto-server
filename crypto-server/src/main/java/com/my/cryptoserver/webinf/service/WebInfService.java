package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.HashMap;
import java.util.Map;

public interface WebInfService
{
    private Map execHttpClient(HttpRequestBase httpRequest, WebInfDto webInfDto)
    {
        Map map = new HashMap<String, String>();

        return map;
    }
}
