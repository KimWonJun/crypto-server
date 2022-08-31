package com.my.cryptoserver.plugin.slack.service;

public interface SlackApiService
{
    public String sendMessage(String id, String text);
}
