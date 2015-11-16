package de.oth.mocker.test;

import static christoph.Mocker.mock;
import static christoph.Mocker.never;
import static christoph.Mocker.verify;
import static christoph.Mocker.spy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ChristophMock
{

	@Test public void functionalityTest(){
//		Can can = new Can();
//		Can mock = spy(can);
		Can mock = mock(Can.class);
		Can mock2 = mock(Can.class);
		mock.setNumber(6);
		System.out.println((mock2.getNumber()));
		verify(mock,never()).setNumber(6);
		
	}
}
