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
 * - Sonar:  1
 * - Manual: 1 
 * 
 * CC:
 * - Sonar:   soma: 2 média = 1
 * - Manual:  soma: 2 média = 1
 * 
 */
public class TesteSuperclass { //CC soma: 2 média = 1

	public TesteSuperclass() { 		//RFC+1   CC+1
		// RFC+1 para o super(), gerado pelo compilador
	}
	
	public int metodoHerdado1 (int x) {		//RFC+1  CC+1
		return x + 1;
	}

}
