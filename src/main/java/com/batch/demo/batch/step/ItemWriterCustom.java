package com.batch.demo.batch.step;

import com.batch.demo.model.PersonModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ItemWriterCustom implements ItemWriter<PersonModel> {

    private JdbcTemplate jdbcTemplate;


    @Override
    public void write(Chunk<? extends PersonModel> chunk) {
        log.info("Tamanho do chunk: {}", chunk.size());
        chunk.getItems().forEach(person -> {
            final String sql = "INSERT INTO PersonModel (name, age) VALUES (?, ?)";
            jdbcTemplate.update(sql, person.getName(), person.getAge());
        });
    }
}
