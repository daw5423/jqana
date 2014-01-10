package abc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
/*
 * RFC:
 * - Sonar: 12
 * - RefactorIt: 
 * - CJKM: 
 * - Manual: 10 
 * - jQana: 
 * 
 * LCOM4:
 * - Sonar: 1
 * - Manual: 1
 * 
 * CC:
 * - Sonar: 9.7
 * - Manual: soma=28 média=14
 * 
 */
public class Teste2 {

	private int x;
	class Inner1 { 							// CC Médio: 8 
		public int teste(int ab) {							//CC: +1
			class abx {						//CC Médio: 4 
				void carregar(int x) throws Exception {		//CC: +1
					if (x < 0) {							//CC: +1
						throw new Exception("ERRO");		//CC: +1
					}
					else if (x == 0) {						//CC: +1
							System.out.println("OK");
					}
				}
			}
			if (ab == 21) { 				//CC: +1
				try {
					(new abx()).carregar(5);
				} catch (Exception e) {			//CC: +1
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 300;						//CC: +1
			}
			return ab * 2;
		}
	}
	public static int metodo1(int a, int b) { 		//RFC +1 		CC: +1 total: 20
		int retorno = 0;
		Teste2 t2 = new Teste2(); 					//RFC +2 construtor no-args e Construtor de Object
		Inner1 i1 = t2.new Inner1(); 				// RFC +1
		i1.teste(2);								// RFC +1
		JButton btn = new JButton();				// RFC +1
		btn.addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {		// RFC +1			CC: +1
							int x = 10;
							for (int c = 0; c < x; c++) {										//CC: +1
								System.out.println();						// RFC +1
							}
						}
						
					}
				);
		
		if (a < b) {																//CC: +1
			for (int x=0; x < a; x++) {												//CC: +1
				System.out.println(a == 0 ? "zero" : a);					// RFC +1 println()
			}
			return 0;													//CC: +1
		}
		
		int c = a > b ? 10 : a == b ? 0 : -1;							//CC: +2
		do {															//CC: +1
			b--;
		} while (b < 1);
		
		while (b != a && c > b) {										//CC: +2
			b = a;
			c = b + 1;
		}
		
		if (a < b) {													//CC: +1
			c = a + 1;
		}
		else if (a == b && b == 1) {									//CC: +2
				c = a;
		}
		try {
			if (a == 100) {												//CC: +1
				System.out.println("Erro2");								// RFC +1 println(String)
			}
			switch (a) {
			case 0: 													//CC: +1
				if (c == a) {											//CC: +1
					System.out.println("Erro");
				}
				break;
			case 1:														//CC: +1
				break;
				default:
					break;
			}
		}
		catch (NumberFormatException nfe) {								//CC: +1
			
		}
		catch (Exception e) {											//CC: +1
			
		}
		
		return retorno;
	}
}
