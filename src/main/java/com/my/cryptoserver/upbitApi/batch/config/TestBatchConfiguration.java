package com.my.cryptoserver.upbitApi.batch.config;

import com.my.cryptoserver.batch.util.UniqueRunIdIncrementer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableBatchProcessing      // 배치 기능 활성화
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
//@Configuration
public class TestBatchConfiguration
{
    private static final Logger log = LogManager.getLogger(TestBatchConfiguration.class);
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음

//    @Bean
    public Job simpleJob() {
        Job job =  jobBuilderFactory.get("simpleJob")
                .incrementer(new RunIdIncrementer())
                .start(simpleStep1())
                .build();
        return job;
    }

//    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is simpleStep");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
