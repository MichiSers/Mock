package de.oth.mocker;

import java.util.HashMap;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class Core {
	
	boolean verification = false;
	HashMap<String,Integer> map = new HashMap<String,Integer>();
	
	public <T> T mock(Class <T> clazz){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new MethodInterceptor(){
			int called=0;
			
			@Override
			public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy)
					throws Throwable
			{
				
				if(verification){
					System.out.println(map.get(method.getName()));
					verification=false;
				}else{
					int count = map.containsKey(method.getName()) ? map.get(method.getName()) : 0;
					map.put(method.getName(), count + 1);
				}
				
				
				
//				
				
				return null;
			}
		});
		return (T) enhancer.create();
	}
	

public void setVerification(boolean b) {
	verification = b;
}
}
