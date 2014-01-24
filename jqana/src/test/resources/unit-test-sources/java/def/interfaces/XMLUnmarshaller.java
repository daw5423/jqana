package def.interfaces;

import java.io.File;


/**
 * Analisa um arquivo XML de alertas, gerando a estrutura de classes.
 * @author Cleuton Sampaio
 * @see TipoListaALertas
 */
public interface XMLUnmarshaller {
	/**
	 * Método que analisa o XML.
	 * @param xmlFile java.io.File: arquivo a ser analisado.
	 * @return TipoListaAlertas: instância da lista de alertas.
	 * @throws InvalidXMLException
	 */
	void unmarshall(File xmlFile) throws Exception;
}
