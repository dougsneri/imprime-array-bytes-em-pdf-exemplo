package dougs.imprimepdf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dougs.imprimepdf.utils.ManipulaArquivos;

@Controller
public class ImprimePdfController {

	private final Logger LOG = LogManager.getLogger("Log4jCore");

	@RequestMapping("/pdf")
	public ResponseEntity<InputStreamResource> baixaPDF() throws IOException {
		ManipulaArquivos chamaArquivo = new ManipulaArquivos();
		byte[] pdfEmArrayDeByte = chamaArquivo.retornaPdfEmArrayDeByte();
		
		//Variaveis para criar nome que sairá na impressão
		String descricao = "TesteImpressaoPDF";
		String sufixo = ".pdf";
		String nomeImpressao = descricao.concat(sufixo);
		
		File arquivoTemp = new File(nomeImpressao);
		FileOutputStream fos = new FileOutputStream(arquivoTemp);
		fos.write(pdfEmArrayDeByte);
		fos.flush();
		fos.close();
		
		try {
			HttpHeaders respHeaders = new HttpHeaders();
			MediaType mediaType = MediaType.parseMediaType("application/pdf");

			respHeaders.setContentType(mediaType);
			respHeaders.setContentLength(arquivoTemp.length());
			respHeaders.setContentDispositionFormData("attachment", arquivoTemp.getName());

			InputStreamResource isr = new InputStreamResource(new FileInputStream(arquivoTemp));
			
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Erro ao imprimir PDF: ", e);
			return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
