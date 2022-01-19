package br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.dbstrategy;

import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.FileException;
import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.ObjectNotFoundException;
import br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.FileStorageStrategy;

public class DBImageFileStorage implements FileStorageStrategy{
	
	@Autowired
	private ImageFileRepository imageFileRepository;

	@Override
	public void uploadFile(InputStream file, String fileName, String ContentType) {
		try {
			ImageFile imageFile;
			imageFile = imageFileRepository.findByName(fileName).orElse(
					new ImageFile(fileName,ContentType));
			imageFile.setContent(IOUtils.toByteArray(file));
			imageFileRepository.save(imageFile);
		} catch (Exception e) {
			throw new FileException("Imagem inválida");
		}	
	}

	@Override
	public URI getFileURI(String name) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("image/db/{name}").buildAndExpand(name).toUri();
	}

	public ImageFile findByName(String name) {
		ImageFile imageFile = imageFileRepository.findByName(name).orElseThrow(()-> new ObjectNotFoundException(name, ImageFile.class));
		return imageFile;
	}
}
