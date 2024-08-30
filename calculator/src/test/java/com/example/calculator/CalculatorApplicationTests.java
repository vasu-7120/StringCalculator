package com.example.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.calculator.service.Service;

@SpringBootTest
class CalculatorApplicationTests {
	
	@Autowired
	Service service;

	@Test
    public void testEmptyString() {
        assertEquals(3, service.add("//;\n1;2"));
    }

    @Test
    public void testSingleNumber() {
    	assertEquals(5, service.add("5"));
    }

    @Test
    public void testTwoNumbers() {
        assertEquals(3, service.add("1,2"));
    }

    @Test
    public void testMultipleNumbers() {
        assertEquals(6, service.add("1,2,3"));
    }

    @Test
    public void testNewLineBetweenNumbers() {
        assertEquals(6, service.add("1\n2,3"));
    }

    @Test
    public void testCustomDelimiter() {
        assertEquals(3, service.add("//;\n1;2"));
    }

    @Test
    public void testAnotherCustomDelimiter() {
        assertEquals(6, service.add("//|\n1|2|3"));
    }

    @Test
    public void testNegativeNumber() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.add("1,-2,3");
        });
        assertEquals("Negative numbers not allowed: -2", thrown.getMessage());
    }

    @Test
    public void testMultipleNegativeNumbers() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.add("1,-2,-3,4");
        });
        assertEquals("Negative numbers not allowed: -2,-3", thrown.getMessage());
    }

}
