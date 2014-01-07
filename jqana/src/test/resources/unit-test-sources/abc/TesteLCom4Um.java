package abc;

public class TesteLCom4Um extends TesteSuperclass {

	public int x = 0;
	public int y = 0;
	public int zpto11 = 0;
	
	public TesteLCom4Um() {
		
	}
	
	public int metodoA() {
		return metodoB() + 2;
	}
	
	
	// This method shoud nod be considered for LCOM4 calculation
	@Override
	public int metodoHerdado1 (int xpto11) {
		return zpto11 * xpto11;
	}
	
	public int chamaHerdado() {
		return metodoHerdado1(4);
	}
	
	public int metodoB() {
		if (x - 3 == 0) {
			return 1;
		}
		return 0;
	}

	public int metodoC() {
		return y * 2 + x;
	}
	
	public int metodoD() {
		int xpto = 0;
		if (xpto == metodoE() + y) {
			return 1;
		}
		return 0;
	}
	
	public int metodoE() {
		return 0;
	}



}
