package sk.radobenc.finance;

import java.util.ArrayList;
import java.util.List;

import sk.radobenc.finance.impl.AbstractPayment;
import sk.radobenc.finance.impl.ValidatorFactory;

public class Payments {

	private static Validator<String> currencyCodeValidator = ValidatorFactory.createCurrencyCodeValidator();

	public static Payment create(final Number amount, final String currencyCode) {
		if (null == amount) {
			throw new IllegalArgumentException("Illegal amount = " + amount);
		}
		if (currencyCodeValidator.isValid(currencyCode)) {
			final Payment p = new AbstractPayment(amount) {

				private static final long serialVersionUID = -5115732910958526678L;

			};
			p.addAttribute(Attribute.CURRENCY_CODE, currencyCode);
			return p;
		}
		throw new IllegalArgumentException(String.format("Illegal currency code = '%s'", currencyCode));
	}

	public static List<Payment> list(final Order order) {
		if (null == order) {
			throw new IllegalArgumentException("order = " + order);
		}
		final List<Payment> result = new ArrayList<Payment>();
		FinDate current = order.getStartDate();
		final String currencyCode = (String) order.getAttribute(Attribute.CURRENCY_CODE);
		if (null == currencyCode) {
			throw new IllegalArgumentException("order.currecy = " + currencyCode);
		}
		while (!current.after(order.getEndDate())) {
			final Payment p = create(order.getAmount(), (String) order.getAttribute(Attribute.CURRENCY_CODE));
			p.putAttributes(order.getAttributes());
			FinDate billingDate = current;
			if (order.isSkippingWeekends()) {
				billingDate = current.previousWorkingDate();
			}
			p.addAttribute(Attribute.BILLING_DATE, billingDate);
			p.addAttribute(Attribute.ORDER_NAME, order.getName());
			result.add(p);
			switch (order.getFrequency()) {
			case DAILY:
				current = current.addDays(1);
			case MONTHLY:
				current = current.addMonths(1);
				break;
			case ANNUALLY:
				current = current.addYear();
			default:
				throw new UnsupportedOperationException("Unsupported order frequency = " + order.getFrequency());
			}
		}
		return result;
	}

}
