package br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage;

import java.io.InputStream;
import java.net.URI;

public interface FileStorageStrategy {

	void uploadFile(InputStream file, String fileName, String ContentType);

	URI getFileURI(String name);

}
