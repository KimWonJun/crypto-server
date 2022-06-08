package com.my.cryptoserver.upbitApi.controller;

import com.my.cryptoserver.test.controller.TestController;
import com.my.cryptoserver.upbitApi.dto.UpbitApiDto;
import com.my.cryptoserver.upbitApi.service.CoinService;
import com.my.cryptoserver.upbitApi.service.UpbitApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Controller
public class UpbitApiController
{
    private static final Logger log = LogManager.getLogger(UpbitApiController.class);

    @Autowired
    private UpbitApiService upbitApiService;

    @Autowired
    private CoinService coinService;

    @RequestMapping(value="/account", method= RequestMethod.GET)
    public Map getAllAccounts() throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        return upbitApiService.getAllAccounts();
    }

    @RequestMapping(value="/orderChance", method= RequestMethod.GET)
    public Map getOrderChance() throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        List<UpbitApiDto> coinList = coinService.getCoinList();
        log.debug("coinList : {}", coinList);

        return upbitApiService.getOrderChance();
    }
}
