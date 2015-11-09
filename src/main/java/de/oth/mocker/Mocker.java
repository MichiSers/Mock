package de.oth.mocker;

import java.util.ArrayList;
import java.util.HashMap;
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
	public static <T> T spy(T object){
		
		MockSettings settings = new MockSettings();
		settings.setSpy(true);
		return mock((Class<T>) object.getClass(), settings);
	}

	public static <T> T verify(T o)
	{
		if(!(o instanceof Mocker)){
			try
			{
				throw new Exception("Not a mocker/spy");
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CORE.setVerification(true);
		return o;
	}

	public static void times()
	{

	}

	public static void never()
	{

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
