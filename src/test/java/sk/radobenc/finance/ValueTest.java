package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import sk.radobenc.finance.impl.SimpleValue;

public class ValueTest {

	@Test
	public void testBeginningOfTime() {
		Value<BigDecimal> v = new SimpleValue<BigDecimal>(BigDecimal.TEN, Value.MIN_TIME);
		assertEquals(BigDecimal.TEN, v.get(0L));
	}

	@Test
	public void testEndOfTime() {
		Value<BigDecimal> v = new SimpleValue<BigDecimal>(BigDecimal.TEN, Value.MAX_TIME);
		assertEquals(BigDecimal.TEN, v.get(Value.MAX_TIME));
		assertNull(v.get(0L));
	}

	@Test
	public void testHistory() {
		Value<String> v = new SimpleValue<String>("A", 0L);
		assertEquals("A", v.get(0L));
		assertEquals("A", v.get(5L));
		v.set(10L, "B");
		assertEquals("B", v.get(10L));
		assertEquals("B", v.get(15L));
		v.set(20L, null);
		assertNull(v.get(20L));
		assertNull(v.get(25L));
		assertNull(v.get(-20L));
	}

	@Test
	public void testLongHistory() {
		Value<String> v = new SimpleValue<String>("A");
		assertEquals("A", v.get(0L));
		assertEquals("A", v.get(5L));
		v.set(10L, "B");
		assertEquals("B", v.get(10L));
		assertEquals("B", v.get(15L));
		v.set(20L, null);
		assertNull(v.get(20L));
		assertNull(v.get(25L));
		v.set(30L, "C");
		assertEquals("C", v.get(30L));
		assertEquals("C", v.get(35L));
		//
		assertNull(v.get(-10L));
	}

}
