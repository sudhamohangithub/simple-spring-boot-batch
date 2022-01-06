package com.example.batchprocessing.commonConfig;

import com.example.batchprocessing.entity.Person;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class Reader {

    @Autowired
    DataSource dataSource;


    @Bean
    @Qualifier("personFileReader")
    public FlatFileItemReader<Person> personFileReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean
    @Qualifier("personDbReader")
    public ItemReader<Person> personDbReader() {
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("valuationReader")
                .dataSource(dataSource)
                .sql("Select * from Person")
                .rowMapper(new BeanPropertyRowMapper<>(Person.class))
                .build();
    }
}
