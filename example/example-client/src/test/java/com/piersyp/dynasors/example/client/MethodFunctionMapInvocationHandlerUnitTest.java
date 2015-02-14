package com.piersyp.dynasors.example.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.Object;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MethodFunctionMapInvocationHandlerUnitTest {

    private Map<Method, Function<Object[], Object>> methodFunctionMap;
    private Object[] inputArgs;

    @Mock
    private Function<Object[], Object> function1Mock;
    @Mock
    private Function<Object[], Object> function2Mock;
    @Mock
    private Function<Object[], Object> function3Mock;
    @Mock
    private MethodFunctionMapInvocationHandler methodFunctionMapInvocationHandler;
    @Mock
    private Object result1Mock;
    @Mock
    private Object result2Mock;
    @Mock
    private Object result3Mock;
    private Method method1;
    private Method method2;
    private Method method3;

    private static class Methods{
        public Object method1(Object[] inputs){
            return null;
        }

        public Object method2(Object[] inputs){
            return null;
        }

        public Object method3(Object[] inputs){
            return null;
        }
    }

    @Before
    public void setUp() throws Exception {
        method1 = Methods.class.getMethod("method1", Object[].class);
        method2 = Methods.class.getMethod("method2", Object[].class);
        method3 = Methods.class.getMethod("method3", Object[].class);

        inputArgs = new Object[3];

        when(function1Mock.apply(inputArgs)).thenReturn(result1Mock);
        when(function2Mock.apply(inputArgs)).thenReturn(result2Mock);
        when(function3Mock.apply(inputArgs)).thenReturn(result3Mock);

        methodFunctionMap = new HashMap<>();
        methodFunctionMap.put(method1, function1Mock);
        methodFunctionMap.put(method2, function2Mock);
        methodFunctionMap.put(method3, function3Mock);

        methodFunctionMapInvocationHandler = new MethodFunctionMapInvocationHandler(methodFunctionMap);

    }

    @Test
    public void whenInvokeCalledForMethod_thenExpectedValueReturned() throws Throwable {
        assertThat(methodFunctionMapInvocationHandler.invoke(null, method1, inputArgs), equalTo(result1Mock));
        assertThat(methodFunctionMapInvocationHandler.invoke(null, method2, inputArgs), equalTo(result2Mock));
        assertThat(methodFunctionMapInvocationHandler.invoke(null, method3, inputArgs), equalTo(result3Mock));
    }

}