package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface HttpService extends WebInfService
{
    public Map execHttpGet(WebInfDto webInfDto);

    public Map execHttpPost(WebInfDto webInfDto);

    public Map execHttpPut(WebInfDto webInfDto);

    public Map execHttpDelete(WebInfDto webInfDto);
}
