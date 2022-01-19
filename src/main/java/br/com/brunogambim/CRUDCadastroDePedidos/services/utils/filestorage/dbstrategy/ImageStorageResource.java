package br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.dbstrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/db/images")
public class ImageStorageResource {
	
	@Autowired
	private DBImageFileStorage dbFileStorage;
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public ResponseEntity<ImageFile> findById(@PathVariable String name) {
		return ResponseEntity.ok()
				.body(this.dbFileStorage.findByName(name));
	}
}
