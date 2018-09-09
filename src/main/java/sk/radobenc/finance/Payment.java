package sk.radobenc.finance;

import java.io.Serializable;
import java.util.Map;

public interface Payment extends Serializable {

	<V> V addAttribute(Attribute<V> attribute, V value);

	Number getAmount();

	Object getAttribute(Attribute<?> attribute);

	Object getAttribute(Attribute<?> billingDate, Object empty);

	Map<Attribute<?>, Object> getAttributes();

	<V> boolean hasAttribute(Attribute<V> attribute);

	void putAttributes(Map<Attribute<?>, Object> attributes);

	<V> V removeAttribute(Attribute<V> attribute);

}
