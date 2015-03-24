package com.piersyp.dynasors.example.client.dynamic;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Cookie;
import java.util.function.BiFunction;

public class CookieConfigurationFunctionFactory implements BiFunction<CookieParam, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>>{

    @Override
    public BiFunction<Object[], WebResource.Builder, WebResource.Builder> apply(CookieParam cookieParam, Integer integer) {
        return new BiFunction<Object[], WebResource.Builder, WebResource.Builder>() {
            @Override
            public WebResource.Builder apply(Object[] objects, WebResource.Builder builder) {
                //TODO allow customisation of this to string approach
                builder.cookie(new Cookie(cookieParam.value(), objects[integer].toString()));
                return builder;
            }
        };
    }
}
