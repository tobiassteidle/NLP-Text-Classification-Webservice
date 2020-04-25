package de.codecave.demo.component.impl;

import com.google.common.primitives.Floats;
import de.codecave.demo.component.ModelMetadata;
import de.codecave.demo.component.NewsCategoriesService;
import de.codecave.demo.component.TensorflowService;
import de.codecave.demo.component.TextPreprocessor;
import one.util.streamex.EntryStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NewsCategoriesServiceImpl implements NewsCategoriesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsCategoriesServiceImpl.class);

    @Autowired
    private TextPreprocessor textPreprocessor;

    @Autowired
    private ModelMetadata modelMetadata;

    @Autowired
    private TensorflowService tensorflowService;

    @Override
    public Map<String, Float> predictCategories(final String newsLine) {

        LOGGER.info("Run News Classification for: {}", newsLine);

        final int[] inputTokens = textPreprocessor.pipeline(newsLine);

        final float[] result = tensorflowService.predictSingleTensorflow(
                inputTokens, modelMetadata.getClasses().size());

        return
                EntryStream.zip(modelMetadata.getClasses(), Floats.asList(result))
                        .toImmutableMap();
    }

}
