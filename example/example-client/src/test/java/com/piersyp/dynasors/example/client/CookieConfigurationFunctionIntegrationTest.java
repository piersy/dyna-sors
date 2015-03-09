package com.piersyp.dynasors.example.client;

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
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CookieConfigurationFunctionIntegrationTest {


    public static final String PARAMETER = "parameter";
    @Mock
    private BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunctionMock;
    @Mock
    private List<Annotation> annotationListMock;
    private CookieConfigurationFunction cookieConfigurationFunction;
    private Annotation[][] parameterAnnotations;
    private Object[] parameters;
    @Mock
    private WebResource.Builder webResourceBuilderMock;
    @Captor
    ArgumentCaptor<Cookie> cookieArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        parameterAnnotations = new Annotation[1][1];
        parameterAnnotations[0][0] = new AnnotationListGenerator().getParameterAnnotationInstance(AnnotationListGenerator.COOKIE_PARAM_CLASS);
//        List<Annotation> cookieParamList = new AnnotationListGenerator().getParameterAnnotationInstance(AnnotationListGenerator.COOKIE_PARAM_CLASS);
//        when(annotationFilteringFunctionMock.apply(annotationListMock, CookieParam.class)).thenReturn(cookieParamList);
        parameters = new Object[1];
        parameters[0] = PARAMETER;
        cookieConfigurationFunction = new CookieConfigurationFunction(new AnnotationFilteringFunction());
    }

    @Test
    public void givenResourceWithCookieParameters_whenApply_thenExpectedConfigurationFunctionReturned() throws Exception {
        BiFunction<Object[], WebResource.Builder, WebResource.Builder> cookieSettingFunction = cookieConfigurationFunction.apply(parameterAnnotations);

        cookieSettingFunction.apply(parameters, webResourceBuilderMock);

        verify(webResourceBuilderMock).cookie(cookieArgumentCaptor.capture());
        assertThat(cookieArgumentCaptor.getValue().getValue(),equalTo(PARAMETER));
    }


}