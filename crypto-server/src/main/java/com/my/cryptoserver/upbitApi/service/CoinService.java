package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.dto.UpbitApiDto;

import java.util.List;

public interface CoinService
{
    List<UpbitApiDto> getCoinList();
}
