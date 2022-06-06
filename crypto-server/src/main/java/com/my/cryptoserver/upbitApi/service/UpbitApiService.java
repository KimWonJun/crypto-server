package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UpbitApiService
{
    Map getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map getOrderChance() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map postOrder(WebInfDto webInfDto) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map candleMinute() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleDay() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleWeek() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleMonth() throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
