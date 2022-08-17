package com.my.cryptoserver.webinf.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.my.cryptoserver.webinf.service.OkHttpServiceImpl;
import lombok.Data;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class UpbitWebSocketListener extends WebSocketListener
{
    private static final Logger log = LogManager.getLogger(UpbitWebSocketListener.class);
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        log.debug("Socket Closed1 : {} / {}\r\n", code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        log.debug("Socket Closing : {} / {}\n", code, reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        webSocket.cancel();
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        log.debug("Socket Error : " + t.getMessage());
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        log.debug("Socket text : {}", text);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes)
    {
        log.debug("Socket bytes : {}", bytes);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response)
    {
        webSocket.close(NORMAL_CLOSURE_STATUS, null); //없을 경우 끊임없이 서버와 통신함
    }

    @Data(staticConstructor = "of")
    private static class Ticket {
        private final String ticket;
    }

    @Data(staticConstructor = "of")
    private static class Type {
        private final String type;
        private final List<String> codes; // market
        private Boolean isOnlySnapshot = false;
        private Boolean isOnlyRealtime = true;
    }
}
