package com.my.cryptoserver.plugin.slack.service;

import com.my.cryptoserver.upbitApi.controller.UpbitApiController;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
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

    @Value("slack.app")
    private String app;
    @Value("slack.token")
    private String token;
    @Value("slack.channel")
    private String channel;

    @Override
    public String sendMessage(String id, String text) {
        // you can get this instance via ctx.client() in a Bolt app
        MethodsClient client = Slack.getInstance().methods();
        ChatPostMessageResponse result = new ChatPostMessageResponse();
        try {
            // Call the chat.postMessage method using the built-in WebClient
            result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(token)
                            .channel(id)
                            .text(text)
                    // You could also use a blocks[] array to send richer content
            );
            // Print result, which includes information about the message (like TS)
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }

        return result.toString();
    }
}
