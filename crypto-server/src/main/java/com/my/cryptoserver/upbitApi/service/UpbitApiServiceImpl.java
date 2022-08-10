package com.my.cryptoserver.upbitApi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
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
    private CoinService coinService;    // 코인 정보 관리 Service

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
        Map coinMap = getOrderChance(coinId);       // 주문가능정보 확인
        coinMap.get("resultEntity");

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(coinMap.get("resultEntity").toString(), JsonObject.class);
        JsonObject askAccount = json.getAsJsonObject("ask_account");
        Map jsonMap = (Map)gson.fromJson(askAccount, HashMap.class);
        log.debug("currency : {}", jsonMap.get("currency"));

        // 시나리오 : 코인가격 하락 확인 -> 시장가 매도
        HashMap<String, String> params = new HashMap<>();
        params.put("market", coinId);
        params.put("side", "ask");      // 매도
        params.put("volume", (String) jsonMap.get("balance"));  // 주문가능 금액/수량 전체
        params.put("price", "7800");       // 시장가 매도시 불필요
        params.put("ord_type", "limit");   // 시장가 매도

        WebInfVO webInfVO = new WebInfVO();

        webInfVO.setParamMap(params);

        webInfVO.setUri("/v1/orders");
        webInfVO.setMethod("POST");
        Map returnMap = webInfService.execHttpPost(webInfVO);
        log.debug("toString : {}", returnMap.get("resultEntity").toString());
        // 테스트용 샘플 주문 결과 json String
//        String jsonSample = "{\n" +
//                "  \"uuid\": \"cdd92199-2897-4e14-9448-f923320408ad\",\n" +
//                "  \"side\": \"bid\",\n" +
//                "  \"ord_type\": \"limit\",\n" +
//                "  \"price\": \"100.0\",\n" +
//                "  \"avg_price\": \"0.0\",\n" +
//                "  \"state\": \"wait\",\n" +
//                "  \"market\": \"KRW-BTC\",\n" +
//                "  \"created_at\": \"2018-04-10T15:42:23+09:00\",\n" +
//                "  \"volume\": \"0.01\",\n" +
//                "  \"remaining_volume\": \"0.01\",\n" +
//                "  \"reserved_fee\": \"0.0015\",\n" +
//                "  \"remaining_fee\": \"0.0015\",\n" +
//                "  \"paid_fee\": \"0.0\",\n" +
//                "  \"locked\": \"1.0015\",\n" +
//                "  \"executed_volume\": \"0.0\",\n" +
//                "  \"trades_count\": 0\n" +
//                "}";

        Gson fieldGson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type voType = new TypeToken<UpbitApiVO>(){}.getType();
//        UpbitApiVO resultVO = fieldGson.fromJson(jsonSample, voType);
        UpbitApiVO resultVO = fieldGson.fromJson(returnMap.get("resultEntity").toString(), voType);
        log.debug("uuid : {}", resultVO.getUuid());
        log.debug("state : {}", resultVO.getState());

        coinService.insertCoinOrderResult(resultVO);        // 코인 주문 정보 입력

        return null;
    }

    /**
     * 주문 상태 확인
     * @param uuid
     * @return
     */
    @Override
    public Map getOrderStatus(String uuid)
    {
        WebInfVO webInfVO = new WebInfVO();

        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", uuid);

        webInfVO.setParamMap(params);

        webInfVO.setUri("/v1/order?");
        webInfVO.setMethod("GET");

        return webInfService.execHttpGet(webInfVO);
    }

    /**
     * 전체 주문 취소
     * @return
     */
    @Override
    public Map deleteOrder()
    {
        WebInfVO webInfVO = new WebInfVO();
        HashMap<String, String> params = new HashMap<>();

        List<UpbitApiVO> orderList = coinService.getAllWaitOrder();
        Map orderStatusMap = new HashMap<>();
        String uuid = "";

        Gson fieldGson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        for(UpbitApiVO vo : orderList)
        {
            uuid = vo.getUuid();
            orderStatusMap = getOrderStatus(uuid);

            Type voType = new TypeToken<UpbitApiVO>(){}.getType();
//        UpbitApiVO resultVO = fieldGson.fromJson(jsonSample, voType);
            UpbitApiVO resultVO = fieldGson.fromJson(orderStatusMap.get("resultEntity").toString(), voType);

            if(resultVO.getState() != "done")       // 아직 주문 완료가 아니라면
            {
                params.put("uuid", uuid);
                webInfVO.setParamMap(params);

                webInfVO.setUri("/v1/order?");
                webInfVO.setMethod("DELETE");

                webInfService.execHttpDelete(webInfVO);     // 취소 주문
            }
        }

        return null;
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
