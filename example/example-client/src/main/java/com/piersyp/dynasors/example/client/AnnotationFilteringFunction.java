package com.piersyp.dynasors.example.client;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class AnnotationFilteringFunction implements BiFunction<List<Annotation>, Class<?>, List<Annotation>> {
    @Override
    public List<Annotation> apply(List<Annotation> annotations, Class<?> aClass) {
        return annotations.stream().filter(e -> aClass.isAssignableFrom(e.getClass())).collect(Collectors.toList());
    }
}
