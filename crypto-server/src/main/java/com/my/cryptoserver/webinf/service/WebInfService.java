package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public interface WebInfService
{
    Map execHttpClient(HttpRequestBase httpRequest, WebInfDto webInfDto);

    String getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String orderChance() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String candleMinute() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    String candleDay() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    String candleWeek() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    String candleMonth() throws NoSuchAlgorithmException, UnsupportedEncodingException;

}
