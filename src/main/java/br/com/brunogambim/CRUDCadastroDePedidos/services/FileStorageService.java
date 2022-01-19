package br.com.brunogambim.CRUDCadastroDePedidos.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.FileException;
import br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.FileStorageStrategy;

@Service
public class FileStorageService {
	
	private FileStorageStrategy fileStorageStrategy;
	private Logger logger = LoggerFactory.getLogger(FileStorageService.class);
	
	@Autowired
	public FileStorageService (FileStorageStrategy fileStorageStrategy){
		this.fileStorageStrategy = fileStorageStrategy;
	}
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			return uploadFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
		} catch (IOException e) {
			throw new FileException("Arquivo inválido");
		}
	}
	
	public URI uploadFile(InputStream file, String fileName, String ContentType) {
		logger.info("Iniciando upload");
		fileStorageStrategy.uploadFile(file, fileName, ContentType);
		logger.info("Upload concluído");
		return getImageURI(fileName);
	}

	private URI getImageURI(String name) {
		return this.fileStorageStrategy.getFileURI(name);
	}
	
	
}
