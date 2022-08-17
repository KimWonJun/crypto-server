package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;

import java.util.List;

public interface CoinService
{
    /**
     * methodName : getCoinList
     * author : KimWonJun
     * description : 모니터링 대상 코인 리스트 조회
     * 작성일 : 2022-08-17
     */
    List<UpbitApiVO> getCoinList();

    /**
     * methodName : insertCoinPrice
     * author : KimWonJun
     * description : 코인가격 입력
     * 작성일 : 2022-08-17
     */
    int insertCoinPrice(UpbitApiVO upbitApiVO);

    /**
     * methodName : insertCoinOrderResult
     * author : KimWonJun
     * description : 코인 주문 결과 입력
     * 작성일 : 2022-08-17
     */
    int insertCoinOrderResult(UpbitApiVO upbitApiVO);

    /**
     * methodName : getAllWaitOrder
     * author : KimWonJun
     * description : 대기중인 주문 조회
     * 작성일 : 2022-08-17
     */
    List<UpbitApiVO> getAllWaitOrder();
}
