package de.oth.mocker;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * This class is a callback for the enhancer to intercept called methods
 * of the mock object.
 * 
 * @author Michael Stadler
 *
 */
public class CMethodInterceptor implements MethodInterceptor
{
	private Core core = null;
	private boolean isSpy = false;
	private UniqueKey key;
	private int invoked = 0;
	private VerificationType verType = null;

	public CMethodInterceptor(Core core, boolean isSpy)
	{
		this.core = core;
		this.isSpy = isSpy;
	}

	/**
	 * Called when a method of the mock object is called.
	 * Decides whether a verification process is needed or
	 * a normal method interception.
	 * Also distinguishes between mock and spy method calls.
	 * 
	 * Here the number of calls are put into a hashMap with a 
	 * UniqueKey to be questioned later.
	 *
	 * 
	 * @param obj 		the mock object
	 * @param method		the called method
	 * @param args		an array of the parameters
	 * @param proxy		the original method
	 * @return			null
	 */
	@Override
	public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy)
			throws Throwable
	{
		key = new UniqueKey(method.getName(), args);
		if (core.verification)
		{
			if (core.map.get(key) == null)
			{
				invoked = 0;
			} else
			{

				invoked = core.map.get(key);
			}
			verType = core.verType;
			if (verType instanceof Times)
			{
				if (invoked != verType.getTimes())
				{
					throw new AssertionError("Verification failure: Expected number of calls " + verType.getTimes()
							+ " but was " + invoked);
				}
			} else if (verType instanceof AtMost)
			{
				if (invoked > verType.getAtMost())
				{
					throw new AssertionError("Verification failure: Expected number of calls at most " + verType.getAtMost()
							+ " but was " + invoked);
				}
			} else if (verType instanceof AtLeast)
			{
				if (invoked < verType.getAtLeast())
				{
					throw new AssertionError("Verification failure: Expected number of calls at Least "
							+ verType.getAtLeast() + " but was " + invoked);
				}

			}
			core.verification = false;
		} else
		{

			{
				int count = core.map.containsKey(key) ? core.map.get(key) : 0;
				core.map.put(key, count + 1);
				if (isSpy == true)
				{
					proxy.invokeSuper(obj, args);
				}
			}
		}

		return null;
	}

}
