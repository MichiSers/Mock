package de.oth.mocker;

import java.util.HashMap;

import net.sf.cglib.proxy.Enhancer;

public class Core
{
	protected boolean verification = false;
	protected HashMap<UniqueKey, Integer> map = new HashMap<UniqueKey, Integer>();
	protected VerificationType verType = null;
	
	@SuppressWarnings("unchecked")
	public <T> T mock(Class<T> clazz, MockSettings settings)
	{
		Class[] interfaces = {Mock.class};
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new CMethodInterceptor(this,settings.isSpy()));
		enhancer.setInterfaces(interfaces);
		T mock = (T) enhancer.create();
		System.out.println(mock.getClass());
		return mock;
	}

	public void setVerification(boolean verification)
	{
		this.verification = verification;
	}
	
	public void setVerType(VerificationType verType)
	{
		this.verType = verType;
	}

}
