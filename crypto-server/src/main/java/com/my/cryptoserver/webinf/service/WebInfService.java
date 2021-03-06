package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public interface WebInfService
{
    public Map execHttpGet(WebInfDto webInfDto);

    public Map execHttpPost(WebInfDto webInfDto);

    public Map execHttpPut(WebInfDto webInfDto);

    public Map execHttpDelete(WebInfDto webInfDto);
}
