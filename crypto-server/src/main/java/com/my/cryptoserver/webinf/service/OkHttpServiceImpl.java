package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;
import com.my.cryptoserver.webinf.vo.WebInfVO;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.my.cryptoserver.webinf.util.UpbitWebSocketListener;
import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class OkHttpServiceImpl implements OkHttpService
{
    private static final Logger log = LogManager.getLogger(OkHttpServiceImpl.class);

    private UpbitWebSocketListener upbitWebSocketListener;

    private UpbitApiVO upbitApiVO;

    @Override
    public Map execHttpGet(WebInfVO webInfVO)
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        HttpUrl.Builder httpBuilder = HttpUrl.parse(webInfVO.getUri()).newBuilder();

        Iterator<String> keys = webInfVO.getParamMap().keySet().iterator();
        while (keys.hasNext())
        {
            String key = keys.next();
            httpBuilder.addQueryParameter(key, (String) webInfVO.getParamMap().get(key));
        }

        Request request = new Request.Builder().url(httpBuilder.build()).build();

        String responseBody = "";
        try(Response response = client.newCall(request).execute())
        {
            responseBody = response.body().string();
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
    public Map execHttpPost(WebInfVO webInfVO) {
        return null;
    }

    @Override
    public Map execHttpPut(WebInfVO webInfVO) {
        return null;
    }

    @Override
    public Map execHttpDelete(WebInfVO webInfVO) {
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
