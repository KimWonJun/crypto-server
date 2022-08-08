package com.my.cryptoserver.upbitApi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

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

    // 주문가능정보
    private String bidFee;          // 매수 수수료 비율
    private String askFee;          // 매도 수수료 비율
    private String makerBidFee;     // ?
    private String makerAskFee;     // ?
    @JsonProperty("bid_account")
    private Map bidAccount;         // 매수 시 사용하는 화폐의 계좌 상태
    @JsonProperty("ask_account")
    private Map askAccount;         // 매도 시 사용하는 화폐의 계좌상태

    // 주문
    private String uuid;            // 주문 고유 id
    private String side;            // 주문 종류
    private String ordType;         // 주문방식
    private String price;           // 주문 당시 화폐 가격
    private String avgPrice;        // 체결가격의 평균가
    private String state;           // 주문 상태
    private String volume;          // 사용자가 입력한 주문량
    private String remainingVolume; // 체결 후 남은 주문량
    private String executedVolume;  // 체결된 양

    // 시세 Ticker
    private String market;              // 종목구분코드
    private int inputDate;              // 현재일자
    private int inputTime;              // 현재시간
    private double openingPrice;        // 시가
    private double highPrice;           // 고가
    private double lowPrice;            // 저가
    private double prevClosingPrice;    // 전일 종가(UTC 0시 기준)
    private double tradeVolume;         // 가장 최근 거래량
    private double accTradePrice;       // 누적 거래대금(UTC 0시 기준)
    private double accTradePrice_24h;    // 24시간 누적 거래대금
    private double accTradeVolume;      // 누적 거래량(UTC 0시 기준)
    private double accTradeVolume_24h;   // 24시간 누적 거래량


}
