package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class AttributeTest {

	@Test
	public void testEquals() {
		final Attribute<?> a1 = Attributes.create("A");
		assertFalse(a1.equals(null));
		assertFalse(a1.equals(new Object()));
		final Attribute<?> a2 = Attributes.create("A");
		assertEquals(a1, a2);
	}

	@Test
	public void testHashCode() {
		final Attribute<?> a1 = Attributes.create("A");
		final Attribute<?> a2 = Attributes.create("A");
		assertEquals(a1.hashCode(), a2.hashCode());
		final Attribute<?> a3 = Attributes.create("B");
		assertFalse(a1.hashCode() == a3.hashCode());
	}
}
