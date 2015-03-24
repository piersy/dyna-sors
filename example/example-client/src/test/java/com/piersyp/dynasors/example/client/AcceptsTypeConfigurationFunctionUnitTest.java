package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.Produces;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;

import static com.piersyp.dynasors.example.client.AnnotationGenerator.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AcceptsTypeConfigurationFunctionUnitTest {

    private List<Annotation> producesAnnotationList;
    private AcceptsTypeConfigurationFunction acceptsTypeConfigurationFunction;

    @Mock
    private BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction;
    @Mock
    private List<Annotation> fullAnnotationListMock;

    @Mock
    private WebResource.Builder webResourceBuilderMock1;
    @Mock
    private WebResource.Builder webResourceBuilderMock2;
    @Mock
    private WebResource.Builder webResourceBuilderMock3;

    @Before
    public void setUp() throws Exception {

        acceptsTypeConfigurationFunction = new AcceptsTypeConfigurationFunction(annotationFilteringFunction);
    }

    @Test
    public void givenFilteredAnnotationListContainsConsumesInstanceSpecifyingOneType_whenApply_thenExpectedWebResourceReturned() throws Exception {
        producesAnnotationList = new AnnotationGenerator().generateList(PRODUCES_CLASS, 1);
        when(annotationFilteringFunction.apply(fullAnnotationListMock, Produces.class)).thenReturn(producesAnnotationList);
        when(webResourceBuilderMock1.accept(ProducesClass.CONTENT_TYPE_VALUE)).thenReturn(webResourceBuilderMock2);

        assertThat(acceptsTypeConfigurationFunction.apply(fullAnnotationListMock, webResourceBuilderMock1), equalTo(webResourceBuilderMock2));
    }

    @Test
    public void givenFilteredAnnotationListContainsConsumesInstanceSpecifyingMoreThanOneType_whenApply_thenExpectedWebResourceReturned() throws Exception {
        producesAnnotationList = new AnnotationGenerator().generateList(MULTI_PRODUCES_CLASS, 1);
        when(annotationFilteringFunction.apply(fullAnnotationListMock, Produces.class)).thenReturn(producesAnnotationList);
        when(webResourceBuilderMock1.accept(MultiProducesClass.FIRST_CONTENT_TYPE_VALUE)).thenReturn(webResourceBuilderMock2);
        when(webResourceBuilderMock2.accept(MultiProducesClass.SECOND_CONTENT_TYPE_VALUE)).thenReturn(webResourceBuilderMock3);

        assertThat(acceptsTypeConfigurationFunction.apply(fullAnnotationListMock, webResourceBuilderMock1), equalTo(webResourceBuilderMock3));
    }

}