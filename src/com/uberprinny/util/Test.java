package com.uberprinny.util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class Test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@org.junit.Test
	public void test() throws ExpressionFormatException {
		IntegerEvaluator e = new IntegerEvaluator();	
		
		assertEquals(13, e.eval("11+2"));
		assertEquals(13, e.eval("11 + 2"));
		
		assertEquals(-4, e.eval("5 - 9"));
		assertEquals(-10, e.eval("-5-5"));
		assertEquals(-500, e.eval("-500"));
		
		assertEquals(40, e.eval("5*8"));
		assertEquals(40, e.eval(" 5 * 8 "));
		assertEquals(0, e.eval("-4 * 0 + 0 - 0 * 0 + 5 - 5"));
		
		assertEquals(54, e.eval(" 5 * 8 + 14")); // order of operations retained
		assertEquals(54, e.eval(" 14 + 5 * 8"));
		
		assertEquals(-8, e.eval("-5 + ((3 - 4) * (8 + 1)) + 3 * 2"));
		assertEquals(-231, e.eval("-5 * (((-5 + 2) * (-9) - 4) * 2) - 1"));
		
		assertEquals(0, e.eval("-1*(-1)-1"));
		assertEquals(0, e.eval("-4--4-(-4--4)"));
		
		assertEquals(1, e.eval("--1"));
		assertEquals(-51, e.eval("---51"));
		assertEquals(5, e.eval(" - - 5"));
		assertEquals(1, e.eval("-(-1)"));
		assertEquals(9, e.eval("8+(-(-1))"));
		assertEquals(7, e.eval("8+(--1)-2"));
		
		assertEquals(2, e.eval("8/4"));
		assertEquals(10, e.eval("81/4/2")); // integer division truncates remainder
		
		assertEquals(40, e.eval("81/4*2")); // order of operations
		assertEquals(10, e.eval("81/(4*2)")); // order of operations still matters
		
		assertEquals(1, e.eval("256%3"));
		
		assertEquals(1, e.eval("22 % 14 / 7")); // order of operations (left to right) matters, despite equal precedence
		assertEquals(0, e.eval("22 % (14 / 7)"));
		
		assertEquals(5, e.eval("15 % 10"));
		assertEquals(-5, e.eval("-5 % 10")); // same as above
		assertEquals(0, e.eval("15 % -3")); // same as above
		
		assertEquals(4295, e.eval("1 * -(4 + (8--4) % 3 * 16 / (127 % 11) + 65 * ((-8) * -(-4) - 1) * 2) + 16 - 7"));
	}

}
