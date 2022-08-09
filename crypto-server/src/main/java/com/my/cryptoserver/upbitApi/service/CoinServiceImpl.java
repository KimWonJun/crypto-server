package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;
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
    public List<UpbitApiVO> getCoinList()
    {
        return coinMapper.getCoinList();
    }

    @Override
    public int insertCoinPrice(UpbitApiVO upbitApiVO)
    {
        return coinMapper.insertCoinPrice(upbitApiVO);
    }

    @Override
    public List<UpbitApiVO> getAllWaitOrder() {
        return coinMapper.getAllWaitOrder();
    }
}
