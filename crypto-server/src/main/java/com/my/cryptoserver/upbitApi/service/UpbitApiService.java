package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UpbitApiService
{
    Map getAllAccounts(WebInfDto webInfDto) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map postOrder(WebInfDto webInfDto) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String orderChance() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String candleMinute() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    String candleDay() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    String candleWeek() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    String candleMonth() throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
