package abc;
/*
 * RFC:
 * - Sonar: 6
 * - RefactorIt: 4
 * - CJKM: 7
 * - Manual: 7
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
	
												//RFC: + 2 (no-args + super())
	
	private ClassB classB = new ClassB();		//RFC: +1			
	public void doSomething () {				//RFC: +1						
		System.out.println("doSomething");		//RFC: +1				
	}
	public void doSomethingBasedOnClassB() {			
		System.out.println(classB.toString());  		//RFC: +2 (println e toString)
	}
}

