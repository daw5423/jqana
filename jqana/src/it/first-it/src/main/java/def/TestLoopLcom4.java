package def;

import java.util.Date;

public class TestLoopLcom4 {
	
	private int x;
	
	public void validade (String a, int b, double c, String d, boolean e, Object f) {
		this.validade("a", 2, 3.0, "d", 'e', "f");
	}
	
	public void validade (String a, int b, double c, String d, char e, Object f) {
		this.validade("a", 2, 3.0, "d", false, "f");
	}
	
	public String returnString() {
		return "x";
	}
	
	public void testMethod () {
		int i = 10;
		for (int x = 1; x < 10; x++) {
			boolean z = false;
			/*
			 * Literals: "a", 'a", 1 etc
			 * method return value
			 * new objects
			 * new and method return: (new Date).toString()
			 * fields: Class.staticField or Object.field
			 * Expression: (1 / x)
			 * Expression with cast: (double) (x)
			 */
			this.validade("a", i, 5.2, this.returnString(), z, new Date());
		}
	}
	

}
