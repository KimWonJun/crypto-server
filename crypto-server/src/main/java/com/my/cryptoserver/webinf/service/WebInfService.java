package com.my.cryptoserver.webinf.service;

import com.my.cryptoserver.webinf.dto.WebInfDto;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public interface WebInfService
{
    Map execHttpGet(WebInfDto webInfDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Map execHttpPost(WebInfDto webInfDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Map execHttpPut(WebInfDto webInfDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Map execHttpDelete(WebInfDto webInfDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
