package dougs.imprimepdf.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ManipulaArquivos {

	/**
	 * @author Douglas S. S. Gonçalves
	 * @return arquivo pdf convertido para um array de bytes
	 * @throws IOException
	 */
	public byte[] retornaPdfEmArrayDeByte() throws IOException {
		// Caminho de um pdf que irá virar um array de bytes
		final String caminho = "..\\imprime-array-bytes-em-pdf-exemplo\\src\\main\\resources\\static\\pdfs\\pdfTeste.pdf";
		byte[] arquivoPdfEmArrayDeBytes = converteOPdfEmArrayDeBytes(caminho);

		return arquivoPdfEmArrayDeBytes;
	}

	private static byte[] converteOPdfEmArrayDeBytes(String caminho) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(caminho);
			return readFully(inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	private static byte[] readFully(InputStream stream) throws IOException {
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		while ((bytesRead = stream.read(buffer)) != -1) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}
}
