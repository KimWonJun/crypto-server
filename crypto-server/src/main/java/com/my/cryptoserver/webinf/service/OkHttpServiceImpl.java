package com.my.cryptoserver.webinf.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my.cryptoserver.upbitApi.dto.UpbitApiDTO;
import com.my.cryptoserver.webinf.dto.WebInfDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    private UpbitApiDTO upbitApiDto;

    @Override
    public Map execHttpGet(WebInfDto webInfDto)
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        HttpUrl.Builder httpBuilder = HttpUrl.parse(webInfDto.getUri()).newBuilder();
        httpBuilder.addQueryParameter("markets", "KRW-BTC");

        Request request = new Request.Builder().url(httpBuilder.build()).build();

        String responseBody = "";
        try(Response response = client.newCall(request).execute()){

            responseBody = response.body().string();
            log.debug("responseBody : {}", responseBody);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        client.dispatcher().executorService().shutdown();
        Map resultMap = new HashMap();
        resultMap.put("responseBody", responseBody);

        return resultMap;
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
