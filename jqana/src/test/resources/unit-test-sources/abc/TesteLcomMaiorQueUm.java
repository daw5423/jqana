package abc;
/*
 * LCOM4 = 2
 */
public class TesteLcomMaiorQueUm {

	private int [] z;
	private int x;
	private int y;
	
	public TesteLcomMaiorQueUm() { //+1

	}
	
	public int getX() { //+0 
		return x;
		
	}
	
	public void setX(int x) { //+0
		this.x = x;
	}
	
	public int getY() {//+0
		return this.y;
	}
	
	public void setY(int y) {//+0
		this.y = y;
	}
	
	public void metodoA() {
		System.out.println( metodoB () + 1); // +1 +1
	}
	
	public int metodoB() { //+1
		int a = x + 2 * x;
		return a;
	}
	
	public void metodoC() { //+1
		y = 1;
	}
	
	public void metodoD() {//+1
		setY(1);
		if ( metodoE () < 0 ) {//+1
			System.out.println("ERRO");
		}
	}
	
	public int metodoE() {//+1
		return 10;
		
	}

}
