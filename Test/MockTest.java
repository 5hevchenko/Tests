package com.company;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by imac on 26.02.2019.
 */
public class MockTest {
    @Test
    public void iteratorTest() {
        Iterator i = mock(Iterator.class);

        when(i.next()).thenReturn("Hello").thenReturn("World");

        String result = i.next() + " " + i.next();
        System.out.println(result);
        assertEquals(result, "Hello World");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParameters() {
        SumTest test = mock(SumTest.class);
        when(test.sum(2, 2)).thenThrow(IllegalArgumentException.class);
        test.sum(2,2);
    }

    private class SumTest {
        public int sum(int a, int b) {
            return a + b;
        }
    }
}