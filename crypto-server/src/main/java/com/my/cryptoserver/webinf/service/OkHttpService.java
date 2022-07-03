package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;

import java.util.Map;

public interface OkHttpService extends WebInfService
{
    public Map execHttpGet(WebInfDto webInfDto);

    public Map execHttpPost(WebInfDto webInfDto);

    public Map execHttpPut(WebInfDto webInfDto);

    public Map execHttpDelete(WebInfDto webInfDto);
}
