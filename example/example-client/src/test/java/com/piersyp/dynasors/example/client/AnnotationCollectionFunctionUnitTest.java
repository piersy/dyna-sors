package com.piersyp.dynasors.example.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationCollectionFunctionUnitTest {

    public static final String ONE = "1";
    private AnnotationCollectionFunction annotationCollectionFunction;

    @Before
    public void setUp() throws Exception {
        annotationCollectionFunction = new AnnotationCollectionFunction();

    }

    @Path(ONE)
    private static interface Test1{
        public void method1();
    }

    @Test
    public void givenClassWithPathAnnotation_whenApply_thenAnnotationFound() throws Exception {
        List<Annotation> annotations = annotationCollectionFunction.apply(Test1.class).apply(Test1.class.getDeclaredMethod("method1"));

        assertThat(annotations, contains(instanceOf(Path.class)));
    }
    
    
    @Path(ONE)
    private static interface Test2{
        @Path(ONE)
        public void method1();
    }

    @Test
    public void givenClassWithTwoPathAnnotations_whenApply_thenAnnotationsFound() throws Exception {
        List<Annotation> annotations = annotationCollectionFunction.apply(Test2.class).apply(Test2.class.getDeclaredMethod("method1"));

        assertThat(annotations, contains(instanceOf(Path.class), instanceOf(Path.class)));
    }

    @Path(ONE)
    private static interface Test3{
        @Path(ONE)
        @GET
        public void method1();
    }

    @Test
    public void givenClassWithTwoPathAnnotationsAndAMethodAnnotation_whenApply_thenAnnotationsFound() throws Exception {
        List<Annotation> annotations = annotationCollectionFunction.apply(Test3.class).apply(Test3.class.getDeclaredMethod("method1"));

        assertThat(annotations, contains(instanceOf(Path.class), instanceOf(Path.class), instanceOf(GET.class)));
    }


    @Path(ONE)
    private static interface Test4{
        @Path(ONE)
        @GET
        public void method1(@HeaderParam("blah") String stringy);
    }

    @Test
    public void givenClassWithTwoPathAnnotationsAMethodAnnotationAndAParameterAnnotation_whenApply_thenAnnotationsFound() throws Exception {
        List<Annotation> annotations = annotationCollectionFunction.apply(Test4.class).apply(Test4.class.getDeclaredMethod("method1", String.class));

        assertThat(annotations, contains(instanceOf(Path.class), instanceOf(Path.class), instanceOf(GET.class), instanceOf(HeaderParam.class)));
    }


}