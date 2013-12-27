
public class Blocks {

	private int x;
	private int y;
	private int z;
	
	public Blocks(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		if (x > y) {
			x = y;
		}
	}
	
	public Blocks() {
		this(1,2,3);
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
		} while (xpto);
		
		// This switch counts as 3:
		switch(a) {
		case 1:
			break;
		case 2:
			return 1; // Count as 1
		case 3:
			break;
		default:
			break;
		}
		
		// This switch count as 2
		switch(this.y) {
		case 1: 
			return 2; // count as 1
		case 2: 
			break;
		}
		
		return a * 2;
	}

}
