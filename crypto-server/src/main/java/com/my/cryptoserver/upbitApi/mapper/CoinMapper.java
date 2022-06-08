package com.my.cryptoserver.upbitApi.mapper;

import com.my.cryptoserver.upbitApi.dto.UpbitApiDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoinMapper
{
    List<UpbitApiDto> getCoinList();
}
