package def;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
/*
 * RFC:
 * - Sonar: 9
 * - RefactorIt: 
 * - CJKM: 
 * - Manual: 9
 * - jQana: 
 * 
 * LCOM4:
 * - Sonar:  1
 * - Manual: 1 
 * 
 * CC:
 * - Sonar:   soma: 10 média = 10
 * - Manual:  soma: 10 média = 10
 * 
 */
public class Teste { // CC soma: 10 média = 10
	
	private static final int PAGE_EXISTS = 0;
	private static final int NO_SUCH_PAGE = 0;
	private List<String> relatorio = new ArrayList<String>();  //RFC+1 if you don't instantiate, the methods of ArrayList will not be 
															  // count in RFC calculation
	
																//RFC+2 no-args const + super()

	public int print(Graphics g, PageFormat pf, int pagina)	
			throws PrinterException {							//RFC+1			//CC+1
		int retorno = PAGE_EXISTS;
		int totalPaginas = relatorio.size() / 30; 				//RFC+1
		if (relatorio.size() % 30 > 0) {										//CC+1
			totalPaginas++;
		}
	   try {
		   System.out.println("OK");							//RFC+1
	   }
	   catch (Exception ex) {													//CC+1
		   totalPaginas = 0;
	   }
	   
	   // while
	   /*
	   if
	   */
	   int teste = retorno == 0 ? 100 : (retorno == NO_SUCH_PAGE) ? 10 : -1;		//CC +2
	   /*
	    * if (retorno == 0) {
	    * 	teste = 100;
	    * }
	    * else if (retorn0 == NO_SUCH_PAGE {
	    * 			teste = -1;
	    * 	   }
	    */
	   
	
	   String xpto = "Teste ? ";	
	   if (pagina > totalPaginas) {													//CC+1
			retorno = NO_SUCH_PAGE;
		}
		else {
			int inicial = pagina * 30;
			int contaLinhas = 0;
			Graphics2D g2d = (Graphics2D)g;
		    g2d.translate(pf.getImageableX(), pf.getImageableY());		//RFC+3
			for (int x = inicial; x < relatorio.size(); x++) {						//CC+1
				contaLinhas++;
				if (contaLinhas > 30 || contaLinhas > 20) {							//CC  +2
					break;
				}
				else if (contaLinhas > 19) {										//CC+1
					break;
				}
				g.drawString(relatorio.get(x), 40, 50 + contaLinhas * 14);		//RFC+2
			}
		}
		return retorno;
	}
}
