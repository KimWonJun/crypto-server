package com.my.cryptoserver.upbitApi.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;
import com.my.cryptoserver.webinf.vo.WebInfVO;
import com.my.cryptoserver.webinf.service.HttpService;
import com.my.cryptoserver.webinf.service.OkHttpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
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
    private HttpService webInfService;  // REST API 요청을 위한 Service

    @Autowired
    private OkHttpService okHttpService;    // WebSocket 요청을 위한 Service

    @Autowired
    private CoinService coinService;

    /**
     * author   : KimWonJun
     * date     : 2022.06.05
     * @return  :
     * @throws  : NoSuchAlgorithmException, UnsupportedEncodingException
     */
    @Override
    public Map getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        WebInfVO webInfVO = new WebInfVO();

        webInfVO.setUri("/v1/accounts?");
        webInfVO.setMethod("GET");

        return webInfService.execHttpGet(webInfVO);
    }

    @Override
    public Map getOrderChance(String coinId) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        WebInfVO webInfVO = new WebInfVO();

        HashMap<String, String> params = new HashMap<>();
        params.put("market", coinId);

        webInfVO.setParamMap(params);

        webInfVO.setUri("/v1/orders/chance?");
        webInfVO.setMethod("GET");

        return webInfService.execHttpGet(webInfVO);
    }

    @Override
    public Map postOrder(String coinId) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        Map coinMap = getOrderChance(coinId);
        coinMap.get("resultEntity");

        HashMap<String, String> params = new HashMap<>();
        params.put("market", coinId);
        params.put("side", "ask");
        params.put("volume", "0.01");
        params.put("price", "100");
        params.put("ord_type", "limit");

        WebInfVO webInfVO = new WebInfVO();

        webInfVO.setParamMap(params);

        webInfVO.setUri("/v1/orders");
        webInfVO.setMethod("POST");

        return webInfService.execHttpPost(webInfVO);
    }

    @Override
    public Map getCoinPrice() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        WebInfVO webInfVO = new WebInfVO();

        HashMap<String, String> params = new HashMap<>();

        List<UpbitApiVO> coinList = coinService.getCoinList();
        StringBuilder coinIdSb = new StringBuilder();
        int coinListSize = coinList.size();
        for(int index = 0; index < coinListSize; index++)
        {
            if(index > 0)
            {
                coinIdSb.append(",");     // coin이 여러개인 경우 반점으로 구분
            }
            coinIdSb.append(coinList.get(index).getCoinId());
        }
        params.put("markets", coinIdSb.toString());
        webInfVO.setParamMap(params);

        webInfVO.setUri("https://api.upbit.com/v1/ticker");
        webInfVO.setMethod("GET");

        Map resultMap = okHttpService.execHttpGet(webInfVO);

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type listType = new TypeToken<ArrayList<UpbitApiVO>>(){}.getType();
        List<UpbitApiVO> resultList = gson.fromJson(resultMap.get("responseBody").toString(), listType);

        int listSize = resultList.size();
        for(int index = 0; index < listSize; index++)
        {
            coinService.insertCoinPrice(resultList.get(index));
        }

        return resultMap;
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
