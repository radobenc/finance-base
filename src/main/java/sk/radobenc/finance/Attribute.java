package sk.radobenc.finance;

import static sk.radobenc.finance.Attributes.create;

import java.io.Serializable;

public interface Attribute<V> extends Cloneable, Serializable {

	public interface Matcher<V> {

		long getCount();

		long getHitCount();

		boolean matches(V v1, V v2);

	}

	Attribute<FinDate> BILLING_DATE = create("BILLING_DATE", Attributes.createFinDateAttributeMatcher("BILLING_DATE"));
	Attribute<String> CONSTANT_SYMBOL = create("CONSTANT_SYMBOL");
	Attribute<String> CURRENCY_CODE = create("CURRENCY_CODE", true);
	Attribute<String> DESCRIPTION = create("DESCRIPTION");
	Attribute<String> ORDER_NAME = create("ORDER_NAME");
	Attribute<FinDate> PROCESSING_DATE = create("PROCESSING_DATE",
			Attributes.createFinDateAttributeMatcher("PROCESSING_DATE"));
	Attribute<String> SOURCE_ACCOUNT = create("SOURCE_ACCOUNT");
	Attribute<String> SPECIFIC_SYMBOL = create("SPECIFIC_SYMBOL");
	Attribute<String> TARGET_ACCOUNT = create("TARGET_ACCOUNT");
	Attribute<String> VARIABLE_SYMBOL = create("VARIABLE_SYMBOL");

	Matcher<V> getMatcher();

	String getName();

	boolean isMandatory();

}
