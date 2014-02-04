package abc;
/*
 * RFC:
 * - Sonar: 3
 * - RefactorIt: 
 * - CJKM: 
 * - Manual: 3 
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
public class ConstructorCountSelf {

	private String chave = new String("");  // Deveria ser contado
	
	public ConstructorCountSelf() {
		super(); // Deveria ser contado?
	}
	

}
