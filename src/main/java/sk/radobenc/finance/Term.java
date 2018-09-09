package sk.radobenc.finance;

import java.io.Serializable;

public interface Term extends Serializable {

	boolean dateIsAfter(FinDate date);

	boolean dateIsBefore(FinDate date);

	boolean dateIsIn(FinDate date);

	FinDate getEndDate();

	FinDate getStartDate();
}
