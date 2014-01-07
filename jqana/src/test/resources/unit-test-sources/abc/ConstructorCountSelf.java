package abc;
/*
 * SONAR RFC: 	3
 * CJKM 1.0:  	3
 * RefactorIt: 	2
 */
public class ConstructorCountSelf {

	private String chave = new String("");  // Deveria ser contado
	
	public ConstructorCountSelf() {
		super(); // Deveria ser contado?
	}
	

}
