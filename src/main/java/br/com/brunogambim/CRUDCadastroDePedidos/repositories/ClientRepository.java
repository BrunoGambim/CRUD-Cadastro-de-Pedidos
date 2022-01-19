package br.com.brunogambim.CRUDCadastroDePedidos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	@Transactional(readOnly = true)
	Optional<Client> findByEmail(String email);
	@Transactional(readOnly = true)
	Optional<Client> findByAddressesId(Long id);
}
