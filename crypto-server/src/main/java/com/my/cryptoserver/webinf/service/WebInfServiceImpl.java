package com.my.cryptoserver.webinf.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.my.cryptoserver.webinf.dto.WebInfDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WebInfServiceImpl implements WebInfService
{
    private static final Logger log = LogManager.getLogger(WebInfServiceImpl.class);

    private static final String ACCESSKEY = "lBTxXTh63tVEO4EkQwSutcbsURndQh52jQfpxOa0";
    private static final String SECRETKEY = "jGXdrBlMrmVtJGFlA9xzktwksxDmujVD1x6XIJIU";
    private static final String SERVERURL = "https://api.upbit.com";

    @Override
    public Map execHttpGet(WebInfDto webInfDto) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        HttpGet httpRequest = new HttpGet();

        return execHttpClient(httpRequest, webInfDto);
    }

    

    private Map execHttpClient(HttpRequestBase httpRequest, WebInfDto webInfDto) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        log.debug("execHttpClient executed");

        Map rtnMap = new HashMap();

        // Query String parameter 가 존재하는 경우
        HashMap<String, String> params = new HashMap<>();

        if(webInfDto.getParamMap() != null && !webInfDto.getParamMap().isEmpty())
        {
            Map<String, String> map = webInfDto.getParamMap();

            for(Map.Entry<String, String> entry : map.entrySet())
            {
                params.put(entry.getKey(), entry.getValue());
            }
        }

        ArrayList<String> queryElements = new ArrayList<>();
        for(Map.Entry<String, String> entity : params.entrySet())
        {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }

        String queryString = String.join("&", queryElements.toArray(new String[0]));

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("UTF-8"));

        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

        Algorithm algorithm = Algorithm.HMAC256(SECRETKEY);
        String jwtToken = JWT.create()
                .withClaim("access_key", ACCESSKEY)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;

        switch(webInfDto.getMethod())
        {
            case "GET":
                httpRequest.setURI(URI.create(SERVERURL + webInfDto.getUri() + queryString));
                httpRequest.setHeader("Content-Type", "application/json");
                httpRequest.addHeader("Authorization", authenticationToken);

                try
                {
                    response = client.execute(httpRequest);
                    entity = response.getEntity();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;

            case "POST":
                try {
                    HttpPost postRequest = new HttpPost(SERVERURL + webInfDto.getUri());
                    postRequest.setHeader("Content-Type", "application/json");
                    postRequest.addHeader("Authorization", authenticationToken);
                    postRequest.setEntity(new StringEntity(new Gson().toJson(params)));

                    response = client.execute(postRequest);
                    entity = response.getEntity();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;

            case "PUT":
                break;

            case "DELETE":
                break;
        }
        rtnMap = setResultMap(response);

        try {
            log.debug(EntityUtils.toString(entity, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rtnMap;
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
