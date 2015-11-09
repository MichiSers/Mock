package de.oth.mocker;

import java.util.List;

public interface Mocker
{
	static final Core CORE = new Core();

	public static <T> T mock(Class<T> clazz)
	{
		MockSettings settings = new MockSettings();
		return mock(clazz, settings);
	}

	public static <T> T mock(Class<T> clazz, MockSettings settings)
	{
		return CORE.mock(clazz, settings);
	}

	@SuppressWarnings("unchecked")
	public static <T> T spy(T object)
	{

		MockSettings settings = new MockSettings();
		settings.setSpy(true);
		return mock((Class<T>) object.getClass(), settings);
	}

	public static <T> T verify(T o)
	{
		
		return verify(o, new VerificationTypeFactory().times(1));
	}

	public static <T> T verify(T o, VerificationType verType)
	{
		 if(!(o instanceof Mock)){
			 throw new RuntimeException("Not a mock/spy");
			 }
		CORE.setVerType(verType.getType());
		CORE.setVerification(true);
		return o;
	}

	public static VerificationType times(int times)
	{
		return new VerificationTypeFactory().times(times);
	}

	public static VerificationType never()
	{
		return new VerificationTypeFactory().times(0);
	}
	
	public static VerificationType atLeast(int atLeast)
	{
		return new VerificationTypeFactory().atLeast(atLeast);
	}

	public static VerificationType atMost(int atMost)
	{
		return new VerificationTypeFactory().atMost(atMost);
	}

}
