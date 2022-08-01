package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.dto.UpbitApiDTO;
import com.my.cryptoserver.webinf.dto.WebInfDto;
import com.my.cryptoserver.webinf.service.HttpService;
import com.my.cryptoserver.webinf.service.OkHttpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpbitApiServiceImpl implements UpbitApiService
{
    private static final Logger log = LogManager.getLogger(UpbitApiService.class);

    @Autowired
    private HttpService webInfService;

    @Autowired
    private OkHttpService okHttpService;

    @Autowired
    private CoinService coinService;

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

        List<UpbitApiDTO> coinList = coinService.getCoinList();
        log.debug("coinList : {}", coinList);

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
    public Map getCoinPrice() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        WebInfDto webInfDto = new WebInfDto();

        HashMap<String, String> params = new HashMap<>();
        List<String> coinList = new ArrayList<String>();
        coinList.add("KRW-BTC");
        params.put("markets", "KRW-BTC");

        webInfDto.setParamMap(params);

        webInfDto.setUri("/v1/ticker");
        webInfDto.setMethod("GET");

        return okHttpService.execHttpGet(webInfDto);
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
