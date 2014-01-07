package com.obomprogramador.tools.jqana_test.it_project_1.p1;
/*
 * LCOM4 = 2
 */
public class TesteLcomMaiorQueUm {

	private int [] z;
	private int x;
	private int y;
	
	public TesteLcomMaiorQueUm() {

	}
	
	public int getX() {
		return x;
		
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void metodoA() {
		System.out.println( metodoB () + 1);
	}
	
	public int metodoB() {
		int a = x + 2 * x;
		return a;
	}
	
	public void metodoC() {
		y = 1;
	}
	
	public void metodoD() {
		setY(1);
		if ( metodoE () < 0 ) {
			System.out.println("ERRO");
		}
	}
	
	public int metodoE() {
		return 10;
		
	}

}
