package com.piersyp.dynasors.example.client.dynamic;

import com.piersyp.dynasors.example.client.AnnotationGenerator;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import java.lang.Object;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ParametersConfigurationFunctionServiceUnitTest {

    public static final int COOKIE_PARAM_POSITION = 0;
    public static final int HEADER_PARAM_POSITION = 1;
    @Mock
    private BiFunction<Annotation, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>> cookieConfigurationFunctionFactoryMock;
    @Mock
    private BiFunction<Annotation, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>> headerConfigurationFunctionFactoryMock;
    @Mock
    private BiFunction<Object[], WebResource.Builder, WebResource.Builder> cookieConfigurationFunctionMock;
    @Mock
    private BiFunction<Object[], WebResource.Builder, WebResource.Builder> headerConfigurationFunctionMock;

    private Map<Class<? extends Annotation>, BiFunction<Annotation, Integer, BiFunction<Object[], WebResource.Builder, WebResource.Builder>>> parameterAnnotationClassToConfigurationFunctionHashMap;

    private Annotation[][] parameterAnnotations;

    private ParametersConfigurationFunctionService parametersConfigurationFunctionService;
    private Annotation cookieParamAnnotation;
    private Annotation headerParamAnnotation;

    @Before
    public void setUp() throws Exception {
        AnnotationGenerator annotationGenerator = new AnnotationGenerator();
        cookieParamAnnotation = annotationGenerator.getParameterAnnotationInstance(AnnotationGenerator.COOKIE_PARAM_CLASS);
        headerParamAnnotation = annotationGenerator.getParameterAnnotationInstance(AnnotationGenerator.HEADER_PARAM_CLASS);

        when(cookieConfigurationFunctionFactoryMock.apply(cookieParamAnnotation, COOKIE_PARAM_POSITION)).thenReturn(cookieConfigurationFunctionMock);
        when(headerConfigurationFunctionFactoryMock.apply(headerParamAnnotation, HEADER_PARAM_POSITION)).thenReturn(headerConfigurationFunctionMock);

        parameterAnnotationClassToConfigurationFunctionHashMap = new HashMap<>();
        parameterAnnotationClassToConfigurationFunctionHashMap.put(HeaderParam.class, headerConfigurationFunctionFactoryMock);
        parameterAnnotationClassToConfigurationFunctionHashMap.put(CookieParam.class, cookieConfigurationFunctionFactoryMock);

        parameterAnnotations = new Annotation[2][];
        parameterAnnotations[COOKIE_PARAM_POSITION] = new Annotation[1];
        parameterAnnotations[HEADER_PARAM_POSITION] = new Annotation[1];
        parameterAnnotations[COOKIE_PARAM_POSITION][0] = cookieParamAnnotation;
        parameterAnnotations[HEADER_PARAM_POSITION][0] = headerParamAnnotation;

        parametersConfigurationFunctionService = new ParametersConfigurationFunctionService(parameterAnnotationClassToConfigurationFunctionHashMap);

    }

    @Test
    public void givenParametersWithKnownAnnotations_whenCreateParameterConfigurationFunctions_thenExpectedConfigurationFunctionsReturned() throws Exception {
        assertThat(parametersConfigurationFunctionService.createParameterConfigurationFunctions(parameterAnnotations), org.hamcrest.Matchers.contains(cookieConfigurationFunctionMock, headerConfigurationFunctionMock));
    }


}