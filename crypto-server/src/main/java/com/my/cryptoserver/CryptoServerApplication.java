package com.my.cryptoserver;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableBatchProcessing	// 배치 기능 활성화
@EnableScheduling
@SpringBootApplication
public class CryptoServerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(CryptoServerApplication.class, args);
	}
}
