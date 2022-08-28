package com.my.cryptoserver.plugin.slack.service;

import com.my.cryptoserver.upbitApi.controller.UpbitApiController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackApiServiceImpl implements SlackApiService
{
    private static final Logger log = LogManager.getLogger(SlackApiServiceImpl.class);

    @Value("${slack.app}")
    private String app;
    @Value("${slack.token}")
    private String token;
    @Value("${slack.channel}")
    private String channel;

    @Override
    public String findConversation(String name) {

        log.info("app : {}", app);
        log.info("token : {}", token);
        log.info("channel : {}", channel);

        var client = Slack.getInstance().methods();
        String conversationId = "";
        try {
            // Call the conversations.list method using the built-in WebClient
            var result = client.conversationsList(r -> r
                    // The token you used to initialize your app
                    .token(System.getenv(token))
            );
            for (Conversation channel : result.getChannels()) {
                if (channel.getName().equals(name)) {
                    conversationId = channel.getId();
                    // Print result
                    log.info("Found conversation ID: {}", conversationId);
                    // Break from for loop
                    break;
                }
            }
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
        return conversationId;
    }

    @Override
    public String sendMessage(String id, String text) {
        return null;
    }

    @Override
    public String replyMessage(String id, String ts, String text) {
        return null;
    }
}
