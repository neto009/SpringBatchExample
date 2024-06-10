package com.batch.demo.batch.config;

import com.batch.demo.batch.step.ItemProcessorCustom;
import com.batch.demo.batch.step.ItemReaderCustom;
import com.batch.demo.batch.step.ItemWriterCustom;
import com.batch.demo.model.PersonModel;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class BatchConfig {

    private JobRepository jobRepository;

    @Bean
    public Step step(final PlatformTransactionManager transactionManager, @Qualifier("taskExecutor") final SimpleAsyncTaskExecutor taskExecutor,
                     final ItemReaderCustom itemReaderCustom, final ItemProcessorCustom itemProcessorCustom,
                     final ItemWriterCustom itemWriterCustom) {
        return new StepBuilder("step", jobRepository)
                .<PersonModel, PersonModel>chunk(10, transactionManager)
                .reader(itemReaderCustom)
                .processor(itemProcessorCustom)
                .writer(itemWriterCustom)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Job job(Step step) {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
}