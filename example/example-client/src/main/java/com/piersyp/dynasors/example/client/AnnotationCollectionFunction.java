package com.piersyp.dynasors.example.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class AnnotationCollectionFunction implements Function<Class<?>, Function<Method, List<Annotation>>> {

    @Override
    public Function<Method, List<Annotation>> apply(Class<?> aClass) {
        return method -> {
            ArrayList<Annotation> list = new ArrayList<>();
            list.addAll(Arrays.asList(aClass.getAnnotations()));
            list.addAll(Arrays.asList(method.getAnnotations()));
            for (Annotation[] parameterAnnotations : method.getParameterAnnotations()) {
                list.addAll(Arrays.asList(parameterAnnotations));
            }
            return list;
        };
    }
}
