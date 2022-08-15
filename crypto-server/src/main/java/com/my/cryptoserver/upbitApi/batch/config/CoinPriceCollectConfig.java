package com.my.cryptoserver.upbitApi.batch.config;

import com.my.cryptoserver.upbitApi.service.CoinService;
import com.my.cryptoserver.upbitApi.service.UpbitApiService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableBatchProcessing      // 배치 기능 활성화
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
//@Configuration
public class CoinPriceCollectConfig
{
    private static final Logger log = LogManager.getLogger(CoinPriceCollectConfig.class);
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음

    @Autowired
    private CoinService coinService;

    @Autowired
    private UpbitApiService upbitApiService;

    @Bean
    public Job coinPriceCollectJob() {
        Job job =  jobBuilderFactory.get("coinPriceCollectJob")
                    .incrementer(new RunIdIncrementer())
                    .start(getCoinPriceStep())
                    .build();
        return job;
    }

    @Bean
    public Step getCoinPriceStep()
    {
        return stepBuilderFactory.get("getCoinListStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is getCoinPriceStep");
                    upbitApiService.getCoinPrice();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
