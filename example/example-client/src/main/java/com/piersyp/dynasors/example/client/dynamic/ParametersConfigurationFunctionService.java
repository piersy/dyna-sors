package com.piersyp.dynasors.example.client.dynamic;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

//Handles the structrual overhead of dealing with parameters without needing to handle the details
public class ParametersConfigurationFunctionService {

    //Unfortunately we need to handle both WebResource and webResourceBuilder here hence the Object, Object this is because you cannot set any path values on a web resource builder
    //More reason to decouple this and allow the provision of a set of function that actually construct the request for you
    private final Map<Class<? extends Annotation>, BiFunction<Annotation, Integer, BiFunction<Object[], Object, Object>>> parameterAnnotationConfigurationFunctionFactoryMap;

    public ParametersConfigurationFunctionService(
            Map<Class<? extends Annotation>, BiFunction<Annotation, Integer, BiFunction<Object[], Object, Object>>> parameterAnnotationConfigurationFunctionFactoryMap) {
        this.parameterAnnotationConfigurationFunctionFactoryMap = parameterAnnotationConfigurationFunctionFactoryMap;
    }

    public List<BiFunction<Object[], Object, Object>> createParameterConfigurationFunctions(Annotation[][] parameterAnnotations) {
        List<BiFunction<Object[], Object, Object>> parameterConfigurationFunctions = new ArrayList<>();
        for (int parameterPosition = 0; parameterPosition < parameterAnnotations.length; parameterPosition++) {
            for (int i = 0; i < parameterAnnotations[parameterPosition].length; i++) {
                Annotation annotation = parameterAnnotations[parameterPosition][i];
                BiFunction<Annotation, Integer, BiFunction<Object[], Object, Object>> parameterConfigurationFunctionFactory = parameterAnnotationConfigurationFunctionFactoryMap.get(
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
