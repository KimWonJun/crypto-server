package com.my.cryptoserver.upbitApi.controller;

import com.my.cryptoserver.upbitApi.service.UpbitApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Controller
public class UpbitApiController
{
    private static final Logger log = LogManager.getLogger(UpbitApiController.class);

    @Autowired
    private UpbitApiService upbitApiService;

    @RequestMapping(value="/account", method= RequestMethod.GET)
    public Map getAllAccounts() throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        return upbitApiService.getAllAccounts();
    }

    @RequestMapping(value="/orderChance/{coinId}", method= RequestMethod.GET)
    public Map getOrderChance(@PathVariable String coinId) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        return upbitApiService.getOrderChance(coinId);
    }

    @RequestMapping(value="/order/{coinId}", method=RequestMethod.GET)
    public Map postOrder(@PathVariable String coinId) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        return upbitApiService.postOrder(coinId);
    }

    @RequestMapping(value="/getCoinPrice", method=RequestMethod.GET)
    public Map getCoinPrice() throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        return upbitApiService.getCoinPrice();
    }
}
