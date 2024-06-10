package com.batch.demo.batch.step;

import com.batch.demo.model.PersonModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessorCustom implements ItemProcessor<PersonModel, PersonModel> {
    @Override
    public PersonModel process(final PersonModel item) {
        item.setName("test");
        return item;
    }
}
