package br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage.dbstrategy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long>{

	@Transactional(readOnly = true)
	public Optional<ImageFile> findByName(String name);

}
