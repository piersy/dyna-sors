package com.piersyp.dynasors.example.client;

import java.lang.reflect.Method;
import java.util.function.Function;

public interface ClientFunctionFactory {
    Function<Object[], Object> createFunction(Class<?> resourceClass, Method resourceMethod);
}
