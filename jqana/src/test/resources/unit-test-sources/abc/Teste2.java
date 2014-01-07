package abc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
/*
 * RFC: 
 * - Sonar: 9
 * - RefactorIt: 6
 * - cjkm: 9
 * - jQana: 9
 */
public class Teste2 {

	private int x;
	class Inner1 {
		public int teste(int ab) {
			class abx {
				void carregar(int x) throws Exception {
					if (x < 0) {
						throw new Exception("ERRO");
					}
					else if (x == 0) {
							System.out.println("OK");
					}
				}
			}
			if (ab == 21) {
				try {
					(new abx()).carregar(5);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 300;
			}
			return ab * 2;
		}
	}
	public static int metodo1(int a, int b) {
		int retorno = 0;
		Teste2 t2 = new Teste2();
		Inner1 i1 = t2.new Inner1();
		i1.teste(2);
		JButton btn = new JButton();
		btn.addActionListener(
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int x = 10;
							for (int c = 0; c < x; c++) {
								System.out.println();
							}
						}
						
					}
				);
		
		if (a < b) {
			for (int x=0; x < a; x++) {
				System.out.println(a == 0 ? "zero" : a);
			}
			return 0;
		}
		
		int c = a > b ? 10 : a == b ? 0 : -1;
		do {
			b--;
		} while (b < 1);
		
		while (b != a && c > b) {
			b = a;
			c = b + 1;
		}
		
		if (a < b) {
			c = a + 1;
		}
		else if (a == b && b == 1) {
				c = a;
		}
		try {
			if (a == 100) {
				System.out.println("Erro2");
			}
			switch (a) {
			case 0: 
				if (c == a) {
					System.out.println("Erro");
				}
				break;
			case 1:
				break;
				default:
					break;
			}
		}
		catch (NumberFormatException nfe) {
			
		}
		catch (Exception e) {
			
		}
		
		return retorno;
	}
}
