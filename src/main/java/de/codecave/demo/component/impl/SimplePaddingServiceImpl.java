package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import de.codecave.demo.component.PaddingService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SimplePaddingServiceImpl implements PaddingService {

    private static final int PADDING = 20;

    @Override
    public int[] padding(int[] tokenizedText) {
        return Arrays.copyOf(tokenizedText, PADDING);
    }

}
