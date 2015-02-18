package com.piersyp.dynasors.example.client;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;

public class AnnotationInheritanceAssumptionTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface SimpleAnnotation {
        String value() default "test";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface InheritedAnnotation {
        String value() default "test";
    }

    // two simple classes that will show that the annotation is *not* inherited
    @SimpleAnnotation
    class BaseClass {
    }

    class SubclassDoesNotInheritAnnotation extends BaseClass {
    }

    // two simple classes that will show that the annotation *is* inherited
    @InheritedAnnotation
    class InheritedBase {
    }

    class ChildInheritsAnnotation extends InheritedBase {
    }

    // proof
    @Test
    public void testAnnotations() {
        // not inherited
        Annotation[] annotations = BaseClass.class.getAnnotations();
        assertEquals(1, annotations.length);
        annotations = SubclassDoesNotInheritAnnotation.class.getAnnotations();
        assertEquals(0, annotations.length);

        // inherited
        annotations = InheritedBase.class.getAnnotations();
        assertEquals(1, annotations.length);
        annotations = ChildInheritsAnnotation.class.getAnnotations();
        assertEquals(1, annotations.length);
    }

}
