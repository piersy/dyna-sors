package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.Consumes;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;

import static com.piersyp.dynasors.example.client.AnnotationGenerator.CONSUMES_CLASS;
import static com.piersyp.dynasors.example.client.AnnotationGenerator.MULTI_CONSUMES_CLASS;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ContentTypeConfigurationFunctionUnitTest {

    private List<Annotation> consumesAnnotationList;
    private ContentTypeConfigurationFunction contentTypeConfigurationFunction;

    @Mock
    private BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction;
    @Mock
    private List<Annotation> fullAnnotationListMock;

    @Mock
    private WebResource.Builder webResourceBuilderMock1;
    @Mock
    private WebResource.Builder webResourceBuilderMock2;

    @Before
    public void setUp() throws Exception {
        contentTypeConfigurationFunction = new ContentTypeConfigurationFunction(annotationFilteringFunction);
    }

    @Test
    public void givenFilteredAnnotationListContainsConsumesInstanceSpecifyingOneType_whenApply_thenExpectedWebResourceReturned() throws Exception {
        consumesAnnotationList = new AnnotationGenerator().generateList(CONSUMES_CLASS, 1);
        when(annotationFilteringFunction.apply(fullAnnotationListMock, Consumes.class)).thenReturn(consumesAnnotationList);
        when(webResourceBuilderMock1.type(AnnotationGenerator.ConsumesClass.CONTENT_TYPE_VALUE)).thenReturn(webResourceBuilderMock2);

        assertThat(contentTypeConfigurationFunction.apply(fullAnnotationListMock, webResourceBuilderMock1), equalTo(webResourceBuilderMock2));
    }

    @Test
    public void givenFilteredAnnotationListContainsConsumesInstanceSpecifyingMoreThanOneType_whenApply_thenExceptionThrown() throws Exception {
        consumesAnnotationList = new AnnotationGenerator().generateList(MULTI_CONSUMES_CLASS, 1);
        when(annotationFilteringFunction.apply(fullAnnotationListMock, Consumes.class)).thenReturn(consumesAnnotationList);

        try {
            contentTypeConfigurationFunction.apply(fullAnnotationListMock, webResourceBuilderMock1);
            fail("Expecting exception to be thrown");
        }catch (RuntimeException e){
            //Good
        }
    }

}