
public class Blocks {

	private int x;
	private int y;
	private int z;
	
	public Blocks(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int teste(int a) {
		
		boolean xpto = true;
		
		if (x > a) {
			x = a;
			if (y > a) {
				y = a;
			}
			else {
				y = x;
			}
		}
		else if (z > a) {
				z = a;
		}
		
		do {
			for (int t=0; t < 10; t++) {
				while (a > t) {
					a = a - t;
				}
			}
		} while (xpto)
		
		return a * 2;
	}

}
