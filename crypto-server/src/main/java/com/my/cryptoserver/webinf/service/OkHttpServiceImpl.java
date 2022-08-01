package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.my.cryptoserver.webinf.util.UpbitWebSocketListener;
import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OkHttpServiceImpl implements OkHttpService
{
    private static final Logger log = LogManager.getLogger(OkHttpServiceImpl.class);

    private UpbitWebSocketListener upbitWebSocketListener;

    @Override
    public Map execHttpGet(WebInfDto webInfDto)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/ticker?markets=KRW-BTC")
                .build();

        UpbitWebSocketListener webSocketListener = new UpbitWebSocketListener();
        webSocketListener.setParameter("TRADE", List.of("KRW-BTC"));

        WebSocket websocket = client.newWebSocket(request, webSocketListener);
        client.dispatcher().executorService().shutdown();


        return null;

//        Map returnMap = new HashMap();
//        Response response = null;
//
//        OkHttpClient client = new OkHttpClient();
//        StringBuilder urlSb = new StringBuilder();
//        urlSb.append("https://api.upbit.com");
//        urlSb.append(webInfDto.getUri());
//
//        Iterator<String> keys = webInfDto.getParamMap().keySet().iterator();
//        if(keys != null)
//        {
//            urlSb.append("?");
//        }
//        while(keys.hasNext())
//        {
//            String key = keys.next();
//            urlSb.append(key);
//            urlSb.append("=");
//            urlSb.append(webInfDto.getParamMap().get(key));
//        }
//
//        //HttpUrl.Builder httpUrlBuilder = HttpUrl.get(urlSb.toString()).newBuilder();
//
//        Request request = new Request.Builder()
//                .url(urlSb.toString())
//                .get()
//                .addHeader("Accept", "application/json")
//                .build();
//
//        try
//        {
//            response = client.newCall(request).execute();
//
//            log.debug("============================================================================");
//            log.debug(response);
//            log.debug("response : {}", response.body());
//            log.debug("============================================================================");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        log.debug("============================================================================");
//        log.debug(response);
//        log.debug("============================================================================");
//
//        return null;
    }

    @Override
    public Map execHttpPost(WebInfDto webInfDto) {
        return null;
    }

    @Override
    public Map execHttpPut(WebInfDto webInfDto) {
        return null;
    }

    @Override
    public Map execHttpDelete(WebInfDto webInfDto) {
        return null;
    }

    private Map setResultMap(CloseableHttpResponse httpResponse)
    {
        Map rtnMap = new HashMap();

        // statusCode
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        try
        {
            if((statusCode / 100) == 2)
            {
                rtnMap.put("resultMsg", "OK");
                rtnMap.put("result", httpResponse.getStatusLine());
                rtnMap.put("resultCode", httpResponse.getStatusLine().getStatusCode());
                rtnMap.put("resultEntity", EntityUtils.toString(httpResponse.getEntity()) );
            }
            else
            {
                rtnMap.put("resultMsg", "ERROR");
                rtnMap.put("result", httpResponse.getStatusLine());
                rtnMap.put("resultCode", httpResponse.getStatusLine().getStatusCode());
                rtnMap.put("resultEntity", EntityUtils.toString(httpResponse.getEntity()) );
            }

            log.debug("rtnMap: {}, {}", rtnMap, httpResponse.getStatusLine());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return rtnMap;
    }
}
