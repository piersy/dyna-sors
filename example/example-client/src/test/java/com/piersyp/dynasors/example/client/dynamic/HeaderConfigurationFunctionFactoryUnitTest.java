package com.piersyp.dynasors.example.client.dynamic;

import com.piersyp.dynasors.example.client.AnnotationGenerator;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.HeaderParam;
import java.util.function.BiFunction;

import static com.piersyp.dynasors.example.client.AnnotationGenerator.HEADER_PARAM_CLASS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HeaderConfigurationFunctionFactoryUnitTest {

    public static final String HEADER_VALUE = "headerValue";
    @Mock
    private WebResource.Builder webResourceBuilderMock;

    private Object[] params;
    private HeaderParam headerParam;
    private HeaderConfigurationFunctionFactory headerConfigurationFunctionFactory;

    @Before
    public void setUp() throws Exception {
        params = new Object[1];
        params[0] = HEADER_VALUE;
        headerParam = (HeaderParam) new AnnotationGenerator().getParameterAnnotationInstance(HEADER_PARAM_CLASS);
        when(webResourceBuilderMock.header(headerParam.value(), HEADER_VALUE)).thenReturn(webResourceBuilderMock);

        headerConfigurationFunctionFactory = new HeaderConfigurationFunctionFactory();
    }

    @Test
    public void whenCreateConfigurationFunction_thenFunctionBehavesAsExpected() throws Exception {
        BiFunction<Object[], WebResource.Builder, WebResource.Builder> cookieConfigurationFunction = headerConfigurationFunctionFactory.apply(headerParam, 0);

        assertThat(cookieConfigurationFunction.apply(params, webResourceBuilderMock), equalTo(webResourceBuilderMock));
    }


}