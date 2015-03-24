package com.piersyp.dynasors.example.client.dynamic;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.CookieParam;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

//Handles the structrual overhead of dealing with parameters without needing to handle the details
public class ParametersConfigurationFunctionService {

    private final Map<Class<? extends Annotation>, BiFunction<Annotation, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>>> parameterAnnotationConfigurationFunctionFactoryMap;

    public ParametersConfigurationFunctionService(
            Map<Class<? extends Annotation>, BiFunction<Annotation, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>>> parameterAnnotationConfigurationFunctionFactoryMap) {
        this.parameterAnnotationConfigurationFunctionFactoryMap = parameterAnnotationConfigurationFunctionFactoryMap;
    }

    public List<BiFunction<Object[], WebResource.Builder, WebResource.Builder>> createParameterConfigurationFunctions(Annotation[][] parameterAnnotations) {
        List<BiFunction<Object[], WebResource.Builder, WebResource.Builder>> parameterConfigurationFunctions = new ArrayList<>();
        for (int parameterPosition = 0; parameterPosition < parameterAnnotations.length; parameterPosition++) {
            for (int i = 0; i < parameterAnnotations[parameterPosition].length; i++) {
                Annotation annotation = parameterAnnotations[parameterPosition][i];
                BiFunction<Annotation, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>> parameterConfigurationFunctionFactory = parameterAnnotationConfigurationFunctionFactoryMap.get(
                        annotation.annotationType());
                //We check to see if this is null since there could be non jax-rs annotations that we are not interested in
                if (parameterConfigurationFunctionFactory != null) {
                    parameterConfigurationFunctions.add(parameterConfigurationFunctionFactory.apply(annotation, parameterPosition));
                }
            }
        }
        return parameterConfigurationFunctions;
    }

}
