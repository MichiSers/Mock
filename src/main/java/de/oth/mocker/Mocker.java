package de.oth.mocker;

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
		// if(!(o instanceof Mocker)){
		// try
		// {
		// throw new Exception("Not a mocker/spy");
		// } catch (Exception e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		return verify(o, new VerificationTypeFactory().times(1));
	}

	public static <T> T verify(T o, VerificationType verType)
	{
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


	// public static void main(String[] args){
	// @SuppressWarnings("unchecked")
	// List<String> mockObject = mock(ArrayList.class);
	// mockObject.add("10");
	// mockObject.add("asd");
	// mockObject.add("10");
	// verify(mockObject).add("10");
	// verify(mockObject).add("asd");
	// verify(mockObject).add("5");
	// }
}
