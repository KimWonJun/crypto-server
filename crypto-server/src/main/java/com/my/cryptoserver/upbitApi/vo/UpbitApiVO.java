package com.my.cryptoserver.upbitApi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpbitApiVO
{
    private String coinId;              // 코인 id
    private String coinNameKo;          // 코인이름(한글)
    private String coinNameEng;         // 코인이름(영문)
    private String monitoringYn;        // 모니터링 여부

    private String market;              // 종목구분코드
    private int inputDate;              // 현재일자
    private int inputTime;              // 현재시간
    private double openingPrice;        // 시가
    private double highPrice;           // 고가
    private double lowPrice;            // 저가
    private double prevClosingPrice;    // 전일 종가(UTC 0시 기준)
    private double tradeVolume;         // 가장 최근 거래량
    private double accTradePrice;       // 누적 거래대금(UTC 0시 기준)
    private double accTradePrice24h;    // 24시간 누적 거래대금
    private double accTradeVolume;      // 누적 거래량(UTC 0시 기준)
    private double accTradeVolume24h;   // 24시간 누적 거래량
}
