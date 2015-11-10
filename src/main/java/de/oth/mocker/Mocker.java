package de.oth.mocker;

/**
 * This is the main interface used to create mock and spy
 * objects. A core is created that most of the methods are
 * delegated to.
 * 
 * @author Michael Stadler
 *
 */
public interface Mocker
{
	static final Core CORE = new Core();

	/**
	 * This sets the default settings and calls 
	 * the mock method with settings.
	 *
	 * 
	 * @param clazz		the class to mock
	 * @return			the mock method with settings
	 */
	public static <T> T mock(Class<T> clazz)
	{
		MockSettings settings = new MockSettings();
		return mock(clazz, settings);
	}

	/**
	 * Calls the mock function of the core class.
	 * 
	 * @param clazz		the class to mock
	 * @param settings	the settings
	 * @return			a mock object
	 */
	public static <T> T mock(Class<T> clazz, MockSettings settings)
	{
		return CORE.mock(clazz, settings);
	}

	/**
	 * Creats a spy object that uses the original methods of
	 * the passed object.
	 * 
	 * @param object	the object to spy
	 * @return			a spy
	 */
	@SuppressWarnings("unchecked")
	public static <T> T spy(T object)
	{

		MockSettings settings = new MockSettings();
		settings.setSpy(true);
		return mock((Class<T>) object.getClass(), settings);
	}

	/**
	 * Calls the verify method with the default verify type.
	 * 
	 * @param object		the object which methods are to be verified
	 * @return				the object which methods are to be verified
	 */
	public static <T> T verify(T object)
	{
		
		return verify(object, new VerificationTypeFactory().times(1));
	}

	/**
	 * Sets the verify variable in the core to true and this way enables verification 
	 * of method calls. Also tests if the object is a mock or spy.
	 * 
	 * @param object		the object which methods are to be verified
	 * @param verType		the verification type
	 * @return
	 */
	public static <T> T verify(T object, VerificationType verType)
	{
		 if(!(object instanceof Mock)){
			 throw new RuntimeException("Not a mock/spy");
			 }
		CORE.setVerType(verType.getType());
		CORE.setVerification(true);
		return object;
	}

	/**
	 * Sets the number of times a method should have been called.
	 * Used only with verifiy.
	 * 
	 * @param times		number of calls
	 * @return			a verificationType (Times)
	 */
	public static VerificationType times(int times)
	{
		return new VerificationTypeFactory().times(times);
	}

	/**
	 * Sets the number of times a method should have been called to 0.
	 * Used only with verifiy.
	 * 
	 * @return			a verificationType (Never)
	 */
	public static VerificationType never()
	{
		return new VerificationTypeFactory().times(0);
	}
	
	/**
	 * Sets the number of times a method should have at least been called.
	 * Used only with verifiy.
	 * 
	 * @param times		number of minimum calls
	 * @return			a verificationType (atLeast)
	 */
	public static VerificationType atLeast(int atLeast)
	{
		return new VerificationTypeFactory().atLeast(atLeast);
	}

	/**
	 * Sets the number of times a method should have at most been called.
	 * Used only with verifiy.
	 * 
	 * @param times		number of maximum calls
	 * @return			a verificationType (atMost)
	 */
	public static VerificationType atMost(int atMost)
	{
		return new VerificationTypeFactory().atMost(atMost);
	}

}
