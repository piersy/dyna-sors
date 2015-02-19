package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiFunction;

import static com.piersyp.dynasors.example.client.AnnotationListGenerator.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PathConfigurationFunctionUnitTest {

    private List<Annotation> pathAnnotationList;
    private PathConfigurationFunction pathConfigurationFunction;

    @Mock
    private BiFunction<List<Annotation>, Class<?>, List<Annotation>> annotationFilteringFunction;
    @Mock
    private List<Annotation> fullAnnotationListMock;

    @Mock
    private WebResource webResourceMock1;
    @Mock
    private WebResource webResourceMock2;
    @Mock
    private WebResource webResourceMock3;

    @Before
    public void setUp() throws Exception {
        pathAnnotationList = new AnnotationListGenerator().generateList(PATH_CLASS, 2);
        when(annotationFilteringFunction.apply(fullAnnotationListMock, Path.class)).thenReturn(pathAnnotationList);

        when(webResourceMock1.path(PathClass.PATH_VALUE)).thenReturn(webResourceMock2);
        when(webResourceMock2.path(PathClass.PATH_VALUE)).thenReturn(webResourceMock3);

        pathConfigurationFunction = new PathConfigurationFunction(annotationFilteringFunction);
    }

    @Test
    public void givenFilteredAnnotationListContains2PathInstances_whenApply_thenExpectedWebResourceReturned() throws Exception {
        assertThat(pathConfigurationFunction.apply(fullAnnotationListMock, webResourceMock1), equalTo(webResourceMock3));

    }


}