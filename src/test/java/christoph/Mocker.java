package christoph;

import static christoph.Rate.RateState;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Mocker {

    private static Map<Method, List<MethodCall>> reallyInvokedMethods;
    // Todo: sysouts -> logger
    private static Logger logger = Logger.getLogger(Mocker.class);

    // used for testing while developing
    public static void main(String[] args) {
        // only basic logger config for this project
        BasicConfigurator.configure();

        spytesting();
        mocktesting();
    }

    public static <T> T mock(Class<T> clazz) {
        logger.info("mock( " + clazz.toString() + " ) is running.");

        resetReallyInvokedMethodsMap();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (Object o, Method method, Object[] os, MethodProxy mp) -> {

            myInterceptLogic(method, os);

            // every method-call on a mock-object has to return null
            return null;
        });

        return clazz.cast(enhancer.create());
    }

    public static <T> T spy(Object obj) {
        resetReallyInvokedMethodsMap();

        // getting the class of given object
        Class<T> clazz = (Class<T>) obj.getClass();
        logger.info("spy( " + clazz.toString() + " ) is running.");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (Object o, Method method, Object[] os, MethodProxy mp) -> {

            myInterceptLogic(method, os);

            // really invoke the method of the real object
            // and save the return object
            Object returnObj = method.invoke(obj, os);

            // returns the returning object of the real methodcall on the real object
            return returnObj;
        });

        return clazz.cast(enhancer.create());
    }

    public static <T> T verify(T mockObj, Rate times) {

        Class<T> clazz = (Class<T>) mockObj.getClass().getSuperclass();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (Object o, Method method, Object[] os, MethodProxy mp) -> {
            MethodCall erwWert;
            erwWert = new MethodCall(method, os);

            switch (times.getState()) {
                case NEVER:
                    if (reallyInvokedMethods.containsKey(method)) {
                        List<MethodCall> methodCalls = reallyInvokedMethods.get(method);
                        if (methodCalls.contains(erwWert)) {
                            int index = methodCalls.indexOf(erwWert);
                            int timesCalled = methodCalls.get(index).getCounter();

                            // TODO is this condition check necessary?
                            if (timesCalled != 0) {
                                throw new AssertionError("Verification failure: Expected number of calls " + times.getRate() + " but was " + timesCalled);
                            }
                        }
                    }
                    break;

                case TIMES:
                    if (reallyInvokedMethods.containsKey(method)) {
                        List<MethodCall> methodCalls = reallyInvokedMethods.get(method);
                        if (methodCalls.contains(erwWert)) {
                            int index = methodCalls.indexOf(erwWert);
                            int timesCalled = methodCalls.get(index).getCounter();
                            if (!(timesCalled == times.getRate())) {
                                throw new AssertionError("Verification failure: Expected number of calls " + times.getRate() + " but was " + timesCalled);
                            }
                        } else {
                            throw new AssertionError("Verification failure: parameter doesn't match");
                        }
                    } else {
                        throw new AssertionError("Verification failure: method doesn't match");
                    }
                    break;

                case ATLEAST:
                    if (reallyInvokedMethods.containsKey(method)) {
                        List<MethodCall> methodCalls = reallyInvokedMethods.get(method);
                        if (methodCalls.contains(erwWert)) {
                            int index = methodCalls.indexOf(erwWert);
                            int timesCalled = methodCalls.get(index).getCounter();
                            if (!(timesCalled >= times.getRate())) {
                                throw new AssertionError("Verification failure: Expected number of calls at least " + times.getRate() + " but was " + timesCalled);
                            }
                        } else {
                            throw new AssertionError("Verification failure: parameter doesn't match");
                        }
                    } else if (!(times.getRate() == 0)) {
                        throw new AssertionError("Verification failure: method doesn't match");
                    }
                    break;

                case ATMOST:
                    if (reallyInvokedMethods.containsKey(method)) {
                        List<MethodCall> methodCalls = reallyInvokedMethods.get(method);
                        if (methodCalls.contains(erwWert)) {
                            int index = methodCalls.indexOf(erwWert);
                            int timesCalled = methodCalls.get(index).getCounter();
                            if (!(timesCalled <= times.getRate())) {
                                throw new AssertionError("Verification failure: Expected number of calls at most " + times.getRate() + " but was " + timesCalled);
                            }
                        } else {
                            throw new AssertionError("Verification failure: parameter doesn't match");
                        }
                    } else if (!(times.getRate() >= 0)) {
                        throw new AssertionError("Verification failure: method doesn't match");
                    }
                    break;

                default:
                    throw new AssertionError("There was another error. No RateState is set?!");
            }

            return null;
        });

        return clazz.cast(enhancer.create());
    }

    private static void myInterceptLogic(Method method, Object[] os) {
        MethodCall currMethCall;
        currMethCall = new MethodCall(method, os);

        if (reallyInvokedMethods.containsKey(method)) {
            // the same method was already called once

            List<MethodCall> parameters = reallyInvokedMethods.get(method);
            if (parameters.contains(currMethCall)) {
                // the same method WITH the same parameters was already called once
                // -> so get the listelement for this method call and add +1 to the counter

                //System.out.println(currMethCall);
                int index = parameters.indexOf(currMethCall);
                parameters.get(index).incrementCounter();
                //System.out.println(parameters.get(index));
            } else {
                // the invoked method was already called, but with other parameter(s)
                // -> add it to the list
                parameters.add(currMethCall);
            }
        } else {
            // the invoked method wasn't already called
            // -> so make a new list for this method and put it to the map (use the method for key)

            List<MethodCall> parameters = new ArrayList<>();
            parameters.add(currMethCall);
            reallyInvokedMethods.put(method, parameters);
        }
    }

    // if there is only one parameter (only the mock object)
    // the second one is the same like 'times(1)'
    public static <T> T verify(T mockObj) {
        return verify(mockObj, times(1));
    }

    public static Rate times(int n) {
        return new Rate(Rate.RateState.TIMES, n);
    }

    public static Rate never() {
        return new Rate(Rate.RateState.NEVER, 0);
    }

    public static Rate atLeast(int n) {
        return new Rate(Rate.RateState.ATLEAST, n);
    }

    public static Rate atMost(int n) {
        return new Rate(Rate.RateState.ATMOST, n);
    }

    private static void resetReallyInvokedMethodsMap() {
        reallyInvokedMethods = new HashMap();
    }

    // TODO is this method still necessary?
    public static void inListeAusgeben() {
        System.out.println("My Output:");
        for (Method key : reallyInvokedMethods.keySet()) {
            System.out.println("Method: " + key.getName());
            for (Object o : reallyInvokedMethods.get(key)) {
                System.out.println("Object: " + o.toString());
            }
        }
    }

    public static void spytesting() {
        // SPY() TESTING
        System.out.println("\n### Run spytesting()");

        List<String> testList = new ArrayList<>();
        List<String> spyList = spy(testList);
        spyList.add("test");
        spyList.add("test2");

        System.out.println("Size of testList: " + testList.size());
        System.out.println("Size of spyList: " + spyList.size());

        verify(spyList).add("test");
        verify(spyList).size();
        verify(spyList, atMost(1)).clear();
        verify(spyList, never()).clear();
    }

    public static void mocktesting() {
        // MOCK() TESTING        
        System.out.println("\n### Run mocktesting()");

        List<String> e2 = mock(ArrayList.class);

        e2.add("Max Muster");
        e2.add("Max Muster");
        e2.add("Max Muster 123");
        e2.size();
        e2.add("Hannes");

        verify(e2).size();
        verify(e2, times(2)).add("Max Muster");
    }
}
