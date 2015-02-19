package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;

public class AcceptsTypeConfigurationFunction implements BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder> {

    private final BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction;

    public AcceptsTypeConfigurationFunction(BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction) {

        this.annotationFilteringFunction = annotationFilteringFunction;
    }

    @Override
    public WebResource.Builder apply(List<Annotation> annotations, WebResource.Builder builder) {
        //there can only be one produces annotation per method that is enforced by the compiler
        Annotation annotation = annotationFilteringFunction.apply(annotations, Produces.class).get(0);
        String[] contentTypes = Produces.class.cast(annotation).value();
        for (String contentType : contentTypes) {
            builder = builder.accept(contentType);
        }
        return builder;
    }
}
