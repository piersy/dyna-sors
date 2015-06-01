package com.piersyp.dynasors.example.client.dynamic;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import java.util.function.BiFunction;

public class PathParamConfigurationFunctionFactory implements BiFunction<PathParam, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>>{

    @Override
    public BiFunction<Object[], WebResource.Builder, WebResource.Builder> apply(PathParam headerParam, Integer integer) {
        return new BiFunction<Object[], WebResource.Builder, WebResource.Builder>() {
            @Override
            public WebResource.Builder apply(Object[] objects, WebResource.Builder builder) {
                //TODO allow customisation of this to string approach
                builder.  /header(headerParam.value(), objects[integer].toString());
                return builder;
            }
        };
    }
}
