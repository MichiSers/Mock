package de.oth.mocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Mocker
{
	static final Core CORE = new Core();

	public static <T> T mock(Class <T> clazz)
	{
		return CORE.mock(clazz);
	}
	
	public static <T> T verify(T o)
	{
		CORE.setVerification(true);
		return o;
	}

	public static void times()
	{

	}

	public static void never()
	{

	}
	
//	public static void main(String[] args){
//		@SuppressWarnings("unchecked")
//		List<String>	mockObject	=	mock(ArrayList.class);
//		mockObject.add("10");
//		mockObject.add("asd");
//		mockObject.add("10");
//		verify(mockObject).add("10");
//		verify(mockObject).add("asd");
//		verify(mockObject).add("5");
//	}
}
