package com.piersyp.dynasors.example.client.dynamic;

import com.piersyp.dynasors.example.client.AnnotationFilteringFunction;
import com.piersyp.dynasors.example.client.AnnotationGenerator;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Cookie;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
/**
 * Because the cookie configuration function internally constructs lists from parameter arrays
 * of the parameter annotations input, we would need to exercise our internal knowledge of how the
 * cookieConfigurationFunction does that in order to construct the lists to use for mocking the
 * annotationFilteringFunction, it doesn't seem worth it so instead we go for an integration test.
 *
 * On the other hand this does point to maybe an error of design that that functionality should be
 * extracted out especially as it will be duplicated by the headerConfigurationFunction.
 */
public class HeaderConfigurationFunctionIntegrationTest {


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
        parameterAnnotations[0][0] = new AnnotationGenerator().getParameterAnnotationInstance(AnnotationGenerator.HEADER_PARAM_CLASS);
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