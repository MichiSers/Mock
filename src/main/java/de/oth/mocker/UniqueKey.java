package de.oth.mocker;

import java.util.Arrays;

/**
 * This class provides a unique key for each method called
 * by the method interceptor.
 * The uniqueness come from the class getting the method name 
 * and all arguments.
 * 
 * @author Michael Stadler
 *
 */
public class UniqueKey {

	public String methodName;
	Object[] args;

	public UniqueKey(String methodName, Object[] arguments) {
		this.methodName = methodName;
		this.args = arguments;

	}

	/**
	 * Eclipse generated hashCode :-)
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		return result;
	}

	/**
	 * A key is identical if the method name and all arguments are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UniqueKey other = (UniqueKey) obj;
		if (!Arrays.equals(args, other.args))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		return true;
	}
	
}
