package com.my.cryptoserver.upbitApi.batch.config;

import com.my.cryptoserver.batch.service.QuartzService;
import com.my.cryptoserver.upbitApi.batch.scheduler.CoinPriceCollectBatchJob;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

//@EnableBatchProcessing      // 배치 기능 활성화
@Configuration
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
public class CoinPriceCollectConfig
{
    private static final Logger log = LogManager.getLogger(CoinPriceCollectConfig.class);
    private final SchedulerFactoryBean schedulerFactoryBean;

    private final QuartzService quartzService;

    /**
     * methodName : init
     * author : Kim WonJun
     * description : 코인 가격 수집 배치 config 클래스 init 메서드
     * 작성일 : 2022.08.16
     */
    @PostConstruct
    public void init()
    {
        try
        {
            quartzService.addCronJob(CoinPriceCollectBatchJob.class, "coinPriceCollect", "코인 가격 수집 배치", null, "0/10 * * ? * * *");
        }
        catch (SchedulerException e)
        {
            throw new RuntimeException(e);
        }
    }
}
