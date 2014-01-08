package abc;
/*
 * RFC:
 * - Sonar: 6
 * - RefactorIt: 4
 * - CJKM: 7
 * - Manual: 6
 * - jQana: 
 * 
 * LCOM4:
 * - Sonar: 1
 * - Manual: 1
 * 
 * CC:
 * - Sonar: 1
 * - Manual: 1
 * 
 */
public class ClassA {
	private ClassB classB = new ClassB();				// call (constructor of class B) => +1
	public void doSomething () {						// method declaration => +1
		System.out.println("doSomething");				// call (System.out.println(String)) => +1
	}
	public void doSomethingBasedOnClassB() {			// method declaration => +1
		System.out.println(classB.toString());        // call (System.out.println (String)) => 0 because already counted on line 5 + call (toString) => +1
	}
}

// default constructor of ClassA => +1
// RFC = 6