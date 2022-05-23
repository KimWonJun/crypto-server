package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public interface WebInfService
{
    Map execHttpClient(HttpRequestBase httpRequest, WebInfDto webInfDto);

    String testApi() throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
