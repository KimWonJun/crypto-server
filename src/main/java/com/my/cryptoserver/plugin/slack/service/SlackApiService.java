package com.my.cryptoserver.plugin.slack.service;

public interface SlackApiService
{
    public String findConversation(String name);

    public String sendMessage(String id, String text);

    public String replyMessage(String id, String ts, String text);
}
