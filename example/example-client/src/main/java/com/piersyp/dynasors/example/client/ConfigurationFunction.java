package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConfigurationFunction implements BiFunction<List<Annotation>, WebResource, WebResource> {
    @Override
    public WebResource apply(List<Annotation> annotations, WebResource webResource) {
        return null;
    }

//    BiFunction<List<Annotation>, Class<?>, List<Annotation>>
//
//    @Override
//    public WebResource apply(List<Annotation> annotations, WebResource webResource) {
//        for(Annotation a : annotations){
//
//        }
//    }
}
