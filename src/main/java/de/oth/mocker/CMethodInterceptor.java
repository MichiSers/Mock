package de.oth.mocker;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CMethodInterceptor implements MethodInterceptor
{
	private Core core = null;
	private boolean isSpy = false;
	protected UniqueKey key;

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
				System.out.println("Was not invoked");
			} else
			{
				System.out.println("Was invoked " + core.map.get(key) + " times");
			}
			core.verification = false;
		} else
		{
			
			{
				int count = core.map.containsKey(key) ? core.map.get(key) : 0;
				core.map.put(key, count + 1);
				if (isSpy == true)
				{
					System.out.println("Is a spy");
					System.out.println("Class Name : "+proxy.getClass());
					proxy.invokeSuper(obj, args);
				}
				System.out.println("Times called: " +core.map.get(key) );
			}
		}

		return null;
	}

}
