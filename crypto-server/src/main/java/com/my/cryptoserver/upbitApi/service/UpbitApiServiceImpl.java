package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;
import com.my.cryptoserver.webinf.service.WebInfService;
import com.my.cryptoserver.webinf.service.WebInfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpbitApiServiceImpl implements UpbitApiService
{
    @Autowired
    private WebInfService webInfService;

    @Override
    public Map getAllAccounts(WebInfDto webInfDto) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        webInfDto.setUri("/v1/accounts?");

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

        webInfDto.setUri("/v1/orders");

        return null;
    }

    @Override
    public String orderChance() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public String orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public String candleMinute() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public String candleDay() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public String candleWeek() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public String candleMonth() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return null;
    }
}
