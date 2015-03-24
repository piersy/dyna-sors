package com.piersyp.dynasors.example.client.dynamic;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Cookie;
import java.util.function.BiFunction;

public class HeaderConfigurationFunctionFactory implements BiFunction<HeaderParam, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>>{

    @Override
    public BiFunction<Object[], WebResource.Builder, WebResource.Builder> apply(HeaderParam headerParam, Integer integer) {
        return new BiFunction<Object[], WebResource.Builder, WebResource.Builder>() {
            @Override
            public WebResource.Builder apply(Object[] objects, WebResource.Builder builder) {
                //TODO allow customisation of this to string approach
                builder.header(headerParam.value(), objects[integer].toString());
                return builder;
            }
        };
    }
}
