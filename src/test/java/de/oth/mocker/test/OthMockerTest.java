package de.oth.mocker.test;

import	java.util.*;
import	org.junit.*;
import	static	org.junit.Assert.*;
import	static	de.oth.mocker.Mocker.mock;
import	static	de.oth.mocker.Mocker.verify;
import	static	de.oth.mocker.Mocker.times;
import	static	de.oth.mocker.Mocker.never;

/*
 * This Java source file was auto generated by running 'gradle init --type java-library'
 * by 'Michi' at '03.11.15 22:32' with Gradle 2.6
 *
 * @author Michi, @date 03.11.15 22:32
 */
public class OthMockerTest {
    @Test public void testSomeLibraryMethods() {
    	List<String>	mockObject	=	mock(ArrayList.class);
    	
//        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethods());
    }
}
