package com.piersyp.dynasors.example.client.dynamic;

import com.piersyp.dynasors.example.client.AnnotationGenerator;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Cookie;

import java.lang.annotation.Annotation;
import java.util.function.BiFunction;

import static com.piersyp.dynasors.example.client.AnnotationGenerator.*;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CookieConfigurationFunctionFactoryUnitTest{

    public static final String COOKIE_VALUE = "cookieValue";
    @Mock
    private WebResource.Builder webResourceBuilderMock;
    @Captor
    private ArgumentCaptor<Cookie> cookieCaptor;

    private Object[] params;
    private CookieParam cookieParam;
    private CookieConfigurationFunctionFactory cookieConfigurationFunctionFactory;

    @Before
    public void setUp() throws Exception {
        when(webResourceBuilderMock.cookie(org.mockito.Matchers.any(Cookie.class))).thenReturn(webResourceBuilderMock);
        params = new Object[1];
        params[0] = COOKIE_VALUE;
        cookieParam = (CookieParam) new AnnotationGenerator().getParameterAnnotationInstance(COOKIE_PARAM_CLASS);

        cookieConfigurationFunctionFactory = new CookieConfigurationFunctionFactory();
    }

    @Test
    public void whenCreateConfigurationFunction_thenFunctionBehavesAsExpected() throws Exception {
        BiFunction<Object[], WebResource.Builder, WebResource.Builder> cookieConfigurationFunction = cookieConfigurationFunctionFactory.apply(cookieParam, 0);

        cookieConfigurationFunction.apply(params, webResourceBuilderMock);

        verify(webResourceBuilderMock).cookie(cookieCaptor.capture());
        verifyNoMoreInteractions(webResourceBuilderMock);
        assertThat(cookieCaptor.getValue(), equalTo(new Cookie(cookieParam.value(), COOKIE_VALUE)));
    }


}