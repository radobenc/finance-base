package sk.radobenc.finance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AttributeTest.class, CalculatorTest.class, FinanceUtilsTest.class, FinDateTest.class,
		OrderTest.class, PaymentTest.class, PaymentsTest.class, TermTest.class, ValidatorTest.class })
public class AllTests {

}
