package sk.radobenc.finance;

public interface Validator<V> {

	boolean isValid(V validatedObject);

}
