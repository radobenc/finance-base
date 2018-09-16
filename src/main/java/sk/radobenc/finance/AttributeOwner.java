package sk.radobenc.finance;

import java.io.Serializable;
import java.util.Map;

public interface AttributeOwner extends Serializable {

	<V> Value<V> addAttribute(Attribute<V> attribute, Value<V> value);

	<V> V getAttribute(Attribute<V> attribute, long time);

	<V> V getAttribute(Attribute<V> attribute, long time, V defaultValue);

	Map<Attribute<?>, Value<?>> getAttributes();

	@SuppressWarnings("rawtypes")
	Map<Attribute, Object> getAttributes(long time);

	<V> boolean hasAttribute(Attribute<V> attribute);

	<V> Value<V> removeAttribute(Attribute<V> attribute);

	<V> Value<V> setAttribute(Attribute<V> attribute, Value<V> value);

	<V> Value<V> setAttribute(Attribute<V> attribute, V value, long time);

	void setAttributes(Map<Attribute<?>, Value<?>> attributeValues);
}
