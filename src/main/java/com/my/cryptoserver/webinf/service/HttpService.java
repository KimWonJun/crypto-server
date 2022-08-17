package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.vo.WebInfVO;

import java.util.Map;

public interface HttpService extends WebInfService
{
    public Map execHttpGet(WebInfVO webInfVO);

    public Map execHttpPost(WebInfVO webInfVO);

    public Map execHttpPut(WebInfVO webInfVO);

    public Map execHttpDelete(WebInfVO webInfVO);
}
