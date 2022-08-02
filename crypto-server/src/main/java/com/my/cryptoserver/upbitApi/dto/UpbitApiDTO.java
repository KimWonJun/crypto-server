package com.my.cryptoserver.upbitApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpbitApiDTO
{
    private String coinId;
    private String coinNameKo;
    private String coinNameEng;
    private String monitoringYn;


}
