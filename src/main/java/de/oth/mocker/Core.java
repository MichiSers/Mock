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
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new CMethodInterceptor(this,settings.isSpy()));
		
		T mock = (T) enhancer.create();
		
		 if (settings.isSpy()) {
	            new CCopyClass().copyToMock(settings.isSpy(), mock);
	        }

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
