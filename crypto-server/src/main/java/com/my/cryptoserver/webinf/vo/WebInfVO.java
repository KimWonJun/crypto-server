package com.my.cryptoserver.webinf.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebInfVO
{
    private String uri;             // 호출 URL
    private String method;          // 호출 메서드
    private Map paramMap;           // 파라미터 목록
    private Map entityMap;          //
    private Map extraHeaderInfo;    // 추가 헤더 정보
}
