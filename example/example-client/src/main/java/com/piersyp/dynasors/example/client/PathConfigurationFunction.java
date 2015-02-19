package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;

public class PathConfigurationFunction implements BiFunction<List<Annotation>, WebResource, WebResource> {

    private final BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction;

    public PathConfigurationFunction(BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction) {
        this.annotationFilteringFunction = annotationFilteringFunction;
    }

    @Override
    public WebResource apply(List<Annotation> annotations, WebResource webResource) {
        for(Annotation a : annotationFilteringFunction.apply(annotations, Path.class)){
            webResource = webResource.path(Path.class.cast(a).value());
        }
        return webResource;
    }

}
