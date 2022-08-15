package com.my.cryptoserver.upbitApi.batch.scheduler;

import com.my.cryptoserver.batch.util.UniqueRunIdIncrementer;
import com.my.cryptoserver.upbitApi.batch.config.TestBatchConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

//@Configuration
@RequiredArgsConstructor
public class TestBatchScheduler
{
    private static final Logger log = LogManager.getLogger(TestBatchScheduler.class);
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

//    @Autowired
    private TestBatchConfiguration testBatchConfig;

//    @Scheduled(cron = "0 * * * * *")
    public void runBatch() throws JobInstanceAlreadyCompleteException
                                , JobExecutionAlreadyRunningException
                                , JobParametersInvalidException
                                , JobRestartException
    {
        // Job 실행
        JobExecution jobExecution = jobLauncher.run(testBatchConfig.simpleJob()
                                                  , new JobParametersBuilder(jobExplorer)
                                                        .getNextJobParameters(testBatchConfig.simpleJob())  // Job 파라미터 자동 increment 세팅
                                                        .toJobParameters());

        // Job 실행정보 출력
        log.info("Job execution : {}", jobExecution.getStatus());
        log.info("Job Config name : {}", jobExecution.getJobConfigurationName());
    }
}
