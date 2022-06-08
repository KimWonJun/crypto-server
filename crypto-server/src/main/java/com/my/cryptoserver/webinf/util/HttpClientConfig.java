package com.my.cryptoserver.webinf.util;

import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class HttpClientConfig
{
    private static final Logger log = LogManager.getLogger(HttpClientConfig.class);

    /**
     * 루트당 허용 Connection 개수
     */
    private static final int MAX_CONNECTIONS_PER_ROUTE = 50;

    /**
     * 허용 전체 Connection 개수
     */
    private static final int MAX_CONNECTIONS_TOTAL = 150;

    /**
     * idle 상태인 Connection을 축출하기 위한 대기 시간
     */
    private static final long EVICT_IDLE_TIMEOUT = 30 * 1000L; // 30초

    /**
     * 주기적으로 미사용 httpClient 객체를 반환하기 위한 시간
     */
    private static final int IDLE_TIMEOUT = 35 * 1000; // 35초

    @Bean
    public HttpClientBuilder cumtomHttpClient()
    {
        return HttpClients.custom()
                .evictIdleConnections(EVICT_IDLE_TIMEOUT, TimeUnit.MILLISECONDS)
                .setConnectionManager(poolingHttpClientConnectionManager());
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(
                        RegistryBuilder.<ConnectionSocketFactory> create()
                                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                                .register("https", sslConnectionSocketFactory())
                                .build());

        //Max Connection 수 및 Route 당 Connection 수 할당
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_ROUTE);
        connectionManager.setMaxTotal(MAX_CONNECTIONS_TOTAL);

        return connectionManager;
    }
}
