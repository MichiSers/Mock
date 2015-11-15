package deniz;

import java.util.ArrayList;
import java.util.List;


import static deniz.Mocker.TimesBuilder.times;
import static deniz.Mocker.Verification.verify;

/**
 * Created by wagum on 02.11.2015.
 */

public class Main {
    public static void main(String[] args) {
        List<String> list = Mocker.mock(ArrayList.class);
 
        list.add("lalala");
        list.add("lalala");
        list.add("lalala");
        list.add("buh");
        list.add("buh");
        list.add("knlkanl");

        verify(list, times(3)).add("lalala");
        verify(list, times(2)).add("buh"); 	
        /*
         * Mock Object wird erzeugt. durch die times Methode wird angegeben mit welcher Anzahl von Aufrufen man rechnet. 
         * Die Anzahl der Aufrufe wird in der verify Methode überprüft. 
         */
    }
}
