package com.piersyp.dynasors.example.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationFilteringFunctionUnitTest {

    private List<Annotation> annotations;
    private AnnotationFilteringFunction annotationFilteringFunction;

    private static interface TestClass{
        @Path("a")
        void a();
        @Path("a")
        void b();
        @Deprecated
        void c();
    }

    @Before
    public void setUp() throws Exception {
        annotations = new ArrayList<>();
        for (Method m : TestClass.class.getMethods()){
            annotations.addAll(Arrays.asList(m.getDeclaredAnnotations()));
        }
        assertThat(annotations, hasSize(3));

        annotationFilteringFunction = new AnnotationFilteringFunction();
    }


    @Test
    public void givenListWithMultipleTypesOfAnnotation_whenApply_thenExpectedResultReturned() throws Exception {
        List<Annotation> pathAnnotations = annotationFilteringFunction.apply(annotations, Path.class);
        List<Annotation> deprecatedAnnotation = annotationFilteringFunction.apply(annotations, Deprecated.class);

        assertThat(pathAnnotations, hasSize(2));
        assertThat(pathAnnotations.get(0).getClass(), typeCompatibleWith(Path.class));
        assertThat(pathAnnotations.get(1).getClass(), typeCompatibleWith(Path.class));

        assertThat(deprecatedAnnotation, hasSize(1));
        assertThat(deprecatedAnnotation.get(0).getClass(), typeCompatibleWith(Deprecated.class));
    }
    
    
}