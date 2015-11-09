package de.oth.mocker;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

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
					throw new Exception("Verification failure: Expected number of calls " + verType.getTimes()
							+ " but was " + invoked);
				}
			} else if (verType instanceof AtMost)
			{
				if (invoked > verType.getAtMost())
				{
					throw new Exception("Verification failure: Expected number of calls at most " + verType.getAtMost()
							+ " but was " + invoked);
				}
			} else if (verType instanceof AtLeast)
			{
				if (invoked < verType.getAtLeast())
				{
					throw new Exception("Verification failure: Expected number of calls at Least "
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
					// System.out.println("Is a spy");
					// System.out.println("Class Name : "+proxy.getClass());
					proxy.invokeSuper(obj, args);
				}
				// System.out.println("Times called: " +core.map.get(key) );
			}
		}

		return null;
	}

}
