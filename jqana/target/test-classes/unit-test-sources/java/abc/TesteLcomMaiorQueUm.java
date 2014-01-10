package abc;
/*
 * RFC:
 * - Sonar: 6
 * - RefactorIt: 
 * - CJKM: 
 * - Manual: 16 
 * - jQana: 
 * 
 * LCOM4:
 * - Sonar:  1
 * - Manual: 2 
 * 
 * CC:
 * - Sonar:   soma: 8  média: 1,1 ????
 * - Manual:  soma: 11 média: 1,1 
 * 
 */
public class TesteLcomMaiorQueUm { // CC soma: 11 média: 1,1 
	
	//RFC +2 (no-args + super())

	private int [] z;
	private int x;
	private int y;
	
	public TesteLcomMaiorQueUm() { 				// RFC +1  CC:+1

	}
	
	public int getX() { 						// RFC +1  CC:+1
		return x;
		
	}
	
	public void setX(int x) { 					// RFC +1  CC:+1
		this.x = x;
	}
	
	public int getY() {							// RFC +1  CC:+1
		return this.y;
	}
	
	public void setY(int y) {					// RFC +1  CC:+1
		this.y = y;
	}
	
	public void metodoA() {						// RFC +1  CC:+1
		System.out.println( metodoB () + 1); 	// RFC 		+2
	}
	
	public int metodoB() { 						// RFC +1  CC:+1
		int a = x + 2 * x;
		return a;
	}
	
	public void metodoC() { 					// RFC +1  CC:+1
		y = 1;
	}
	
	public void metodoD() {						// RFC +1  CC:+1
		setY(1);								// RFC +1  CC:+1
		if ( metodoE () < 0 ) {					// RFC +1
			System.out.println("ERRO");
		}
	}
	
	public int metodoE() {						// RFC +1  CC:+1
		return 10;
		
	}

}
