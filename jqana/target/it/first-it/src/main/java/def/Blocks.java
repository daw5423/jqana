package def;
/*
 * RFC:
 * - Sonar: 4
 * - RefactorIt: 
 * - CJKM: 
 * - Manual: 4 
 * - jQana: 
 * 
 * LCOM4:
 * - Sonar:  1
 * - Manual: 1 
 * 
 * CC:
 * - Sonar:   soma: 17 média = 5,7
 * - Manual:  soma: 17 média = 5,7
 * 
 */
public class Blocks {  //CC soma: 17 média = 5,7

	private int x;
	private int y;
	private int z;
	
	public Blocks(int x, int y, int z) {			//RFC+1		//CC+1
		this.x = x;
		this.y = y;
		this.z = z;
		if (x > y) {											//CC+1
			x = y;
		}
	}
	
	public Blocks() {								//RFC+1		//CC+1
		this(1,2,3);								//RFC+1
	}
	
	public int teste(int a) {						//RFC+1		//CC+1
		
		boolean xpto = true;
		
		if (x > a) {												//CC+1
			x = a;
			if (y > a) {											//CC+1
				y = a;
			}
			else {
				y = x;
			}
		}
		else if (z > a) {											//CC+1
				z = a;
		}
		
		do {														//CC+1
			for (int t=0; t < 10; t++) {							//CC+1
				while (a > t) {										//CC+1
					a = a - t;
				}
			}
		} while (xpto);
		
		// This switch counts as 3:
		switch(a) {
		case 1:														//CC+1
			break;
		case 2:														//CC+1
			return 1; // Count as 1									//CC+1
		case 3:														//CC+1
			break;
		default:
			break;
		}
		
		// This switch count as 2
		switch(this.y) {
		case 1: 														//CC+1
			return 2; // count as 1										//CC+1
		case 2: 														//CC+1
			break;
		}
		
		return a * 2;
	}

}
