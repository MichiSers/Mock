package de.oth.mocker;

import java.util.Arrays;

public class UniqueKey {

	public String methodName;
	Object[] args;

	public UniqueKey(String methodName, Object[] arguments) {
		this.methodName = methodName;
		this.args = arguments;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UniqueKey other = (UniqueKey) obj;
		if (!Arrays.equals(args, other.args))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		return true;
	}
	
//	@Override
//	public boolean equals(Object other) {
//
//		if (other == null){
//			
//			System.out.println("Other is null");
//			return false;}
//		if (other == this){
//			System.out.println("Other is this");
//			return true;}
//		if (!(other instanceof UniqueKey)){
//			System.out.println("Other is not a UniqeKey");
//			return false;}
//		UniqueKey otherClass = (UniqueKey) other;
//		
//			if(this.methodName == otherClass.methodName){
//				if(this.args.equals(otherClass.args)){
//					return true;
//				}
//			}else{
//				System.out.println("Method name is not the same");
//				return false;
//			}
//			return false;
//	}

	
}
