package com.my.cryptoserver.upbitApi.service;

import com.my.cryptoserver.upbitApi.vo.UpbitApiVO;
import com.my.cryptoserver.webinf.vo.WebInfVO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public interface UpbitApiService
{
    /**
     * methodName : getAllAccounts
     * author : KimWonJun
     * description : 전체 계좌 조회 API 호출
     * 작성일 : 2022-08-17
     */
    Map getAllAccounts() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    /**
     * methodName : getOrderChance
     * author : KimWonJun
     * description : 주문 가능 정보 조회 API 호출
     * 작성일 : 2022-08-17
     */
    Map getOrderChance(String coinId) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    /**
     * methodName : getCoinPrice
     * author : KimWonJun
     * description : 모니터링 대상 코인 조회 -> 코인 가격 조회 API 호출 -> 코인가격 DB에 입력
     * 작성일 : 2022-08-17
     */
    Map getCoinPrice() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    /**
     * methodName : postOrder
     * author : KimWonJun
     * description : 주문 API 호출
     * 작성일 : 2022-08-17
     */
    Map postOrder(String coinId) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    /**
     * methodName : getOrderStatus
     * author : KimWonJun
     * description : 주문 상태 조회 API 호출
     * 작성일 : 2022-08-17
     */
    Map getOrderStatus(String uuid);

    /**
     * methodName : deleteOrder
     * author : KimWonJun
     * description : 주문 삭제 API 호출
     * 작성일 : 2022-08-17
     */
    Map deleteOrder();

    Map orderSeperate() throws NoSuchAlgorithmException, UnsupportedEncodingException;

    Map candleMinute() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleDay() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleWeek() throws NoSuchAlgorithmException, UnsupportedEncodingException;
    Map candleMonth() throws NoSuchAlgorithmException, UnsupportedEncodingException;


}
