package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.dto.UpbitApiDTO;

import java.util.List;

public interface CoinService
{
    List<UpbitApiDTO> getCoinList();
}
