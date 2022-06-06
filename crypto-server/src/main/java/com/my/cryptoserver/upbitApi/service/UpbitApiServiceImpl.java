package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.test.controller.TestController;
import com.my.cryptoserver.webinf.dto.WebInfDto;
import com.my.cryptoserver.webinf.service.WebInfService;
import com.my.cryptoserver.webinf.service.WebInfServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpbitApiServiceImpl implements UpbitApiService
{
    private static final Logger log = LogManager.getLogger(UpbitApiService.class);

    @Autowired
    private WebInfService webInfService;

    @Override
    public Map getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        log.debug("upbitApiService getallAccounts");

        WebInfDto webInfDto = new WebInfDto();

        webInfDto.setUri("/v1/accounts?");
        webInfDto.setMethod("GET");

        return webInfService.execHttpGet(webInfDto);
    }

    @Override
    public Map getOrderChance() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        log.debug("upbitApiService getOrderChance");

        WebInfDto webInfDto = new WebInfDto();

        HashMap<String, String> params = new HashMap<>();
        params.put("market", "KRW-BTC");

        webInfDto.setParamMap(params);

        webInfDto.setUri("/v1/orders/chance?");
        webInfDto.setMethod("GET");

        return webInfService.execHttpGet(webInfDto);
    }

    @Override
    public Map postOrder(WebInfDto webInfDto) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("market", "KRW-BTC");
        params.put("side", "bid");
        params.put("volume", "0.01");
        params.put("price", "100");
        params.put("ord_type", "limit");

        webInfDto.setParamMap(params);

        webInfDto.setUri("/v1/orders");
        webInfDto.setMethod("POST");

        return webInfService.execHttpPost(webInfDto);
    }

    @Override
    public Map orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public Map candleMinute() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public Map candleDay() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public Map candleWeek() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public Map candleMonth() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }
}
