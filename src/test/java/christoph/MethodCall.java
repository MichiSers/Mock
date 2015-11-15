package christoph;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class MethodCall {
    private Method method;
    private Object[] objects;
    private int counter = 0;
    
    public MethodCall (Method m, Object[] os) {
        this.method = m;
        this.objects = os;
        this.counter = 1;
    }
    
    public void incrementCounter () {
        counter++;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getObjects() {
        return objects;
    }

    public int getCounter() {
        return counter;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.method);
        hash = 37 * hash + Arrays.deepHashCode(this.objects);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodCall other = (MethodCall) obj;
        if (!Objects.equals(this.method, other.method)) {
            return false;
        }
        if (!Arrays.deepEquals(this.objects, other.objects)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MethodCall{" + "method=" + method.getName().toString() + ", objects= ");
        for (int i=0; i<objects.length; i++) {
            sb.append(objects[i].toString() + " --");
        }
        sb.append(", counter=" + counter + '}');
        return sb.toString();
    }
    
    
}
