package com.my.cryptoserver.webinf.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class UpbitWebSocketListener extends WebSocketListener
{
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private String json;
    private String siseType;

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closed : %s / %s\r\n", code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closing : %s / %s\n", code, reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        webSocket.cancel();
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        System.out.println("Socket Error : " + t.getMessage());
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        System.out.println(text.toString());
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        System.out.println(bytes.string(Charset.defaultCharset()));
//        switch(siseType) {
//            case "TRADE":
//                TradeResult tradeResult = JsonUtil.fromJson(bytes.string(StandardCharsets.UTF_8), TradeResult.class);
//                System.out.println(tradeResult);
//                break;
//            case "TICKER":
//                TickerResult result = JsonUtil.fromJson(bytes.string(StandardCharsets.UTF_8), TickerResult.class);
//                System.out.println(result);
//                break;
//            case "ORDERBOOK":
//                System.out.println(JsonUtil.fromJson(bytes.string(StandardCharsets.UTF_8), OrderBookResult.class));
//                break;
//            default:
//                throw new RuntimeException("지원하지 않는 웹소켓 조회 유형입니다. : " + siseType.getType());
//        }

    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        webSocket.send(getParameter());
        webSocket.close(NORMAL_CLOSURE_STATUS, null); // 없을 경우 끊임없이 서버와 통신함
    }

    public void setParameter(String siseType, List<String> codes) {
        this.siseType = siseType;
    }

    private String getParameter() {
        return this.json;
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
