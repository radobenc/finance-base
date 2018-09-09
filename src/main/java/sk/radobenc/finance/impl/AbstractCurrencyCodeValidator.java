package sk.radobenc.finance.impl;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import sk.radobenc.finance.FinanceUtils.LazyValue;
import sk.radobenc.finance.Validator;

public abstract class AbstractCurrencyCodeValidator implements Validator<String> {

	private final LazyValue<Set<String>> currencyCodeCache = new LazyValue<Set<String>>() {

		@Override
		public Set<String> create() {
			final Set<String> result = new HashSet<String>();
			final Set<Currency> currencies = Currency.getAvailableCurrencies();
			for (final Currency c : currencies) {
				result.add(c.getCurrencyCode());
			}
			return result;
		}

	};

	public boolean isValid(final String currencyCode) {
		return currencyCodeCache.get().contains(currencyCode);
	}

}
