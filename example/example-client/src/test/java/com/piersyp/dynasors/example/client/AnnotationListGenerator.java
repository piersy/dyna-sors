package com.piersyp.dynasors.example.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AnnotationListGenerator {

    public static interface PathClass {
        public static String PATH_VALUE = "path";
        @Path(PATH_VALUE)
        void method();
    }

    public static interface GetClass {
        @GET
        void method();
    }

    public static interface ConsumesClass {
        public static String CONTENT_TYPE_VALUE = MediaType.APPLICATION_JSON;

        @Consumes(CONTENT_TYPE_VALUE)
        void method();
    }

    public static interface MultiConsumesClass {
        public static String FIRST_CONTENT_TYPE_VALUE = MediaType.APPLICATION_JSON;
        public static String SECOND_CONTENT_TYPE_VALUE = MediaType.APPLICATION_XML;

        @Consumes({FIRST_CONTENT_TYPE_VALUE, SECOND_CONTENT_TYPE_VALUE})
        void method();
    }

    public static interface ProducesClass {
        public static String CONTENT_TYPE_VALUE = MediaType.APPLICATION_JSON;

        @Produces(CONTENT_TYPE_VALUE)
        void method();
    }

    public static interface MultiProducesClass {
        public static String FIRST_CONTENT_TYPE_VALUE = MediaType.APPLICATION_JSON;
        public static String SECOND_CONTENT_TYPE_VALUE = MediaType.APPLICATION_XML;

        @Produces({FIRST_CONTENT_TYPE_VALUE, SECOND_CONTENT_TYPE_VALUE})
        void method();
    }

    public static final class AnnotatedClassReference{
        private final Class annotationHoldingClass;

        private AnnotatedClassReference(Class annotationHoldingClass) {
            this.annotationHoldingClass = annotationHoldingClass;
        }
    }

    //the AnnotatedClassReference stops just anything being shoved inside the generate method
    public static final AnnotatedClassReference PATH_CLASS = new AnnotatedClassReference(PathClass.class);
    public static final AnnotatedClassReference CONSUMES_CLASS = new AnnotatedClassReference(ConsumesClass.class);
    public static final AnnotatedClassReference MULTI_CONSUMES_CLASS = new AnnotatedClassReference(MultiConsumesClass.class);
    public static final AnnotatedClassReference PRODUCES_CLASS = new AnnotatedClassReference(ProducesClass.class);
    public static final AnnotatedClassReference MULTI_PRODUCES_CLASS = new AnnotatedClassReference(MultiProducesClass.class);
    public static final AnnotatedClassReference GET_CLASS = new AnnotatedClassReference(GetClass.class);

    public List<Annotation> generateList(AnnotatedClassReference annotatedClassReference, int size) {
        List<Annotation> result = new ArrayList<>();
        for (int i = 0; i <size ; i++) {
            try {
                result.add(annotatedClassReference.annotationHoldingClass.getDeclaredMethod("method").getAnnotations()[0]);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }



}
