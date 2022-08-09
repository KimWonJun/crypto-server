package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;

import java.util.List;

public interface CoinService
{
    List<UpbitApiVO> getCoinList();

    int insertCoinPrice(UpbitApiVO upbitAPIVO);





    List<UpbitApiVO> getAllWaitOrder();
}
