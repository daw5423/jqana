package abc;

public class TesteLCom4Um {

	public int x = 0;
	public int y = 0;
	
	public TesteLCom4Um() {
		// TODO Auto-generated constructor stub
	}
	
	public int metodoA() {
		return metodoB() + 2;
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
