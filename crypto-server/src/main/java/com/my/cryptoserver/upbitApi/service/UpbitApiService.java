package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.webinf.vo.WebInfVO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UpbitApiService
{
    Map getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map getOrderChance(String coinId) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map getCoinPrice() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map postOrder(String coinId) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map candleMinute() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleDay() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleWeek() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleMonth() throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
