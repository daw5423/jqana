package abc;

import com.obomprogramador.tools.testerfc.TesteRFC.ClassB;

/*
 * RFC:
 * - Sonar: 6
 * - RefactorIt: 4
 * - CJKM: 7
 */
public class ClassA {
	private ClassB classB = new ClassB();				// call (constructor of class B) => +1
	public void doSomething () {						// method declaration => +1
		System.out.println("doSomething");				// call (System.out.println) => +1
	}
	public void doSomethingBasedOnClassB() {			// method declaration => +1
		System.out.println(classB.toString());        // call (System.out.println) => 0 because already counted on line 5 + call (toString) => +1
	}
}

// default constructor of ClassA => +1
// RFC = 6