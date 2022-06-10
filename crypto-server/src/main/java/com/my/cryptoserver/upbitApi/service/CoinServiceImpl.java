package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.dto.UpbitApiDTO;
import com.my.cryptoserver.upbitApi.mapper.CoinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinServiceImpl implements CoinService
{
    @Autowired
    private CoinMapper coinMapper;

    @Override
    public List<UpbitApiDTO> getCoinList()
    {
        return coinMapper.getCoinList();
    }


}
