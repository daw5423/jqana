package def;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.List;

public class Teste {private static final int PAGE_EXISTS = 0;
private static final int NO_SUCH_PAGE = 0;
private List<String> relatorio;

public int print(Graphics g, PageFormat pf, int pagina)
		throws PrinterException {
	int retorno = PAGE_EXISTS;
	int totalPaginas = relatorio.size() / 30; //int totalPaginas = relatorio.size() / 30;
	if (relatorio.size() % 30 > 0) {
		totalPaginas++;
	}
   try {
	   System.out.println("OK");
   }
   catch (Exception ex) {
	   totalPaginas = 0;
   }
   
   // while
   /*
   if
   */
   int teste = retorno == 0 ? 100 : (retorno == NO_SUCH_PAGE) ? 10 : -1;
   /*
    * if (retorno == 0) {
    * 	teste = 100;
    * }
    * else if (retorn0 == NO_SUCH_PAGE {
    * 			teste = -1;
    * 	   }
    */
   

   String xpto = "Teste ? ";	
   if (pagina > totalPaginas) {
		retorno = NO_SUCH_PAGE;
	}
	else {
		int inicial = pagina * 30;
		int contaLinhas = 0;
		Graphics2D g2d = (Graphics2D)g;
	    g2d.translate(pf.getImageableX(), pf.getImageableY());
		for (int x = inicial; x < relatorio.size(); x++) {
			contaLinhas++;
			if (contaLinhas > 30 || contaLinhas > 20) {
				break;
			}
			g.drawString(relatorio.get(x), 40, 50 + contaLinhas * 14);
		}
	}
	return retorno;
}
}
