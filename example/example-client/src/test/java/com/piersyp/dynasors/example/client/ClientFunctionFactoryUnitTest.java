package com.piersyp.dynasors.example.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientFunctionFactoryUnitTest {

    private Method method;
    private ClientFunctionFactory clientFunctionFactory;

    @Mock
    private Function<Object[], Object> expectedFunctionMock;

    private static class TestResource{
        public Object method(Object[] objects){
            return objects;
        }

    }

    @Before
    public void setUp() throws Exception {
        method = TestResource.class.getMethod("method", Object[].class);
//        clientFunctionFactory = new ClientFunctionFactory(client, pathConfigurationFunction, contentTypeConfigurationFunction, acceptsTypeConfigurationFunction,
//                cookieConfigurationFunction, headerConfigurationFunction, httpMethodConfigurationFunction);
    }

    @Test
    public void whenCreateFunction_thenExpectedFunctionReturned() throws Exception {
        assertThat(clientFunctionFactory.createFunction(TestResource.class, method), equalTo(expectedFunctionMock));
    }
    

}