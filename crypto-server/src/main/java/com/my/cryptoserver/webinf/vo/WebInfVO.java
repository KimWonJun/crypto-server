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
    private String uri;
    private String method;
    private Map paramMap;
    private Map entityMap;
    private Map extraHeaderInfo;
}
