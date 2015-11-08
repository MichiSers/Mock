package de.oth.mocker;

import java.util.HashMap;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class Core
{
	boolean verification = false;
	HashMap<UniqueKey, Integer> map = new HashMap<UniqueKey, Integer>();
	UniqueKey key;

	@SuppressWarnings("unchecked")
	public <T> T mock(Class<T> clazz)
	{
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new MethodInterceptor()
		{

			@Override
			public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy)
					throws Throwable
			{
				key = new UniqueKey(method.getName(), args);
				if (verification)
				{
					if (map.get(key) == null)
					{
						System.out.println("Was not invoked");
					} else
					{
						System.out.println("Was invoked " + map.get(key) + " times");
					}
					verification = false;
				} else
				{
					int count = map.containsKey(key) ? map.get(key) : 0;
					map.put(key, count + 1);
					System.out.println("Count: " + count);
				}

				return null;
			}
		});
		return (T) enhancer.create();
	}

	public void setVerification(boolean b)
	{
		verification = b;
	}
}
