package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;

import java.util.List;

public interface CoinService
{
    List<UpbitApiVO> getCoinList();

    int insertCoinPrice(UpbitApiVO upbitApiVO);

    int insertCoinOrderResult(UpbitApiVO upbitApiVO);

    List<UpbitApiVO> getAllWaitOrder();
}
