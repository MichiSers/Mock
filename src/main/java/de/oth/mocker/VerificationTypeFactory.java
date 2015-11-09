package de.oth.mocker;

public class VerificationTypeFactory
{
	public VerificationType times(int times){
		return new Times(times);
	}
	
	public VerificationType atLeast(int atLeast){
		return new AtLeast(atLeast);
	}
	
	public VerificationType atMost(int atMost){
		return new AtMost(atMost);
	}
}
