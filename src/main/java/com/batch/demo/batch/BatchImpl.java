package com.batch.demo.batch;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class BatchImpl implements Batch{

    private JobLauncher jobLauncher;

    private Job job;

    @Override
    public void execute() {
        try {
            final JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("data", new Date())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("Falha ao executar o Job: {}", e.getMessage());
        }
    }
}
