package sk.radobenc.finance;

import java.io.Serializable;

/**
 * Value represents an object whose value can change over time. The actual value
 * can be set or determined by providing parameter <b>time</b>.
 * <p>
 * For example, the value object can be initialized to have value &quot;A&quot;
 * at time 0 and then change the value to &quot;B&quot; at time 10. Querying the
 * Value object at time -10 will return null, at time 5 will return
 * &quot;A&quot;, and at 15 will return &quot;B&quot;.
 * 
 * @author radobenc@gmail.com
 *
 * @param <V>
 */
public interface Value<V> extends Serializable {

	long MAX_TIME = Long.MAX_VALUE;
	long MIN_TIME = Long.MIN_VALUE;

	/**
	 * Return the value for the specified time.
	 * 
	 * @param time the time to set
	 * @return the value
	 */
	V get(long time);

	/**
	 * Sets the value for the specified time.
	 * 
	 * @param time  the time to set
	 * @param value the value to set
	 */
	void set(long time, V value);

}
