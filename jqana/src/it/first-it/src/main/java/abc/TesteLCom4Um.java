package abc;
/*
 * RFC:
 * - Sonar: 5
 * - RefactorIt: 
 * - CJKM: 
 * - Manual: 12 
 * - jQana: 
 * 
 * LCOM4:
 * - Sonar: 1
 * - Manual: 1
 * 
 * CC:
 * - Sonar: soma:12 média: 1,5
 * - Manual: soma:12 média: 1,5
 * 
 */
public class TesteLCom4Um extends TesteSuperclass {  //CC médio: 1,5

	public int x = 0;
	public int y = 0;
	public int zpto11 = 0;
	
	public TesteLCom4Um() { 	//RFC+1 CC:+1
		
	}
	
	public int metodoA() {		//RFC+1 CC:+1
		return metodoB() + 2;	//RFC+1
	}
	
	
	// This method shoud nod be considered for LCOM4 calculation
	@Override
	public int metodoHerdado1 (int xpto11) {	//RFC+1  CC:+1
		return zpto11 * xpto11;					//RFC+1
	}
	
	public int chamaHerdado() {					//RFC+1  CC:+1
		return metodoHerdado1(4);				//RFC+1
	}
	
	public int metodoB() {						//RFC+1  CC:+1
		if (x - 3 == 0) {						// CC:+1
			return 1;							// CC:+1
		}
		return 0;
	}

	public int metodoC() {						//RFC+1  CC:+1
		return y * 2 + x;
	}
	
	public int metodoD() {						//RFC+1  CC:+1
		int xpto = 0;
		if (xpto == metodoE() + y) {			//RFC+1  CC:+1
			return 1;							// CC:+1
		}
		return 0;
	}
	
	public int metodoE() {						//RFC+1	CC:+1
		return 0;
	}



}
