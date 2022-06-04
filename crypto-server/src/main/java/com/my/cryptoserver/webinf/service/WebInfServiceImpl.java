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

    private Map execHttpClient(WebInfDto webInfDto) throws NoSuchAlgorithmException, UnsupportedEncodingException {
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
                try
                {
                    HttpGet getRequest = new HttpGet(SERVERURL + "/v1/accounts?" + queryString);
                    getRequest.setHeader("Content-Type", "application/json");
                    getRequest.addHeader("Authorization", authenticationToken);

                    response = client.execute(getRequest);
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
        try {
            if((statusCode / 100) == 2) {
                rtnMap.put("resultMsg", "OK");
                rtnMap.put("result", httpResponse.getStatusLine());
                rtnMap.put("resultCode", httpResponse.getStatusLine().getStatusCode());
                rtnMap.put("resultEntity", EntityUtils.toString(httpResponse.getEntity()) );

            } else {
                rtnMap.put("resultMsg", "ERROR");
                rtnMap.put("result", httpResponse.getStatusLine());
                rtnMap.put("resultCode", httpResponse.getStatusLine().getStatusCode());
                rtnMap.put("resultEntity", EntityUtils.toString(httpResponse.getEntity()) );

            }

            log.debug("rtnMap: {}, {}", rtnMap, httpResponse.getStatusLine());

        } catch(IOException e)
        {
            e.printStackTrace();
        }

        return rtnMap;

    }

    @Override
    public String getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        Algorithm algorithm = Algorithm.HMAC256(SECRETKEY);
        String jwtToken = JWT.create()
                .withClaim("access_key", ACCESSKEY)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        StringBuffer result = new StringBuffer();

        CloseableHttpClient client = HttpClientBuilder.create().build();

        try
        {
            HttpGet request = new HttpGet(SERVERURL + "/v1/accounts?");
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), Charset.forName("UTF-8")));
            String buffer = null;
            while((buffer=br.readLine()) != null)
            {
                result.append(buffer).append("\r\n");
            }
            log.debug("result : {}", result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try
            {
                client.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    @Override
    public String orderChance() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("market", "KRW-BTC");

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
        StringBuffer result = new StringBuffer();

        CloseableHttpClient client = HttpClientBuilder.create().build();

        try
        {
            HttpGet request = new HttpGet(SERVERURL + "/v1/orders/chance?" + queryString);
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try
            {
                client.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    @Override
    public String orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", "9ca023a5-851b-4fec-9f0a-48cd83c2eaae");

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
        StringBuffer result = new StringBuffer();

        CloseableHttpClient client = HttpClientBuilder.create().build();

        try
        {
            HttpGet request = new HttpGet(SERVERURL + "/v1/order?" + queryString);
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), Charset.forName("UTF-8")));
            String buffer = null;
            while((buffer=br.readLine()) != null)
            {
                result.append(buffer).append("\r\n");
            }
            log.debug("result : {}", result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try
            {
                client.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    public Map postOrders() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("market", "KRW-BTC");
        params.put("side", "bid");
        params.put("volume", "0.01");
        params.put("price", "100");
        params.put("ord_type", "limit");

        ArrayList<String> queryElements = new ArrayList<>();
        for(Map.Entry<String, String> entity : params.entrySet()) {
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

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(serverUrl + "/v1/orders");
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);
            request.setEntity(new StringEntity(new Gson().toJson(params)));

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            System.out.println(EntityUtils.toString(entity, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
