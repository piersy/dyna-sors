package com.piersyp.dynasors.example.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AnnotationCollectionFunction implements BiFunction<Class<?>, Method, List<Annotation>> {
    @Override
    public List<Annotation> apply(Class<?> aClass, Method method) {
        ArrayList<Annotation> list = new ArrayList<>();
        list.addAll(Arrays.asList(aClass.getAnnotations()));
        list.addAll(Arrays.asList(method.getAnnotations()));
        for (Annotation[] parameterAnnotations : method.getParameterAnnotations()) {
            list.addAll(Arrays.asList(parameterAnnotations));
        }
        return list;
    }

}
