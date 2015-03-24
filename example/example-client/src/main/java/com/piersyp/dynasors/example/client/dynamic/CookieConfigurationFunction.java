package com.piersyp.dynasors.example.client.dynamic;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Cookie;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CookieConfigurationFunction implements Function<Annotation[][], BiFunction<Object[], WebResource.Builder, WebResource.Builder>> {
    private final BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction;

    //We could add here a list of transformers in the order that cookies are defined on the method that could be used to
    //transform cookies to string values
    public CookieConfigurationFunction(BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction) {
        this.annotationFilteringFunction = annotationFilteringFunction;
    }

    @Override
    public BiFunction<Object[], WebResource.Builder, WebResource.Builder> apply(Annotation[][] parameterAnnotations) {
            List<NamedParameter> namedParameters = new ArrayList<>();
        for (int i = 0; i < parameterAnnotations.length ; i++) {
            List<Annotation> cookieAnnotations = annotationFilteringFunction.apply(Arrays.asList(parameterAnnotations[i]), CookieParam.class);
            if(!cookieAnnotations.isEmpty()){
                CookieParam cookieAnnotation = CookieParam.class.cast(cookieAnnotations.get(0));
                namedParameters.add(new NamedParameter(cookieAnnotation.value(), i));
            }
        }
        return new BiFunction<Object[], WebResource.Builder, WebResource.Builder>() {
            @Override
            public WebResource.Builder apply(Object[] objects, WebResource.Builder builder) {
                for (NamedParameter namedParameter : namedParameters) {
                    /*
                        TODO decide how to decode cookie values to string, could make them implement an interface,
                        or provide a function at  client construction time to decode the cookie, or toString?
                     */
                    builder = builder.cookie(new Cookie(namedParameter.getName(), objects[namedParameter.getCookiePosition()].toString()));
                }
                return builder;
            }
        };
    }
}
