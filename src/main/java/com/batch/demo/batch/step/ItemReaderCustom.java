package com.batch.demo.batch.step;

import com.batch.demo.model.PersonModel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Component
public class ItemReaderCustom implements ItemReader<PersonModel> {

    private BufferedReader reader;

    @Override
    public PersonModel read() throws IOException {
        String line = reader.readLine();
        if (line != null) {
            return this.convertLineInPerson(line);
        }
        return null;
    }

    private PersonModel convertLineInPerson(final String line) {
        String[] parts = line.split(";");

        final Long id = Long.valueOf(parts[0]);
        final String name = parts[1];
        final int age = Integer.valueOf(parts[2]);

        return new PersonModel(id, name, age);
    }

    @PostConstruct
    public void init() {
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/registros.txt"));
        } catch (FileNotFoundException e) {
            log.error("Falha na leitura do arquivo: {}", e.getMessage());
        }
    }

    @PreDestroy
    public void cleanup() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("Falha ao fechar o arquivo: {}", e.getMessage());
            }
        }
    }
}
