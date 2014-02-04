package abc;

public class DuplicateConstructor {
	/*
	 * RFC:
	 * - Sonar: 5
	 * - RefactorIt: 
	 * - CJKM: 
	 * - Manual: 5 
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
	private String texto = new String(); // construtor de String com nulo +1
	
	public DuplicateConstructor() { 		// construtor +1
		String texto = new String(this.texto); // construtor de String com outro String; +1
		String texto3 = "";                 // construtor de String com literal String +1 
		String texto4 = new String("a");	// construtor de String com literal String +0
		System.out.println(texto3);         // toString() +1
		System.out.println(texto);          // toString() +0
	}
}

// RFC: 5
