package de.oth.mocker;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public interface Mocker
{
	

	@SuppressWarnings("unchecked")
	public static <T> T mock(Class <T> clazz)
	{
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new MethodInterceptor(){
			int called=0;
			@Override
			public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy)
					throws Throwable
			{
				
//				System.out.println("Object "+ obj);
				System.out.println("Invoked "+method.getName());
				for(Object o : args){
					System.out.println("Object[] "+o);
				}
//				System.out.println("Object[] "+args[0]);
//				System.out.println("Object[] "+args[1]);
//				System.out.println("Object[] "+args[2]);
				called++;
				System.out.println("Called: "+called);
				
				return null;
			}
		});
		return (T) enhancer.create();
	}
	
	public static void verify()
	{
		
	}

	public static void times()
	{

	}

	public static void never()
	{

	}
	
	public static void main(String[] args){
		@SuppressWarnings("unchecked")
		List<String>	mockObject	=	mock(ArrayList.class);
		System.out.println(mockObject.add("John	Doe"));
		System.out.println(mockObject.add("John	Noe"));
		System.out.println(mockObject.add("John	Doe"));
		
	}
}
