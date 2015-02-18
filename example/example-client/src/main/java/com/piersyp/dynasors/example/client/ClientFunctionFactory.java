package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

public class ClientFunctionFactory {

    private final Client client;
    private final Function<Class<?>, Function<Method, List<Annotation>>> annotationCollectionFunction;
    private final Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> contentTypeConfigurationFunction;
    private final Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> acceptsTypeConfigurationFunction;
    private final Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> cookieConfigurationFunction;
    private final Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> headerConfigurationFunction;
    private final Function<Method, Function<WebResource.Builder, Function<Object[], Object>>> httpMethodConfigurationFunction;

    public ClientFunctionFactory(Client client, Function<Class<?>, Function<Method, List<Annotation>>> annotationColletionFunction,
                                 Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> contentTypeConfigurationFunction,
                                 Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> acceptsTypeConfigurationFunction,
                                 Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> cookieConfigurationFunction,
                                 Function<Class<?>, Function<Method, Function<WebResource.Builder, WebResource.Builder>>> headerConfigurationFunction,
                                 Function<Method, Function<WebResource.Builder, Function<Object[], Object>>> httpMethodConfigurationFunction) {
        this.client = client;
        this.annotationCollectionFunction = annotationColletionFunction;
        this.contentTypeConfigurationFunction = contentTypeConfigurationFunction;
        this.acceptsTypeConfigurationFunction = acceptsTypeConfigurationFunction;
        this.cookieConfigurationFunction = cookieConfigurationFunction;
        this.headerConfigurationFunction = headerConfigurationFunction;
        this.httpMethodConfigurationFunction = httpMethodConfigurationFunction;
    }


    Function<Object[], Object> createFunction(Class<?> resourceClass, Method resourceMethod) {
        List<Annotation> annotations = annotationCollectionFunction.apply(resourceClass).apply(resourceMethod);
//
////        Client.create().resource("hfhf").type().header().accept().entity().post();
//        WebResource webResource = annotationCollectionFunction.apply(resourceClass).apply(resourceMethod).apply(client);
//        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_OCTET_STREAM);
//        builder = contentTypeConfigurationFunction.apply(resourceClass).apply(resourceMethod).apply(builder);
//        builder = acceptsTypeConfigurationFunction.apply(resourceClass).apply(resourceMethod).apply(builder);
//        builder = cookieConfigurationFunction.apply(resourceClass).apply(resourceMethod).apply(builder);
//        builder = headerConfigurationFunction.apply(resourceClass).apply(resourceMethod).apply(builder);
//        return httpMethodConfigurationFunction.apply(resourceMethod).apply(builder);
        return null;
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
