package com.my.cryptoserver.webinf.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.my.cryptoserver.webinf.dto.WebInfDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
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

    @Override
    public Map execHttpClient(HttpRequestBase httpRequest, WebInfDto webInfDto)
    {
        Map map = new HashMap<String, String>();
        return map;
    }

    @Override
    public String getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String accessKey = "lBTxXTh63tVEO4EkQwSutcbsURndQh52jQfpxOa0";
        String secretKey = "jGXdrBlMrmVtJGFlA9xzktwksxDmujVD1x6XIJIU";
        String serverUrl = "https://api.upbit.com";

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        StringBuffer result = new StringBuffer();

        CloseableHttpClient client = HttpClientBuilder.create().build();

        try
        {
            HttpGet request = new HttpGet(serverUrl + "/v1/accounts?");
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
        String accessKey = "lBTxXTh63tVEO4EkQwSutcbsURndQh52jQfpxOa0";
        String secretKey = "jGXdrBlMrmVtJGFlA9xzktwksxDmujVD1x6XIJIU";
        String serverUrl = "https://api.upbit.com";

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

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        StringBuffer result = new StringBuffer();

        CloseableHttpClient client = HttpClientBuilder.create().build();

        try
        {
            HttpGet request = new HttpGet(serverUrl + "/v1/orders/chance?" + queryString);
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
    public String orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String accessKey = "lBTxXTh63tVEO4EkQwSutcbsURndQh52jQfpxOa0";
        String secretKey = "jGXdrBlMrmVtJGFlA9xzktwksxDmujVD1x6XIJIU";
        String serverUrl = "https://api.upbit.com";

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

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        StringBuffer result = new StringBuffer();

        CloseableHttpClient client = HttpClientBuilder.create().build();

        try
        {
            HttpGet request = new HttpGet(serverUrl + "/v1/order?" + queryString);
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
}
