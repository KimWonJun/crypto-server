package com.my.cryptoserver.webinf.dto;

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
public class WebInfDto
{
    private String uri;
    private String method;
    private Map paramMap;
    private Map entityMap;
    private Map extraHeaderInfo;
}
