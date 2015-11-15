package deniz;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * Created by wagum on 26.10.2015.
 */
public class Mocker {
    private static Map<String, Integer> mapMethodCalls = new LinkedHashMap<>();



    /*
     * Factory Methode gibt das mockobject zurück. Wird in der verify methode mit dem Parameter internal == True aufgerufen.
     * Dadurch wird ein unterschiedlicher Callback definiert. 
     */
    private static <T> T mock(Class<T> clazz, boolean internal, Integer times) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        if (!internal) {
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    //fill the map with method calls here
                    putMethodCalls(method, objects);

                    //the different return values according to type
                    
                    if (method.getReturnType().isPrimitive() && ! (method.getReturnType().equals(Boolean.TYPE))) {
                        return 0;
                    } else if(method.getReturnType().isPrimitive() && (method.getReturnType().equals(Boolean.TYPE))) {
                        return false;
                    }

                    return null;
                }
            });
        } else {
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    //hier jetzt schaun was zu überprüfen ist
                    //proxy object erzeugen was die method calls die zu überprüfen sind handled
                	Verification(generateKey(method, objects), times);
                    //the different return values according to type
                    if (method.getReturnType().isPrimitive() && ! (method.getReturnType().equals(Boolean.TYPE))) {
                        return 0;
                    } else if(method.getReturnType().isPrimitive() && (method.getReturnType().equals(Boolean.TYPE))) {
                        return false;
                    }

                    return null;
                }
            });
        }


        return (T) enhancer.create();
    }

    private static void Verification(String key, Integer times) {
        System.out.println("Verifying key "+key+" expected number of calls: " + times + ". actual number of calls: " + mapMethodCalls.get(key));
        if (mapMethodCalls.get(key) != times) {
            throw new AssertionError("expected number of calls: " +times + " but was " +mapMethodCalls.get(key));
        }

    }



    public static <T> T mock (Class<T> clazz) {
        return mock(clazz, false, 0); //0 weils egal is
    }


    private static void putMethodCalls(Method method, Object[] objects) {
        String key = generateKey(method, objects);
        if (mapMethodCalls.get(key) == null) {
            System.out.println("creating entry for new key = " + key  );
            mapMethodCalls.put(key, 1);
        } else {
            int newCount = mapMethodCalls.get(key) + 1;
            System.out.println("Incrementing counter for key = " + key + ". current calls: " + newCount);
            mapMethodCalls.put(key, newCount);
        }
    }

  
    private static String generateKey(Method method, Object[] objects) {
        StringBuilder builder = new StringBuilder();
        builder.append(method.getName());
        for (Object obj : objects) {
            builder.append(obj.toString());
        }
        return builder.toString();

    }

    public interface Verification {
        static <T> T verify(T toVerify, Integer times) {
            return mock((Class<T>) toVerify.getClass().getSuperclass(), true, times);
        }

        static <T> T verify(T toVerify) {
            return mock((Class<T>) toVerify.getClass().getSuperclass(), true, 1);
        }
    }

    public interface TimesBuilder {
        static Integer times(Integer anzahl) {
            return anzahl;
        }

        static Integer never() {
            return 0;
        }

    }



}
