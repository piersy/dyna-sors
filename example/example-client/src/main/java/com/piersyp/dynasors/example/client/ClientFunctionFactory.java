package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ClientFunctionFactory {

    private final WebResource resource;
    private final BiFunction<Class<?>, Method, List<Annotation>> annotationCollectionFunction;
    private final BiFunction<List<Annotation>, WebResource, WebResource> pathConfigurationFunction;
    private final BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder> contentTypeConfigurationFunction;
    private final BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder> acceptsTypeConfigurationFunction;
    private final Function<Annotation[][], BiFunction<Object[], WebResource.Builder, WebResource.Builder>> cookieConfigurationFunction;
    private final BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder> headerConfigurationFunction;
    private final BiFunction<List<Annotation>, WebResource.Builder, Function<Object[], Object>> httpMethodConfigurationFunction;

    public ClientFunctionFactory(WebResource resource, BiFunction<Class<?>,Method, List<Annotation>> annotationCollectionFunction,
                                 BiFunction<List<Annotation>, WebResource, WebResource> pathConfigurationFunction,
                                 BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder> contentTypeConfigurationFunction,
                                 BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder> acceptsTypeConfigurationFunction,
                                 Function<Annotation[][], BiFunction<Object[], WebResource.Builder, WebResource.Builder>> cookieConfigurationFunction,
                                 BiFunction<List<Annotation>, WebResource.Builder, WebResource.Builder> headerConfigurationFunction,
                                 BiFunction<List<Annotation>, WebResource.Builder, Function<Object[], Object>> httpMethodConfigurationFunction) {
        this.resource = resource;
        this.annotationCollectionFunction = annotationCollectionFunction;
        this.pathConfigurationFunction = pathConfigurationFunction;
        this.contentTypeConfigurationFunction = contentTypeConfigurationFunction;
        this.acceptsTypeConfigurationFunction = acceptsTypeConfigurationFunction;
        //so the cookie and header configuration function will need access to the actual objects and the builder and also the
        this.cookieConfigurationFunction = cookieConfigurationFunction;
        this.headerConfigurationFunction = headerConfigurationFunction;
        this.httpMethodConfigurationFunction = httpMethodConfigurationFunction;
    }


    Function<Object[], Object> createFunction(Class<?> resourceClass, Method resourceMethod) {
        List<Annotation> annotations = annotationCollectionFunction.apply(resourceClass, resourceMethod);
        WebResource webResource = pathConfigurationFunction.apply(annotations, resource);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
        builder = contentTypeConfigurationFunction.apply(annotations, builder);
        builder = acceptsTypeConfigurationFunction.apply(annotations, builder);
        BiFunction<Object[], WebResource.Builder, WebResource.Builder> cookieSettingFunction = cookieConfigurationFunction.apply(resourceMethod.getParameterAnnotations());
        builder = headerConfigurationFunction.apply(annotations, builder);
        return httpMethodConfigurationFunction.apply(annotations, builder);
    }

    //reference
//    public <A, B, C> Function<A, Function<B, C>> curry(final BiFunction<A, B, C> f) {
//        return (A a) -> (B b) -> f.apply(a, b);
//    }
//
//    public <A, B, C> BiFunction<A, B, C> uncurry(Function<A, Function<B, C>> f) {
//        return (A a, B b) -> f.apply(a).apply(b);
//    }
}
