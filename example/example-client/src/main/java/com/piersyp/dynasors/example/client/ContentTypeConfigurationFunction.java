package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.Consumes;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;

public class ContentTypeConfigurationFunction implements BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder>{

    private final BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction;

    public ContentTypeConfigurationFunction(BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction) {
        this.annotationFilteringFunction = annotationFilteringFunction;
    }

    @Override
    public WebResource.Builder apply(List<Annotation> annotations, WebResource.Builder builder) {
        //there can only be one consumes annotation that is enforced by the compiler
        Annotation annotation = annotationFilteringFunction.apply(annotations, Consumes.class).get(0);
        String[] contentTypes = Consumes.class.cast(annotation).value();
        if(contentTypes.length > 1){
            throw new RuntimeException("More than one content type option found, please specify a preference. Types:"+contentTypes);
        }
        return builder.type(contentTypes[0]);
    }
}
