package de.codecave.demo.component;

import java.util.Map;

public interface NewsCategoriesService {

    Map<String, Float> predictCategories(String newsLine);

}
