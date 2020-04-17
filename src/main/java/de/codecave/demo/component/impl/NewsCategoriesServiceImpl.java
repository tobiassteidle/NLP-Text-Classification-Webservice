package de.codecave.demo.component.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Floats;
import de.codecave.demo.component.ModelMetadata;
import de.codecave.demo.component.NewsCategoriesService;
import de.codecave.demo.component.TensorflowService;
import de.codecave.demo.component.TextPreprocessor;
import one.util.streamex.EntryStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

@Service
public class NewsCategoriesServiceImpl implements NewsCategoriesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsCategoriesServiceImpl.class);

    @Autowired
    private TextPreprocessor textPreprocessor;

    @Value("classpath:/model/model_metadata.json")
    private Resource modelDataJsonFile;

    @Autowired
    private ObjectMapper objectMapper;

    private ModelMetadata modelData;

    @Autowired
    private TensorflowService tensorflowService;

    @PostConstruct
    public void loadAndConfigure() {
        modelData = loadModelData();
    }

    private ModelMetadata loadModelData() {

        try {
            return objectMapper.readValue(modelDataJsonFile.getInputStream(), ModelMetadata.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    @Override
    public Map<String, Float> predictCategories(final String newsLine) {

        final int[] inputTokens = textPreprocessor.pipeline(newsLine);

        final float[] result = tensorflowService.predictSingleTensorflow(inputTokens);

        return
                EntryStream.zip(modelData.getClasses(), Floats.asList(result))
                        .toImmutableMap();
    }

}
