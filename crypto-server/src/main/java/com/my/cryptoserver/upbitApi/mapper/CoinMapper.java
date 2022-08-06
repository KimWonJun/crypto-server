package com.my.cryptoserver.upbitApi.mapper;

import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoinMapper
{
    List<UpbitApiVO> getCoinList();

    int insertCoinPrice(UpbitApiVO upbitApiVO);
}
