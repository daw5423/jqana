package teste2;

import java.util.Calendar;

public class ClassWithDuplicateName {
    
    public static String teste() {
        return "teste";
    }
	
	public static String returnDate() {
		Calendar returnDate = Calendar.getInstance();

		return returnDate.toString();
	}
	
}
