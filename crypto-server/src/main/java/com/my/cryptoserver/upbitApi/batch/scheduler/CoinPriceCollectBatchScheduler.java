package com.my.cryptoserver.upbitApi.batch.scheduler;

import com.my.cryptoserver.upbitApi.batch.config.CoinPriceCollectConfig;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class CoinPriceCollectBatchScheduler
{
    private static final Logger log = LogManager.getLogger(TestBatchScheduler.class);
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @Autowired
    private CoinPriceCollectConfig coinPriceCollectConfig;

    @Scheduled(cron = "0/10 * * * * *")
    public void runBatch() throws JobInstanceAlreadyCompleteException
            , JobExecutionAlreadyRunningException
            , JobParametersInvalidException
            , JobRestartException
    {
        // Job 실행
        JobExecution jobExecution = jobLauncher.run(coinPriceCollectConfig.coinPriceCollectJob()
                , new JobParametersBuilder(jobExplorer)
                        .getNextJobParameters(coinPriceCollectConfig.coinPriceCollectJob())  // Job 파라미터 자동 increment 세팅
                        .toJobParameters());

        // Job 실행정보 출력
        log.info("Job execution : {}", jobExecution.getStatus());
        log.info("Job Config name : {}", jobExecution.getJobConfigurationName());
    }
}
