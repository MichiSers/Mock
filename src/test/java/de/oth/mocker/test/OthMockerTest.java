package de.oth.mocker.test;

import static de.oth.mocker.Mocker.mock;
import static de.oth.mocker.Mocker.verify;
import static de.oth.mocker.Mocker.spy;
import static de.oth.mocker.Mocker.never;
import static de.oth.mocker.Mocker.atLeast;
import static de.oth.mocker.Mocker.atMost;
import static de.oth.mocker.Mocker.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/*
 * This Java source file was auto generated by running 'gradle init --type java-library'
 * by 'Michi' at '03.11.15 22:32' with Gradle 2.6
 *
 * @author Michi, @date 03.11.15 22:32
 */
public class OthMockerTest
{
	
	  @Test public void testSomeLibraryMethod() {
	    	List<String>	mockObject	=	mock(ArrayList.class);
	    	List<String>	mockObject2	=	mock(ArrayList.class);
	    	mockObject.add("asd");
	    	mockObject2.add("asd");
	    	verify(mockObject).add("asd");
	    }

//	@Test public void testIfSpy()
//	{
//		List<String> list = new ArrayList<String>();
//		List<String> mockObject = mock(ArrayList.class);
//		mockObject.add("asd");
//		verify(mockObject,never()).add("asd");
//	}

}
