package de.oth.mocker;

import java.util.HashMap;

import net.sf.cglib.proxy.Enhancer;

/**
 * The mock object is created here.
 * Also the verification variables are set and 
 * the hashmap for method calls is stored.
 * 
 * @author Michael Stadler
 *
 */
public class Core
{
	protected boolean verification = false;
	protected HashMap<UniqueKey, Integer> map = new HashMap<UniqueKey, Integer>();
	protected VerificationType verType = null;
	
	/**
	 * Creates a mock object with the cglib enhancer class.
	 * Sets an interface so the objcet can be recognized as
	 * mock object.
	 * 
	 * @param clazz			the mocked class
	 * @param settings		the settings
	 * @return				a mock object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T mock(Class<T> clazz, MockSettings settings)
	{
		Class[] interfaces = {Mock.class};
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new CMethodInterceptor(this,settings.isSpy()));
		enhancer.setInterfaces(interfaces);
		T mock = (T) enhancer.create();
		return mock;
	}

	/**
	 * Sets the verification variable.
	 * 
	 * @param verification		if true, the method interceptor uses the verification mode
	 */
	public void setVerification(boolean verification)
	{
		this.verification = verification;
	}
	
	/**
	 * The verification type (can be times,never,atLeast,atMost)
	 * 
	 * @param verType		the verification type
	 */
	public void setVerType(VerificationType verType)
	{
		this.verType = verType;
	}

}
