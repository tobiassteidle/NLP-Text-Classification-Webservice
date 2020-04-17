package de.codecave.demo.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.codecave.demo.component.ModelMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("classpath:/model/model_metadata.json")
    private Resource modelDataJsonFile;

    @Bean
    public ModelMetadata modelMetadata(ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(modelDataJsonFile.getInputStream(), ModelMetadata.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
