package abc;

import java.util.Date;

/*
 * RFC:
 * - Sonar: 9
 * - RefactorIt: 
 * - CJKM: 
 * - Manual: 9 
 * - jQana: 
 * 
 * LCOM4:
 * - Sonar:  1
 * - Manual: 1 
 * 
 * CC:
 * - Sonar:   soma: 5  média: 1.7
 * - Manual:  Soma: 5 Média: 1,6666666...
 * 
 */
public class TesteRfc { // CC Soma: 5 Média: 1,6666666...
	
	// RFC +2 (1 para o no-args constructor e 1 para a chamada de super())

	public void metodo1() {								// RFC +1   //CC+1
		for (int x=1; x < Math.round(10.5); x++) {		// RFC +1	//CC+1
			
		}
		
		int x = 10;
		if ((2 * (x - 1)) > 10) {						//CC+1
			
		}
		System.out.println(new Date());					// RFC +2 (println(Object) e Construtor de "Date*)
	}
	
	public void metodo2() {								// RFC +1			//CC+1
		System.out.println("");							// RFC +1 (println(String)
	}
	
	public void metodo3() {								// RFC +1			//CC+1
		System.out.println("");
	}

}
