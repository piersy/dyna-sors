package com.piersyp.dynasors.example.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationFunctionUnitTest {

    @Before
    public void setUp() throws Exception {
       // new ConfigurationFunction(filterFunctionMock)

    }

    @Test
    public void givenListContainingMultiplePathAnnotationsAlongsideOtherAnnotations_whenApply_thenWebResourceConfiguredWithPathMatchingOnlyThosePathAnnotationsReturned() throws Exception {
        //could we break this down into a filtering step filterByClassFunction and an application function and a function that combines those 2 functions?
        //Then this test name would be significantly simpler to write
        //Also should break down
    }

    @Test
    public void givenListOfAnnotations_whenApply_thenExpectedOperationsPerformedOnWebResourceBuilder() throws Exception {

    }



}