package com.piersyp.dynasors.example.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

public class MethodFunctionMapInvocationHandler implements InvocationHandler {

    private final Map<Method, Function<Object[], Object>> methodFunctionMap;

    public MethodFunctionMapInvocationHandler(Map<Method, Function<Object[], Object>> methodFunctionMap) {

        this.methodFunctionMap = methodFunctionMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return methodFunctionMap.get(method).apply(args);
    }
}
